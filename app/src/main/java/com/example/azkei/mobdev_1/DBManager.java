package com.example.azkei.mobdev_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by azkei on 21/11/2016.
 */

public class DBManager {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Shopping.db";
    private static final String TABLE_ACCOUNTS = "Accounts";
    private static final String TABLE_SHOPPING_LIST = "Shopping_List";

    //add more fields here for your table
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";

    private static final String KEY_CUSTID = "customer_id";
    private static final String KEY_ITEM = "item";
    private static final String KEY_COST = "cost";
    private static final String KEY_AMOUNT = "amount";

    //CREATE TABLE ACCOUNTS
    private static final String CREATE_TABLE1 = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNTS+
            "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "password TEXT" +
            ");";

    //CREATE TABLE LIST
    private static final String CREATE_TABLE2 = "CREATE TABLE IF NOT EXISTS " +TABLE_SHOPPING_LIST+
            "("+
            "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "customer_id INT,"+
            "item TEXT,"+
            "cost TEXT,"+
            "amount TEXT,"+
            "FOREIGN KEY(customer_id) REFERENCES Accounts(_id)"+
            ");";

    private final Context context;
    private MyDatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        this.context = context;
        DBHelper = new MyDatabaseHelper(context);
    }


    private static class MyDatabaseHelper extends SQLiteOpenHelper{

        public MyDatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE1);
            db.execSQL(CREATE_TABLE2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST);
            onCreate(db);
        }
    }

    public DBManager open() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        DBHelper.close();
    }

    public long insertAccount(String username, String password){
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME,username);
        initialValues.put(KEY_PASSWORD,password);
        return db.insert(TABLE_ACCOUNTS, null, initialValues);
    }
    public long insertList(int id,String item, String cost,String amount){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CUSTID,id);
        initialValues.put(KEY_ITEM,item);
        initialValues.put(KEY_COST,cost);
        initialValues.put(KEY_AMOUNT,amount);
        return db.insert(TABLE_SHOPPING_LIST, null, initialValues);
    }

    public Cursor getAll(String query){
        db = DBHelper.getWritableDatabase();
        Cursor mCursor = db.rawQuery(query, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    //brings in a sql query and returns result
    public Cursor searchAccount(String query){
        db = DBHelper.getWritableDatabase();
        String str = null;
        Cursor res = db.rawQuery(query,null);
        if(res != null){
            res.moveToFirst();
            //str = res.getString(res.getColumnIndex(KEY_NAME));
        }
        return res;
    }
    public Cursor searchPassword(String query){
        db = DBHelper.getWritableDatabase();
        //String str = null;
        Cursor res = db.rawQuery(query,null);
        if(res != null){
            res.moveToFirst();
            //str = res.getString(res.getColumnIndex(KEY_NAME));
        }
        return res;
    }
    public Cursor searchID(String query){
        db = DBHelper.getWritableDatabase();
        //int str = 0;
        Cursor res = db.rawQuery(query,null);

        if(res != null){
            res.moveToFirst();
            //str = res.getInt(res.getColumnIndex(KEY_ID));
        }
        return res;
    }
}
