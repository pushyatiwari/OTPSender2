package coml.example.android.otpsender.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

import coml.example.android.otpsender.R;

public class MyAdapter extends SimpleCursorAdapter {
    Context context;
    MyHelper myHelperAdp;
    String[] data;
    private SQLiteDatabase mSqldbAdp;
    private SimpleCursorAdapter myCursorAdapter;
    private int[] position;


    public MyAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        data = from;
        position = to;
        myCursorAdapter = new SimpleCursorAdapter(context, R.layout.edit_adapter, c, data, position, 0
        );

    }
}

