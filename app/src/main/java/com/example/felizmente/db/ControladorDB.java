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
                "NAME_USER TEXT NOT NULL, EMAIL TEXT NOT NULL, PASS TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void addUser(String user, String email, String pass) {

        ContentValues registro = new ContentValues();
        registro.put("NAME_USER", user);
        registro.put("EMAIL", email);
        registro.put("PASS", pass);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("USERS", null, registro);
        db.close();
    }

    public boolean userExists(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = new String[]{user};
        Cursor cursor = db.rawQuery("SELECT * FROM USERS WHERE NAME_USER=?", args);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public String login(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = new String[] {username, password};

        Cursor cursor = db.rawQuery("SELECT NAME_USER FROM USERS WHERE NAME_USER=? AND PASS=?", args);

        cursor.moveToFirst();

        cursor.close();

        return cursor.getString(0);
    }


}