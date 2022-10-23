package fpt.edu.schoolproject.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpt.edu.schoolproject.database.SchoolDB;
import fpt.edu.schoolproject.model.User;

public class UserDao {
    SchoolDB schoolDB;

    public UserDao(Context context) {
        this.schoolDB = new SchoolDB(context);
    }

    @SuppressLint("Range")
    public ArrayList<User> get(String sql, String... choose) {
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = schoolDB.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, choose);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                User user = new User();
                user.setUsername(cursor.getString(cursor.getColumnIndex("userName")));
                user.setFullname(cursor.getString(cursor.getColumnIndex("fullName")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                list.add(user);
                Log.i("TAG", user.toString());
            } while (cursor.moveToNext());
        }

        return list;
    };

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase sqLiteDatabase = schoolDB.getReadableDatabase();
        String sqlLogin = "SELECT * FROM USER WHERE userName=? AND password=?";
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sqlLogin, new String[]{username, password});
        return cursor.getCount() != 0;
    }

    public ArrayList<User> getAll(){
        String sql = "SELECT * FROM USER";
        return get(sql);
    }

    public boolean insertUser(User user){
        ContentValues values = new ContentValues();
        values.put("userName", user.getUsername());
        values.put("fullName", user.getFullname());
        values.put("password", user.getPassword());
        SQLiteDatabase sqLiteDatabase = schoolDB.getWritableDatabase();
        long check = sqLiteDatabase.insert("USER", null, values);
        return check != -1;
    }
}
