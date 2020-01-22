package coml.example.android.otpsender.Fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import coml.example.android.otpsender.Utils.MyAdapter;
import coml.example.android.otpsender.Utils.MyHelper;
import coml.example.android.otpsender.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment implements Runnable {


    ListView listView;
    private MyHelper mMyHelper3;
    SQLiteDatabase mSQLDB3;
    public MyAdapter mMyAdapter;
    private String[] data;
    private int[] position1;
    private Button clear_btn;
    private TextView His_tv;
     Cursor cursorAdp;

    public History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        clear_btn = (Button) view.findViewById(R.id.clear_btn);
        His_tv = (TextView) view.findViewById(R.id.his_tv);
        mMyHelper3 = new MyHelper(getActivity(), "OTPReceivers", null, 1);
        mSQLDB3 = mMyHelper3.getWritableDatabase();
        cursorAdp = mMyHelper3.getAllData();
        if (cursorAdp.getCount() == 0) {
            // show message
            Log.i("nothing", "onCreate: edt");

        } else {
                    populateTv();
        }


        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mMyHelper3.deleteData();
                    mMyAdapter.notifyDataSetChanged();
                    populateTv();
                    InsideER1.buffer.delete(0,cursorAdp.getCount());
                }catch (Exception e){
                    Toast.makeText(getActivity(), "no history", Toast.LENGTH_SHORT).show();
                }
               // His_tv.setAlpha(1);
                update();
//                    for(int i=0;i<data.length;i++) {
//                        mMyHelper3.deleteData(String.valueOf(i));


            }
        });


        return view;
    }

    public void update() {
        History history = new History();
        Thread historyThrd = new Thread(history);

        historyThrd.start();


    }


    @Override
    public void run() {
        mMyHelper3 = new MyHelper(getActivity(), "OTPReceivers", null, 1);
        final Cursor cursorAdp = mMyHelper3.getAllData();
        try {
            if (cursorAdp.getCount() == 0) {
                // show message
                Log.i("nothing", "onCreate: edt");

            } else {

                cursorAdp.moveToFirst();
                data = new String[]{cursorAdp.getColumnName(0),
                        cursorAdp.getColumnName(1),
                        cursorAdp.getColumnName(2)};
                position1 = new int[]{R.id.id_tv, R.id.phone, R.id.body};
                mMyAdapter = new MyAdapter(getActivity(), R.layout.edit_adapter, cursorAdp, data, position1, 0);
                listView.setAdapter(mMyAdapter);
                mMyAdapter.notifyDataSetChanged();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void populateTv(){

        cursorAdp.moveToFirst();
        data = new String[]{cursorAdp.getColumnName(0),
                cursorAdp.getColumnName(1),
                cursorAdp.getColumnName(2)};
        position1 = new int[]{R.id.id_tv, R.id.phone, R.id.body};
        mMyAdapter = new MyAdapter(getActivity(), R.layout.edit_adapter, cursorAdp, data, position1, 0);
        listView.setAdapter(mMyAdapter);
        if(listView.getCount()==0){
            His_tv.setAlpha(1);
        }else{
            His_tv.setAlpha(0);
        }
    }
}