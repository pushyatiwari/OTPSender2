package coml.example.android.otpsender.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table OTPReceivers(_id integer primary key,phone text,message text )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "OTPReceivers");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public boolean insertData(String phone, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone", phone);
        contentValues.put("message", text);
        long result = db.insert("OTPReceivers", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        Cursor res = null;
        try{
        SQLiteDatabase db = this.getWritableDatabase();
         res = db.rawQuery("select * from " + "OTPReceivers", null);}
        catch(Exception e){
            e.printStackTrace();
        }


        return res;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("OTPReceivers", "_id = ?", new String[]{id});
    }

    public Integer deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from OTPReceivers");
        return 0;
    }
}

