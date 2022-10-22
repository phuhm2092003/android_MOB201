package fpt.edu.schoolproject.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpt.edu.schoolproject.database.SchoolDB;

public class UserDao {
    SchoolDB schoolDB;

    public UserDao(Context context) {
        this.schoolDB = new SchoolDB(context);
    }

    public boolean checkLogin(String username, String password){
        SQLiteDatabase sqLiteDatabase = schoolDB.getReadableDatabase();
        String sqlLogin = "SELECT * FROM USER WHERE userName=? AND password=?";
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sqlLogin, new String[]{username, password});
        return cursor.getCount() != 0;
    }
}
