package coml.example.android.otpsender.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import coml.example.android.otpsender.Fragments.InsideER1;
import coml.example.android.otpsender.Fragments.InsideER2;
import coml.example.android.otpsender.Utils.JobSchedularService;
import coml.example.android.otpsender.Utils.MyHelper;
import coml.example.android.otpsender.R;
import coml.example.android.otpsender.Utils.sectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private static int res;
    private sectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static MyHelper mMyHelperttoER1;
    private static InsideER1 insideER1;
    private static InsideER2 insideER2;
    private Toolbar mToolbar;
    private static JobScheduler mJobScheduler;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            if(item.getItemId()==R.id.guide_note){
                Intent intent = new Intent(getApplicationContext(),Guide.class);
                startActivity(intent);
            }
            return super.onOptionsItemSelected(item);


    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.N) {
//            PersistableBundle intentExtras = new PersistableBundle(Integer.parseInt("android.provider.Telephony.SMS_RECEIVED"));
            Uri uriSms = Uri.parse("content://sms/inbox");
            ComponentName component= new ComponentName( getPackageName(),
                    JobSchedularService.class.getName() );
            JobInfo builder = new JobInfo.Builder(1, component)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .addTriggerContentUri(new JobInfo.TriggerContentUri(
                            Telephony.Sms.CONTENT_URI,
                            JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS))
                    .setTriggerContentUpdateDelay(1000)
                    .setTriggerContentMaxDelay(300)
                    .build();

            mJobScheduler = (JobScheduler)
                    getSystemService(this.JOB_SCHEDULER_SERVICE );
            int res = mJobScheduler.schedule(builder);




            if(res==JobScheduler.RESULT_SUCCESS){
                Log.i("res", "job scheduled");
            }else {
                Log.i("res", "job scheduled failed");
            }

        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);}
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_SMS},
                    2);
        }


        insideER1 = new InsideER1();
         insideER2 = new InsideER2();
        mMyHelperttoER1 = new MyHelper(MainActivity.this,"OTPReceivers",null,1);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
       // ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, 1);

        mSectionsPagerAdapter = new sectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public void sendtoInsideER1() {
        try {
            change(R.id.frame_layout,insideER1);



        } catch (Exception e){

        }
    }

    public void sendshowctctoER2() {


            change(R.id.frame_layout,insideER2);




    }

    public void change(int i ,  android.support.v4.app.Fragment f){
        android.support.v4.app.FragmentManager fragmentManager2 = getSupportFragmentManager();
        fragmentManager2.beginTransaction()
                .replace(i,f )
                .commit();
    }

    }

