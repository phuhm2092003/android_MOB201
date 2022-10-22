package fpt.edu.schoolproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import fpt.edu.schoolproject.dao.KhoaHocDao;
import fpt.edu.schoolproject.model.KhoaHoc;
import fpt.edu.schoolproject.notification.MyNotification;

public class DangKyKhoaHocService extends Service {

    public static final String REGISTER = "Register";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KhoaHocDao khoaHocDao = new KhoaHocDao(this);
        Bundle bundle = intent.getExtras();
        KhoaHoc khoaHoc  = khoaHocDao.getByMaKhoaHoc(bundle.getString("maKhoaHoc"));
        if(khoaHoc.getTrangThai() == 1){
            // chưa đăng ký
            khoaHoc.setTrangThai(0);
            MyNotification.checkSDK(this);
            MyNotification.getNotification(this, "Đăng ký khoá học thành công");
        }else {
            // huỷ đăng ký
            khoaHoc.setTrangThai(1);
            MyNotification.checkSDK(this);
            MyNotification.getNotification(this, "Huỷ khoá học thành công");
        }
        boolean check = khoaHocDao.updateKhoaHoc(khoaHoc);

        // send broadcast
        Intent intentBR = new Intent();
        Bundle bundleBR = new Bundle();
        bundleBR.putBoolean("check", check);
        intentBR.setAction(REGISTER);
        intentBR.putExtras(bundleBR);
        sendBroadcast(intentBR);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "onDestroy: DangKyKhoaHoc");
    }
}
