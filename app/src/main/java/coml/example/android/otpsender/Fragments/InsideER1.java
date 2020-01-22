package coml.example.android.otpsender.Fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import coml.example.android.otpsender.R;

import static coml.example.android.otpsender.Activities.MainActivity.mMyHelperttoER1;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsideER1 extends Fragment {


    private TextView data_tv ;
   View view;
   String TAG = "Inside ER1";
    static  StringBuffer buffer;


    public InsideER1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     view = inflater.inflate(R.layout.fragment_inside_er1, container, false);
        data_tv = (TextView) view.findViewById(R.id.ier_tv);
        Log.i("data_tv",String.valueOf( R.id.ier_tv));
        Log.i(TAG, "onCreateView: ");
        setdata();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: ");
        super.onAttach(context);

    }

    public void receive(StringBuffer buffer) {

        //Log.i("buffer", buffer.toString());
        //Log.i("data_tv",String.valueOf( R.id.ier_tv));

        try {

            data_tv.setText(buffer.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i(TAG, "receive: ");

    }
    private void setdata(){

        Cursor res = mMyHelperttoER1.getAllData();
        try{

        res.moveToFirst();
       buffer = new StringBuffer();

        buffer.append("Id :" + res.getString(0) + "\n");
        buffer.append("send to :" + res.getString(1) + "\n");
        buffer.append("text :" + res.getString(2) + "\n");

        while (res.moveToNext()) {
            buffer.append("Id :" + res.getString(0) + "\n");
            buffer.append("send to :" + res.getString(1) + "\n");
            buffer.append("text :" + res.getString(2) + "\n");
        }
        //Log.i("buffer", "sendtoInsideER1: "+buffer.toString());
        receive(buffer);
       }
        catch (Exception e){
            Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
        }

    }





}
