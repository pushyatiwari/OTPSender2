package coml.example.android.otpsender.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import coml.example.android.otpsender.R;

public class Guide extends AppCompatActivity {
 private  TextView guide_txt;
 private  StringBuffer text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        text = new StringBuffer();
        guide_txt = (TextView)findViewById(R.id.guidetv);
        String txt = "OTPSender sends all otp and passwords that is received to your phone to registered mobile number." +
                "Send your otp automatically to your family members who uses your card."+
                "Edit registration as required.\n"+
                "\n"+"\n"+"  Warning: Only register trusted numbers.";
    text.append(txt);
    guide_txt.setText(text.toString());
    }
}
