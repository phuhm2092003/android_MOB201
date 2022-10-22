package fpt.edu.schoolproject.ui.khoahoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;

import fpt.edu.schoolproject.R;
import fpt.edu.schoolproject.adapter.KhoaHocAdapter;
import fpt.edu.schoolproject.dao.KhoaHocDao;
import fpt.edu.schoolproject.service.DangKyKhoaHocService;

public class KhoaHocDaDangKyActivity extends AppCompatActivity {
    Toolbar toolbar;
    IntentFilter intentFilter;
    ListView lv;
    KhoaHocDao khoaHocDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoa_hoc_da_dang_ky);
        toolbar = findViewById(R.id.tbDaDKKhoaHoc);
        setSupportActionBar(toolbar);
        lv = findViewById(R.id.lvKhoaHoc);
        loadData();
    }

    private void loadData() {
        khoaHocDao = new KhoaHocDao(this);
        KhoaHocAdapter khoaHocAdapter = new KhoaHocAdapter(this, khoaHocDao.getKhoaHocRegistered(), khoaHocDao);
        khoaHocAdapter.notifyDataSetChanged();
        lv.setAdapter(khoaHocAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        intentFilter = new IntentFilter();
        intentFilter.addAction(DangKyKhoaHocService.REGISTER);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcastReceiver);
    }

    private final BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case DangKyKhoaHocService.REGISTER:
                    boolean check = intent.getExtras().getBoolean("check", true);
                    if (check) {
                        loadData();
                    }
                    break;
            }
        }
    };
}