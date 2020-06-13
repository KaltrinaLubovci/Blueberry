package com.kl.blueberry.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.kl.blueberry.model.register_user.User;

public class Database extends SQLiteOpenHelper {
    public static final String UserTable = "user";
    public Database(@Nullable Context context) {
        super(context, "BLUEBERRYDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strQuery = "create table "+UserTable+" (" +
                User.ID + " integer primary key autoincrement,"+
                User.Fullname +" text not null,"+
                User.Username+" text not null,"+
                User.Email+" text,"+
                User.Password+" text not null"+
                ")";

        db.execSQL(strQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
