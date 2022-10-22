package fpt.edu.schoolproject.ui.khoahoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import fpt.edu.schoolproject.R;
import fpt.edu.schoolproject.adapter.KhoaHocAdapter;
import fpt.edu.schoolproject.dao.KhoaHocDao;
import fpt.edu.schoolproject.model.KhoaHoc;
import fpt.edu.schoolproject.service.DangKyKhoaHocService;

public class DangKyKhoaHocActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView lvKhoaHoc;
    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_khoa_hoc);
        toolbar = findViewById(R.id.tbDKKhoaHoc);
        setSupportActionBar(toolbar);
        lvKhoaHoc = findViewById(R.id.lvKhoaHoc);

        loadData();
    }

    private void loadData() {
        KhoaHocDao khoaHocDao = new KhoaHocDao(this);
        KhoaHocAdapter khoaHocAdapter = new KhoaHocAdapter(this, khoaHocDao.getAll(), khoaHocDao);
        khoaHocAdapter.notifyDataSetChanged();
        lvKhoaHoc.setAdapter(khoaHocAdapter);

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
            switch (intent.getAction()){
                case DangKyKhoaHocService.REGISTER:
                    boolean check = intent.getExtras().getBoolean("check", true);
                    if(check){
                        loadData();
                    }
                    break;
            }
        }
    };
}