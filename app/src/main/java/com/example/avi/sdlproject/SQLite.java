package com.example.avi.sdlproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {

    private static final String Database_name="Sdlproject.db";
    private static final String Table_name="Alarm";
    private static final String col_0="ID";
    private static final String col_1="Hour";
    private static final String col_2="Min";
    private static final String col_3="Sound_Type";
    private static final String col_4="Ringtone";
    //private static final String col_5="Snooze_Time";
    private static final String col_5="Dismiss_Alarm_By";

    public SQLite(Context context) {
        super(context, Database_name , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_name + "(ID INTEGER primary key,Hour INTEGER,Min INTEGER,Sound_Type text,Ringtone text,Dismiss_Alarm_By text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+Table_name);
        onCreate(db);
    }
    public boolean insertData(Integer ID,Integer Hour,Integer Min,String Sound_type,String Ringtone,String Dismiss_Alarm_By){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col_0,ID);
        contentValues.put(col_1,Hour);
        contentValues.put(col_2,Min);
        contentValues.put(col_3,Sound_type);
        contentValues.put(col_4,Ringtone);
        //contentValues.put(col_5,Snooze_Time);
        contentValues.put(col_5,Dismiss_Alarm_By);
        if(db.insert(Table_name,null,contentValues)==-1) return false;
        return true;
    }

    public Cursor getALLData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + Table_name, null);
        return res;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(Table_name,"ID = ?",new String[] {id});
    }
}