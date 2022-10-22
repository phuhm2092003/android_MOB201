package fpt.edu.schoolproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Transition;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fpt.edu.schoolproject.adapter.ViewPagerAdapter;
import fpt.edu.schoolproject.database.SchoolDB;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bnvMenu;
    private ViewPager2 vpMain;
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        SchoolDB schoolDB = new SchoolDB(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = schoolDB.getReadableDatabase();
        sqLiteDatabase.close();
        adapter = new ViewPagerAdapter(this);
        vpMain.setAdapter(adapter);
        bnvMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.khoa_hoc:
                        vpMain.setCurrentItem(0, false);
                        break;
                    case R.id.tin_tuc:
                        vpMain.setCurrentItem(1, false);
                        break;
                    case R.id.ban_do:
                        vpMain.setCurrentItem(2, false);
                        break;
                    case R.id.xa_hoi:
                        vpMain.setCurrentItem(3, false);
                        break;
                }
                return true;
            }
        });

    }

    private void initView() {
        bnvMenu = findViewById(R.id.bnvMenu);
        vpMain = findViewById(R.id.vpMain);
        vpMain.setUserInputEnabled(false);
    }

    @Override
    public void onBackPressed() {
        vpMain.setCurrentItem(0);
        bnvMenu.getMenu().findItem(R.id.khoa_hoc).setChecked(true);
    }

}