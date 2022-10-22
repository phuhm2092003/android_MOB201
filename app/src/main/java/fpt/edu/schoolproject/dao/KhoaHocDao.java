package fpt.edu.schoolproject.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpt.edu.schoolproject.database.SchoolDB;
import fpt.edu.schoolproject.model.KhoaHoc;

public class KhoaHocDao {
    SchoolDB schoolDB;

    public KhoaHocDao(Context context) {
        this.schoolDB = new SchoolDB(context);
    }

    @SuppressLint("Range")
    public ArrayList<KhoaHoc> get(String sql, String...choose){
        ArrayList<KhoaHoc> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = schoolDB.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, choose);
        if(cursor.getCount() > 0){
            cursor.moveToNext();
            do {
                KhoaHoc khoaHoc = new KhoaHoc();
                khoaHoc.setMaKhoaHoc(cursor.getInt(cursor.getColumnIndex("maKhoaHoc")));
                khoaHoc.setTenKhoaHoc(cursor.getString(cursor.getColumnIndex("tenKhoaHoc")));
                khoaHoc.setGiaKhoaHoc(cursor.getInt(cursor.getColumnIndex("giaKhoaHoc")));
                khoaHoc.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));
                list.add(khoaHoc);
                Log.i("TAG", khoaHoc.toString());
            }while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<KhoaHoc> getAll(){
        String sql = "SELECT * FROM KhoaHoc";
        return get(sql);
    }
    public KhoaHoc getByMaKhoaHoc(String maKhoaHoc){
        String sql = "SELECT * FROM KhoaHoc WHERE maKhoaHoc=?";
        ArrayList<KhoaHoc> list = get(sql, maKhoaHoc);
        return list.get(0);
    }

    public ArrayList<KhoaHoc> getKhoaHocRegistered(){
        String sql = "SELECT * FROM KhoaHoc WHERE trangThai =?";
        return get(sql, "0");
    }

    public boolean updateKhoaHoc(KhoaHoc khoaHoc){
        ContentValues values = new ContentValues();
        values.put("trangThai", khoaHoc.getTrangThai());
        SQLiteDatabase sqLiteDatabase = schoolDB.getWritableDatabase();
        int check = sqLiteDatabase.update("KhoaHoc", values, "maKhoaHoc=?", new String[]{String.valueOf(khoaHoc.getMaKhoaHoc())});
        return check > 0;
    }
}
