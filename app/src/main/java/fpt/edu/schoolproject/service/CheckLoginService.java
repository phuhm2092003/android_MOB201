package fpt.edu.schoolproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import fpt.edu.schoolproject.dao.UserDao;

public class CheckLoginService extends Service {
    public static final String ACTION_LOGIN ="action.login";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        String username = bundle.getString("BUNDLE_USERNAME");
        String password = bundle.getString("BUNDLE_PASSWORD");
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else {
            UserDao userDao = new UserDao(getApplicationContext());
            Log.i("TAG", "user size: "+userDao.getAll().size());
            boolean checkLogin = userDao.checkLogin(username, password);
            senData(checkLogin);
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    private void senData(boolean checkLogin) {
        Intent intentBr = new Intent();
        Bundle bundleBr = new Bundle();
        bundleBr.putBoolean("BUNDLE_CHECK", checkLogin);
        intentBr.putExtras(bundleBr);
        intentBr.setAction(ACTION_LOGIN);
        sendBroadcast(intentBr);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "onDestroy: CheckLoginService");
    }
}
