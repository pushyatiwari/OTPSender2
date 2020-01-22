package coml.example.android.otpsender.Utils;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import coml.example.android.otpsender.R;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private MyHelper mMyHelper2;
    ArrayList<String> newcontacts = new ArrayList<>();
    private SharedPreferences prefs;
    private String CHANNEL_ID;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Toast.makeText(context, "started broadcast", Toast.LENGTH_SHORT).show();

        mMyHelper2 = new MyHelper(context,"OTPReceivers",null,1);

         prefs =context.getSharedPreferences("coml.example.android.otpsender", Context.MODE_PRIVATE);

        try {
            newcontacts = (ArrayList<String>) objectSerializer.deserialize(prefs
                        .getString("contacts", objectSerializer.serialize(new ArrayList<String>())));
            if(newcontacts!=null) {
                for(int i =0;i<newcontacts.size();i++) {
                    Log.i("BroadcastContacts", "for"+i+ ": "+newcontacts.get(i));
                }
            }else {
                Log.i("empty", "onCreateView: ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get("pdus");

            String smsMessageStr = "";
            String smsMessageBody="";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();
                int y =0;
                smsMessageStr +=   "from: "+ address+":: "+smsBody  ;
                Pattern p = Pattern.compile("OTP(.*?)");
                Matcher m = p.matcher(smsMessageStr);
                if(m.find()) {
                    smsMessageStr= smsMessageStr;
                 for(int j =0;j<newcontacts.size();j++) {
                     if(!newcontacts.isEmpty()) {
                         mMyHelper2.insertData(newcontacts.get(j), smsMessageStr);
                         SmsManager sendsms = SmsManager.getDefault();
                          sendsms.sendTextMessage(newcontacts.get(j), null, smsMessageStr, null, null);
                          Notification(context,newcontacts.get(j).toString());

                     }else{
                         Toast.makeText(context, "to send otp, register a number", Toast.LENGTH_SHORT).show();
                     }
                    }
                    y=1;
                }
                Pattern p2 = Pattern.compile("otp(.*?)");
                Matcher m2 = p2.matcher(smsMessageStr);
                if(m2.find()) {
                    smsMessageStr= smsMessageStr;
                   for(int j =0;j<newcontacts.size();j++) {
                       if(!newcontacts.isEmpty()) {
                           mMyHelper2.insertData(newcontacts.get(j), smsMessageStr);
                           SmsManager sendsms = SmsManager.getDefault();
                           sendsms.sendTextMessage(newcontacts.get(j), null, smsMessageStr, null, null);
                           Notification(context,newcontacts.get(j).toString());
                       }else{
                           Toast.makeText(context, "to send otp, register a number", Toast.LENGTH_SHORT).show();
                       }
                    }
                    y=1;
                }
                Pattern p3 = Pattern.compile("password(.*?)");
                Matcher m3 = p3.matcher(smsMessageStr);
                if(m3.find()) {
                    smsMessageStr= smsMessageStr;
                    for(int j =0;j<newcontacts.size();j++) {
                        if(!newcontacts.isEmpty()) {
                            mMyHelper2.insertData(newcontacts.get(j), smsMessageStr);
                            SmsManager sendsms = SmsManager.getDefault();
                            sendsms.sendTextMessage(newcontacts.get(j), null, smsMessageStr, null, null);
                            Notification(context,newcontacts.get(j).toString());
                        }else{
                            Toast.makeText(context, "to send password, register a number", Toast.LENGTH_SHORT).show();
                        }
                    }
                    y=1;
                }
                Pattern p4 = Pattern.compile("Password(.*?)");
                Matcher m4 = p4.matcher(smsMessageStr);
                if(m4.find()) {
                    smsMessageStr= smsMessageStr;
                    for(int j =0;j<newcontacts.size();j++) {
                        if(!newcontacts.isEmpty()) {
                            mMyHelper2.insertData(newcontacts.get(j), smsMessageStr);
                            SmsManager sendsms = SmsManager.getDefault();
                            sendsms.sendTextMessage(newcontacts.get(j), null, smsMessageStr, null, null);
                            Notification(context,newcontacts.get(j).toString());
                        }else{
                            Toast.makeText(context, "to send password, register a number", Toast.LENGTH_SHORT).show();
                        }
                    }
                    y=1;
                }
                Pattern p5 = Pattern.compile("PASSWORD(.*?)");
                Matcher m5 = p5.matcher(smsMessageStr);
                if(m5.find()) {
                    smsMessageStr= smsMessageStr;
                    for(int j =0;j<newcontacts.size();j++) {
                        if(!newcontacts.isEmpty()) {
                            mMyHelper2.insertData(newcontacts.get(j), smsMessageStr);
                            SmsManager sendsms = SmsManager.getDefault();
                            sendsms.sendTextMessage(newcontacts.get(j), null, smsMessageStr, null, null);
                            Notification(context,newcontacts.get(j).toString());
                        }else{
                            Toast.makeText(context, "to send password, register a number", Toast.LENGTH_SHORT).show();
                        }
                    }
                    y=1;
                }
                if(y==0){
                    Toast.makeText(context, "msg not an otp", Toast.LENGTH_SHORT).show();
                }



            }



        }
        Toast.makeText(context, "broadcast recieved", Toast.LENGTH_SHORT).show();



    }
    private void Notification(Context context,String s){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.iconotp)
                .setContentTitle("otp send to " + s)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, mBuilder.build());
    }


}


