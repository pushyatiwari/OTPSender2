package coml.example.android.otpsender.Utils;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import coml.example.android.otpsender.R;

@TargetApi(Build.VERSION_CODES.N)
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedularService extends JobService {
    ArrayList<String> smsBody = new ArrayList<String>();

    private Cursor c;

    private String body;
    String finalmsg;

    String phone;
    private MyHelper mMyHelper2;
    ArrayList<String> main = new ArrayList<String>();
    ArrayList<String> newcontacts = new ArrayList<>();
    private SharedPreferences prefs;
    private String CHANNEL_ID;

    String TAG = "jobschdlar";
    private int type2;


    @Override
    public boolean onStartJob(JobParameters parameters) {
        //Toast.makeText(this, "job started", Toast.LENGTH_SHORT).show();
        parameters.getTriggeredContentUris();
        mMyHelper2 = new MyHelper(getApplicationContext(), "OTPReceivers", null, 1);

        prefs = getApplicationContext().getSharedPreferences("coml.example.android.otpsender", Context.MODE_PRIVATE);

        try {
            newcontacts = (ArrayList<String>) objectSerializer.deserialize(prefs
                    .getString("contacts", objectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        getAllSms(getApplicationContext());
        for(int j =0;j<newcontacts.size();j++) {
            if(!newcontacts.isEmpty()&&finalmsg!=null) {
                if(type2==1) {
                    try{
                    SmsManager sendsms = SmsManager.getDefault();
                    sendsms.sendTextMessage(newcontacts.get(j), null, finalmsg, null, null);
                        Toast.makeText(this, "sms send", Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "onStartJob: smssend");}
                    catch (Exception e){
                        e.printStackTrace();
                        Log.i("failedsms", "onStartJob: ");
                    }
                }
                if(type2==2){
                    Log.i("jobfinalmsgtype2", finalmsg);
                mMyHelper2.insertData(newcontacts.get(j), finalmsg);
                }
                Notification(newcontacts.get(j).toString());
            }
        }

            jobFinished(parameters, true);
            refresh();

        return false;
    }

    private void Notification(String s) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.iconotp)
                .setContentTitle("otp send to " + s)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(1, mBuilder.build());
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this, "job schdular failed", Toast.LENGTH_SHORT).show();
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)

    private void refresh() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.N) {
            ComponentName component = new ComponentName(getPackageName(),
                    JobSchedularService.class.getName());
            JobInfo builder = new JobInfo.Builder(1, component)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .addTriggerContentUri(new JobInfo.TriggerContentUri(
                            Telephony.Sms.CONTENT_URI,
                            JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS))
                    .setTriggerContentUpdateDelay(1000)
                    .setTriggerContentMaxDelay(300)
                    .build();

            JobScheduler mJobScheduler = (JobScheduler)
                    getSystemService(JOB_SCHEDULER_SERVICE);
            int res = mJobScheduler.schedule(builder);


            Toast.makeText(this, "Job scheduled", Toast.LENGTH_SHORT).show();

            if (res == JobScheduler.RESULT_SUCCESS) {
                Log.i("res", "job scheduled" );
            } else {
                Log.i("res", "job scheduled failed");
            }

        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getAllSms(Context context) {
           int count = 0;
        ContentResolver cr = context.getContentResolver();
        c = cr.query(Telephony.Sms.CONTENT_URI, new String[]{"address", "body", "date", "read","type"}
        , null, null, null);
        int totalSMS = 0;
        int j = 0;
        boolean found = false;
        Date currentTime = Calendar.getInstance().getTime();
              String systime=String.valueOf(currentTime);
        Log.i("time", String.valueOf(currentTime));
        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {

                    int i;
                    body = c.getString(c.getColumnIndex("address")) + "\n" +" " +
                            c.getString(c.getColumnIndex("body"));
                    String date = c.getString(c.getColumnIndex("date"));
                    String status = c.getString(c.getColumnIndex("read"));


                   Long timestamp = Long.parseLong(date);
                    Calendar calendar = Calendar.getInstance();
                   calendar.setTimeInMillis(timestamp);
                    Date finaldate = calendar.getTime();
                    String smstime = finaldate.toString();
                    Log.i("smstym", smstime);
                    //date of message
                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    cal.setTimeInMillis(Long.parseLong(date));
                    date = DateFormat.format("dd-MMM-yyyy", cal).toString();
                    //current date
                    Date curdate = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(curdate);
                    int y = 0;
                    String type = c.getString(c.getColumnIndex("type"));
                     type2 = Integer.parseInt(type);
                    Log.i("type1", type);
                    if (date.equals(formattedDate)&&(type2==1)||(type2==2)) {
                        Pattern p = Pattern.compile("OTP(.*?)");
                        Matcher m = p.matcher(body);

                        while (m.find()) {
                            y = 1;
                            main.add(m.group(0));
                            Toast.makeText(context, "done", Toast.LENGTH_LONG).show();
                            smsBody.add(body.toString());
                            smsBody.add("djd");
                            Log.i("myarray", "getAllSms: "+smsBody.get(0));
                            Log.i("smsBody", body+"  j=  "+j);
                            i=j;
                            finalmsg = body;
                            count++;

                        }
                        if (y == 0) {
                            Pattern p2 = Pattern.compile("otp(.*?)");
                            Matcher m2 = p2.matcher(body);
                            while (m2.find()) {
                                found=true;
                                y = 2;
                                main.add(m2.group(0));
                                Toast.makeText(context, "done", Toast.LENGTH_LONG).show();

                                Log.i("smsBody", body+"  j =  "+j );
                                i=j;
                                finalmsg = body;
                                count++;

                            }


                        }

                            Pattern p3 = Pattern.compile("password(.*?)");
                            Matcher m3 = p3.matcher(body);
                            while (m3.find()) {
                                found=true;
                                y = 1;
                                main.add(m3.group(0));
                                Toast.makeText(context, "done", Toast.LENGTH_LONG).show();

                                Log.i("smsBody", body+"  j =  "+j );
                                i=j;
                                finalmsg = body;
                                count++;

                            }
                        Pattern p4 = Pattern.compile("Password(.*?)");
                        Matcher m4 = p4.matcher(body);
                        while (m4.find()) {
                            found=true;
                            y = 1;
                            main.add(m4.group(0));
                            Toast.makeText(context, "done", Toast.LENGTH_LONG).show();

                            Log.i("smsBody", body+"  j =  "+j );
                            i=j;
                            finalmsg = body;
                            count++;

                        }
                        Pattern p5 = Pattern.compile("PASSWORD(.*?)");
                        Matcher m5 = p5.matcher(body);
                        while (m5.find()) {
                            found=true;
                            y = 1;
                            main.add(m5.group(0));
                            Toast.makeText(context, "done", Toast.LENGTH_LONG).show();

                            Log.i("smsBody", body+"  j =  "+j );
                            i=j;
                            finalmsg = body;
                            count++;

                        }





                    }







                Log.i("count", String.valueOf(count));


            } else {
                Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show();
            }
        }


    }

}