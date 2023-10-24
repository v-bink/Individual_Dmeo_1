package com.example.individual_dmeo_1;


import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.individual_dmeo_1.dao.UserDao;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * function：连接页面加载首页
 */
public class MainActivity extends AppCompatActivity {
    private static final String PREF_NAME = "MyFirstTimePref";
    private static final String IS_FIRST_TIME = "is_first_time";
    private static String TAG = "信息打印";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("你好");
        // 获取 SharedPreferences 对象
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
//清除缓存
        SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        // 判断用户是否第一次使用
        if (sharedPreferences.getBoolean(IS_FIRST_TIME, true)) {
            // 第一次使用，执行这里的代码
            Log.i(TAG,"这是用户第一次使用该程序");
            Toast.makeText(getApplicationContext(), "这是用户第一次使用该程序", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_register);
            // 将第一次使用标记设置为 false
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean(IS_FIRST_TIME, false);
//            editor.apply();
        } else {
            // 非第一次使用，执行这里的代码
            Log.i(TAG,"这不是用户第一次使用该程序");
            Toast.makeText(getApplicationContext(), "这不是用户第一次使用该程序", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_login);
        }
    }

    public void reg(View view){
        startActivity(new Intent(getApplicationContext(),register.class));
    }


    /**
     * function: 登录
     * */
    public void login(View view){

        EditText EditTextAccount = findViewById(R.id.uesrAccount);
        EditText EditTextPassword = findViewById(R.id.userPassword);

        new Thread(){
            @Override
            public void run() {
                UserDao userDao = new UserDao();
                int msg = userDao.login(EditTextAccount.getText().toString(),EditTextPassword.getText().toString());
                hand1.sendEmptyMessage(msg);
            }
        }.start();

    }

    @SuppressLint("HandlerLeak")
    final Handler hand1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
            } else if (msg.what == 2){
                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
            } else if (msg.what == 3){
                Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG).show();
            }
        }
    };
}
