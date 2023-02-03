package com.lvtmall.registeration16.Main.Userinfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.lvtmall.registeration16.Main.Main.MainActivity.m_id;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.lvtmall.registeration.R;
import com.lvtmall.registeration16.Register.RegisterActivity;


import org.json.JSONObject;

public class Changeinfo extends AppCompatActivity {
    TextView Adress; TextView Adress1;private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeinfo);

        Button findAdressButton = (Button)findViewById(R.id.findAdressButton);
        Button fixButton = (Button) findViewById(R.id.fixButton);
        final TextView idText = (TextView)findViewById(R.id.idText);
        final EditText changepasswordText = (EditText)findViewById(R.id.changepasswordText);
        final EditText changepasswordText1 = (EditText) findViewById(R.id.changepasswordText1);
        final EditText findAdress2 = (EditText)findViewById(R.id.findAdress2);
        final EditText phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        final EditText emailText = (EditText)findViewById(R.id.emailText);
        Adress = (TextView)findViewById(R.id.findAdress);
        Adress1 = (TextView)findViewById(R.id.findAdress1);

        idText.setText(m_id);
        findAdressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Changeinfo.this, findAdress.class);
                startActivityForResult(intent, 0);
            }
       });

        fixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_pwd = (String) changepasswordText.getText().toString();
                String addr1 = (String) Adress.getText().toString();
                String addr2 = (String) Adress1.getText().toString();
                String addr3 = (String) findAdress2.getText().toString();
                String phone1 = (String) phoneNumber.getText().toString();
                String email1 = (String) emailText.getText().toString();

                if ( !changepasswordText.getText().toString().equals(changepasswordText1.getText().toString()) ) {
                    Toast.makeText(Changeinfo.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    changepasswordText.setText("");
                    changepasswordText1.setText("");
                    changepasswordText.requestFocus();
                    return;
                }
                if(m_pwd.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Changeinfo.this);
                    dialog = builder.setMessage("비밀번호를 입력해주세요.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                //아무런 문제가 없을경우 정상적으로 회원가입 구현
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Changeinfo.this);
                                dialog = builder.setMessage("회원정보를 수정 하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Changeinfo.this);
                                dialog = builder.setMessage("회원정보를 수정 하지 못했습니다.")
                                        .setNegativeButton("확인",null)
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
                ChangeinfoRequest changeinfoRequest= new ChangeinfoRequest(m_id, m_pwd,addr1,addr2,addr3,phone1,email1,
                        responseListener);
                RequestQueue queue = Volley.newRequestQueue(Changeinfo.this);
                queue.add(changeinfoRequest);
            }
        });

    }
    @Override
    protected  void onStop() {
        super.onStop();
        if(dialog !=null)
        {
            dialog.dismiss();
            dialog= null;
        }
    }

    @Override
    protected  void onActivityResult(int requestcode,int resultcode, Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        if (requestcode==0){
            if(resultcode == RESULT_OK){
                Bundle bundle = data.getExtras();
                String txt_address0 = bundle.getString("txt_address0");
                String txt_address2 = bundle.getString("txt_address2");
                Adress.setText(txt_address0);
                Adress1.setText(txt_address2);
            }
        }
    }

}