package com.example.felizmente.db;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControladorDB extends SQLiteOpenHelper {

    public ControladorDB(Context context) {
        super(context, "com.example.felizmente.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS (ID_USER INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_EMAIL TEXT NOT NULL, PASS TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void addUser(String email, String pass) {

        ContentValues registro = new ContentValues();
        registro.put("USER_EMAIL", email);
        registro.put("PASS", pass);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("USERS", null, registro);
        db.close();
    }

    public boolean userExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = new String[]{email};
        Cursor cursor = db.rawQuery("SELECT * FROM USERS WHERE USER_EMAIL=?", args);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public String login(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = new String[] {email, password};

        Cursor cursor = db.rawQuery("SELECT USER_EMAIL FROM USERS WHERE USER_EMAIL=? AND PASS=?", args);

        cursor.moveToFirst();

        String result = cursor.getString(0);

        cursor.close();

        return result;
    }


}