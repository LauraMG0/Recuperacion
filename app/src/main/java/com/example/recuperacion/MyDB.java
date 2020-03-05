package com.example.recuperacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDB{

    private MyDatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public final static String EMP_TABLE = "constants";

    public final static String EMP_ID="_id";
    public final static String EMP_NAME="name";
    public final static String EMP_QUANTITY="quantity";


    /**
     *
     * @param context
     */
    public MyDB(Context context){
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecords(int id, String name, String number){
        if (id == 0) {
            Cursor c = selectRecords();
            c.moveToLast();
            id = c.getInt(0)+1;
        }
        ContentValues values = new ContentValues();
        values.put(EMP_ID, id);
        values.put(EMP_NAME, name);
        values.put(EMP_QUANTITY, number);
        return database.insert(EMP_TABLE, null, values);
    }

    public void insert(){
        createRecords(1,"Manilow Bags","4");
        createRecords(2,"Christmas Tree","7");
    }

    public Cursor selectRecords() {
        String[] cols = new String[] {EMP_ID, EMP_NAME, EMP_QUANTITY};
        Cursor mCursor = database.query(true, EMP_TABLE,cols, null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public long update(int id, String name, String number){
        ContentValues values = new ContentValues();
        values.put(EMP_NAME,name);
        values.put(EMP_QUANTITY, number);
        return database.update(EMP_TABLE,values,"_id = ?",new String[]{String.valueOf(id)});
    }

    public void delete(int s){
        database.delete(EMP_TABLE, "_id = ?",new String[]{String.valueOf(s)});
    }
}