package coml.example.android.otpsender.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;

import coml.example.android.otpsender.Activities.MainActivity;
import coml.example.android.otpsender.Utils.MyHelper;
import coml.example.android.otpsender.R;
import coml.example.android.otpsender.Utils.objectSerializer;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditReceivers extends Fragment {
    public static MyHelper mMyHelperReg ;
    private String[] data;
    public EditReceivers() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_receivers, container, false);
        Button show = (Button) view.findViewById(R.id.show);
        Button showTab = (Button) view.findViewById(R.id.showTable);
        final InsideER1 insideER1 = new InsideER1();
        Button clear = (Button) view.findViewById(R.id.clear);
        show.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
MainActivity mainActivity2 = (MainActivity) getActivity();
           mainActivity2.sendshowctctoER2();




            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> clear1 = new ArrayList<>();

                SharedPreferences prefs = getActivity().getSharedPreferences("coml.example.android.otpsender", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();


                try {

                    if(clear1!=null) {

                        clear1 = (ArrayList<String>) objectSerializer.deserialize(prefs
                                .getString("contacts", objectSerializer.serialize(new ArrayList<String>())));
                        editor.clear();
                        editor.commit();

                        if(clear1!=null) {
                            for(int i =0;i<clear1.size();i++) {

                                Log.i("Contacts", clear1.get(i));
                            }}
                        else{
                            Log.i("empty", "onclear");
                        }

                    }else {
                        Log.i("empty", "onCreateView: ");
                    }
                    InsideER2 insideER2ER =  new InsideER2();
                    android.support.v4.app.FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                    fragmentManager2.beginTransaction()
                            .replace(R.id.frame_layout,insideER2ER )
                            .commit();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });




        try {



            showTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MainActivity mMainActivity = (MainActivity) getActivity();

                    mMainActivity.sendtoInsideER1();



                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

}
