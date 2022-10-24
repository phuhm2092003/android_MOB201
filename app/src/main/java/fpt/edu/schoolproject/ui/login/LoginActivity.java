package fpt.edu.schoolproject.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Objects;

import fpt.edu.schoolproject.MainActivity;
import fpt.edu.schoolproject.R;
import fpt.edu.schoolproject.dao.UserDao;
import fpt.edu.schoolproject.notification.MyNotification;
import fpt.edu.schoolproject.service.CheckLoginService;

public class LoginActivity extends AppCompatActivity {
    Button signIn, skip, loginFb;
    TextInputLayout tilUserName, tilPassword;
    UserDao userDao;
    IntentFilter intentFilter;
    CallbackManager callbackManager;
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        skip = findViewById(R.id.btnSkip);
        signIn = findViewById(R.id.btnSignIn);
        tilUserName = findViewById(R.id.tilUserName);
        tilPassword = findViewById(R.id.tilUserPassword);
        loginFb = findViewById(R.id.btnSignInFb);
        tvRegister = findViewById(R.id.tvDangKy);
        callbackManager = CallbackManager.Factory.create();
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
        // đăng nhập facbook
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        MyNotification.checkSDK(LoginActivity.this);
                        MyNotification.getNotification(LoginActivity.this, "Đăng nhập hệ thống bằng Facebook thành công");
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        loginFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScreenRegister();
            }
        });
    }

    private void startScreenRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
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
                    overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                    Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    MyNotification.checkSDK(context);
                    MyNotification.getNotification(context, "Đăng nhập hệ thống thành công");
                }else {
                    Toast.makeText(context, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}