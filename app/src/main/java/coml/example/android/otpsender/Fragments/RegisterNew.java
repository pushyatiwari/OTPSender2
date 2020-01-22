package coml.example.android.otpsender.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import coml.example.android.otpsender.Utils.MyHelper;
import coml.example.android.otpsender.R;
import coml.example.android.otpsender.Utils.objectSerializer;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterNew extends Fragment {
    private EditText contact;
    StringBuffer s = new StringBuffer();
    private Cursor c;
    private MyHelper mMyHelper;
    SQLiteDatabase mSQLDB;
    String phone;
    public SharedPreferences sharedPreferences;
    private ArrayList<String> contactsarray = new ArrayList<>();
    private Button pickctc;
    private static final int result=0;
    private TextView nametv;


    public RegisterNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_register_new, container, false);
        Button reg_btn = (Button) view.findViewById(R.id.RegisterNew);
        pickctc=(Button) view.findViewById(R.id.cnt_pick);
        contact = (EditText) view.findViewById(R.id.contact_edt);
        nametv =(TextView) view.findViewById(R.id.name);
        mMyHelper = new MyHelper(getActivity(),"OTPReceivers",null,1);
        mSQLDB =mMyHelper.getWritableDatabase();
        pickctc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, result);
            }
        });


        reg_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                phone = contact.getText().toString();

                sharedPreferences =getActivity().getSharedPreferences("coml.example.android.otpsender", Context.MODE_PRIVATE);

                try {
                    contactsarray = (ArrayList<String>) objectSerializer.deserialize(sharedPreferences
                            .getString("contacts", objectSerializer.serialize(new ArrayList<String>())));

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    contactsarray.add(phone);
                    editor.putString("contacts",objectSerializer.serialize(contactsarray));
                    editor.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                contact.setText("");
                nametv.setText("");
                InsideER2 insideER2RN =  new InsideER2();
                android.support.v4.app.FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                fragmentManager2.beginTransaction()
                        .replace(R.id.frame_layout,insideER2RN )
                        .commit();

 }
        });







        return view;
    }
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            Uri uri = data.getData();
            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
             nametv.setText(name);
            contact.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case result:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }



}


