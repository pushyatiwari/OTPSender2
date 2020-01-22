package coml.example.android.otpsender.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import coml.example.android.otpsender.R;
import coml.example.android.otpsender.Utils.objectSerializer;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsideER2 extends Fragment {
   String TAG = "Inside ER2";
    static SharedPreferences showsp;


    public static TextView rec_tvER2;
    public  ArrayList<String> newcontacts = new ArrayList<>();


    public InsideER2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inside_er2, container, false);
        rec_tvER2 = (TextView) view.findViewById(R.id.receivers);
        Log.i(TAG, "onCreateView: ");
        setdatainER2();
        return view;
    }

   public void setdatainER2(){
       if(newcontacts!=null) {
           showsp =     getActivity().getSharedPreferences("coml.example.android.otpsender",Context.MODE_PRIVATE);


           try {
               newcontacts = (ArrayList<String>) objectSerializer.deserialize(showsp
                       .getString("contacts", objectSerializer.serialize(new ArrayList<String>())));
           } catch (IOException e) {

           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }
           for(int i =0;i<newcontacts.size();i++) {
               Log.i("Contacts", newcontacts.get(i));

           }
           StringBuffer stnewCtc = new StringBuffer();

           for(int i =0;i<newcontacts.size();i++) {
               stnewCtc.append(i+1+":  ");
              stnewCtc.append( newcontacts.get(i).toString());
              stnewCtc.append("\n");
       }
       rec_tvER2.setText(stnewCtc);
       }
       else {
           Log.i("empty", "onCreateView: ");
       }
   }
}
