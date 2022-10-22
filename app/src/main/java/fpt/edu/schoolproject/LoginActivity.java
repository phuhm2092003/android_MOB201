package fpt.edu.schoolproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import fpt.edu.schoolproject.dao.UserDao;
import fpt.edu.schoolproject.notification.MyNotification;
import fpt.edu.schoolproject.service.CheckLoginService;

public class LoginActivity extends AppCompatActivity {
    Button signIn, skip, loginFb;
    TextInputLayout tilUserName, tilPassword;
    UserDao userDao;
    IntentFilter intentFilter;
    CheckBox chkRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        skip = findViewById(R.id.btnSkip);
        signIn = findViewById(R.id.btnSignIn);
        tilUserName = findViewById(R.id.tilUserName);
        tilPassword = findViewById(R.id.tilUserPassword);
        chkRemember = findViewById(R.id.chkRememberUser);
        loginFb = findViewById(R.id.btnSignInFb);
        userDao = new UserDao(this);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScreenWelcome();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getText(tilUserName);
                String paswrod = getText(tilPassword);
                Intent myIntent = new Intent(LoginActivity.this, CheckLoginService.class);
                Bundle bundle = new Bundle();
                bundle.putString("BUNDLE_USERNAME", username);
                bundle.putString("BUNDLE_PASSWORD", paswrod);
                myIntent.putExtras(bundle);
                startService(myIntent);
            }
        });

        loginFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Đăng nhập fb thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startScreenWelcome() {
        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @NonNull
    private String getText(TextInputLayout tilUserName) {
        return Objects.requireNonNull(tilUserName.getEditText()).getText().toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        intentFilter = new IntentFilter();
        intentFilter.addAction(CheckLoginService.ACTION_LOGIN);
        registerReceiver(myBroadcast, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadcast);
    }



    public BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(CheckLoginService.ACTION_LOGIN)){
                Bundle bundle = intent.getExtras();
                boolean check = bundle.getBoolean("BUNDLE_CHECK");
                if(check){
                    context.startActivity(new Intent(context, MainActivity.class));
                    Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    MyNotification.checkSDK(context);
                    MyNotification.getNotification(context, "Đăng nhập hệ thống thành công");
                }else {
                    Toast.makeText(context, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


}