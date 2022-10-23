package fpt.edu.schoolproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.schoolproject.dao.UserDao;
import fpt.edu.schoolproject.model.User;
import fpt.edu.schoolproject.notification.MyNotification;

public class RegisterUserActivity extends AppCompatActivity {
    ImageButton back;
    Button register;
    TextInputLayout username, fullname, password;
    UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        back = findViewById(R.id.imgback);
        register = findViewById(R.id.register);
        username = findViewById(R.id.userName);
        fullname = findViewById(R.id.fullName);
        password = findViewById(R.id.password);
        userDao = new UserDao(this);
        back.setOnClickListener(view -> onBackPressed());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = Objects.requireNonNull(username.getEditText()).getText().toString();
                String fullName = Objects.requireNonNull(fullname.getEditText()).getText().toString();
                String passwords = Objects.requireNonNull(password.getEditText()).getText().toString();
                if(userName.isEmpty() || fullName.isEmpty() || passwords.isEmpty()){
                    Toast.makeText(RegisterUserActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if(userDao.insertUser(new User(userName, fullName, passwords))){
                        notifiRegister();
                    }else {
                        Toast.makeText(RegisterUserActivity.this, "Tên đăng nhập tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void notifiRegister() {
        MyNotification.checkSDK(RegisterUserActivity.this);
        MyNotification.getNotification(RegisterUserActivity.this, "Đăng ký tài khoản thành công");
        Toast.makeText(RegisterUserActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}