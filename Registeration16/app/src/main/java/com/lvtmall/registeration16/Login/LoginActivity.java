package com.lvtmall.registeration16.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.lvtmall.registeration.R;
import com.lvtmall.registeration16.Main.Main.MainActivity;
import com.lvtmall.registeration16.Register.RegisterActivity;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String m_id = idText.getText().toString();//메인 엑티비티로 id값을 넘겨줄때 final써야된다.
                final String m_pwd = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            dialog = builder.setMessage("로그인에 성공했습니다.")
                                    .setPositiveButton("확인",null)
                                    .create();
                            dialog.show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                               intent.putExtra("m_id",m_id);//MainActivity로 id값을 넘겨줌
                                intent.putExtra("m_pwd",m_pwd);
                            LoginActivity.this.startActivity(intent);
                            finish();
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            dialog = builder.setMessage("계정을 다시 확인 하세요.")
                                    .setNegativeButton("다시 시도", null)
                                    .create();
                            dialog.show();
                        }
                    }
                        catch (Exception e)

                    {
                        e.printStackTrace();
                    }
                        }
                };
                LoginRequest loginRequest = new LoginRequest(m_id, m_pwd ,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog =null;
        }
    }
}
