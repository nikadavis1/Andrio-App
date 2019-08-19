package com.example.utabazaarsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME= "register.db";

    private static final int DATABASE_VERSION = 14;

    //Table Names
    private static final String TABLE_NAME = "registerUser";
    private static final String TABLE_GROUP = "groupName";
    private static final String TABLE_GROUP_JOIN = "groupJoin";
    private static final String TABLE_GROUP_MESSAGE = "groupMessage";


    //common column names
    private static final String COL_1 = "ID";

    //register column names
    private static final String USERNAME = "userName";
    private static final String COL_3 = "passWord";

    //Group column names
   private static final String GROUP_NAME = "Name";
    private static final String GROUP_DES = "Description";

    //Group message column
    private static final String GROUP_MESSAGE = "Message";


    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_NAME +
            "(" + COL_1 + " INTEGER PRIMARY KEY, " + USERNAME + " TEXT, " + COL_3 +
            " TEXT" + ")";

    private static final String CREATE_TABLE_GROUP = "CREATE TABLE " + TABLE_GROUP +
            "(" + COL_1 + " INTEGER PRIMARY KEY, " + GROUP_NAME + " TEXT, " + GROUP_DES +
            " TEXT, " + USERNAME + " TEXT" + ")";

    private static final String CREATE_TABLE_GROUP_JOIN = "CREATE TABLE " + TABLE_GROUP_JOIN +
            "(" + GROUP_NAME + " TEXT, " + USERNAME + " TEXT" + ")";

    private static final String CREATE_TABLE_GROUP_MESSAGE = "CREATE TABLE " + TABLE_GROUP_MESSAGE +
            "(" + COL_1 + " INTEGER PRIMARY KEY, " + GROUP_NAME + " TEXT, " + USERNAME + " TEXT, " + GROUP_MESSAGE + " TEXT" + ")";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER); //create database
        sqLiteDatabase.execSQL(CREATE_TABLE_GROUP);
        sqLiteDatabase.execSQL(CREATE_TABLE_GROUP_JOIN);
        sqLiteDatabase.execSQL(CREATE_TABLE_GROUP_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_JOIN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_MESSAGE);
        onCreate(sqLiteDatabase);

    }

    //add user and password
    public long addUser(String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName",user);
        contentValues.put("passWord",password);
        long res = db.insert("registerUser",null,contentValues);
        db.close();
        return res;
    }


    //checks if username and password is in database
    public boolean checkUser(String username, String password){
        String [] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = USERNAME + "=?" + " and " + COL_3 + "=?";
        String [] selectionArgs = { username, password};
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return true;
        else
            return false;
    }

    //add group to databass
    public boolean addGroup(String group_name, String group_des){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GROUP_DES,group_des);
        contentValues.put(GROUP_NAME,group_name);
        Log.d("Debug",CREATE_TABLE_GROUP);

        long res = db.insert("groupName",null,contentValues);
        db.close();
        return res != -1;
    }

    //add member to group
    public boolean addMember(String group_mem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName",group_mem);
        long res = db.insert("groupName",null,contentValues);
        db.close();
        return res != -1;
    }



    //view all data in goup database
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " Select * from " + TABLE_GROUP;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public String get_group_info(String info){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+ "*" + " FROM groupName WHERE Name = '" + info + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String get_des=cursor.getString(2);
        return get_des;
    }


    public Cursor getItemID(String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_1 + " FROM " + TABLE_GROUP +
                " WHERE " + USERNAME + " = " + userName + " ";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    //delete user from group
    public boolean deleteUser( String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_GROUP +
                " WHERE " + USERNAME + " = " + userName + " ";
        db.execSQL(query);
        return true;
    }




}
