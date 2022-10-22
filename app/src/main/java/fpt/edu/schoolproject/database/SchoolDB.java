package fpt.edu.schoolproject.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SchoolDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "dbSchool";
    public static final int DB_VERSION = 1;
    public static final String TABLE_USER = "CREATE TABLE USER(" +
            "userName text PRIMARY KEY NOT NULL," +
            "fullName text NOT NULL," +
            "password text NOT NULL" +
            ")";
    public static final String TABLE_KHOAHOC = "CREATE TABLE KhoaHoc (" +
            "maKhoaHoc INTEGER PRIMARY KEY AUTOINCREMENT," +
            "tenKhoaHoc TEXT NOT NULL," +
            "giaKhoaHoc INTEGER NOT NULL," +
            " trangThai INTEGER)";
    public SchoolDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_USER);
        sqLiteDatabase.execSQL(Data_Sql.INSERT_USER);
        sqLiteDatabase.execSQL(TABLE_KHOAHOC);
        sqLiteDatabase.execSQL(Data_Sql.INSERT_KHOAHOC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTableUser = "DROP TABLE IF EXISTS USER";
        sqLiteDatabase.execSQL(dropTableUser);
        String dropTableKhoaHoc = "DROP TABLE IF EXISTS KhoaHoc";
        sqLiteDatabase.execSQL(dropTableKhoaHoc);
    }
}
