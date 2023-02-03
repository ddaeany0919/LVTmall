package com.lvtmall.registeration16.Register;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.lvtmall.registeration.R;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;


    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        final EditText crexidText = (EditText) findViewById(R.id.crexidText);
        final EditText emailpwdText = (EditText) findViewById(R.id.emailpwdText);
        final EditText crexpwdText = (EditText) findViewById(R.id.crexpwdText);
        final EditText passwordText1 = (EditText) findViewById(R.id.passwordText1);

        /*회원가입시 아이디가 사용가능한지 검증하는 부분
        문제가 하나라도 발생시 생길수 있는 소스 구현
        중복체크 버튼 구현 및 빈칸일때 오류 구현*/
        final Button validateButton = (Button) findViewById(R.id.validateButton);//초기화 진행 vaildteButton 다지인 액티비티 매칭
        validateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String m_id = idText.getText().toString();
                if (validate)//중복체크가 되있다면 바로 종료시킨다
                {
                    return;//검증 완료
                }
                //ID 값을 입력하지 않았다면(아무런 내용이 없다면 오류를 실행시킨다)
                if(m_id.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                //검증시작(정상적으로 아이디를 입력했다면(서버로부터 여기서 데이터를 받음))
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                        //중복체크에 성공하였다면
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){//사용할 수 있는 아이디라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);//아이디값을 바꿀 수 없도록 함
                                validate = true;//검증완료
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));

                            }else{//사용할 수 없는 아이디라면(중복체크에 실패 하였다면)
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                };//Response.Listener 완료
                //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분(실지적으로 접속 할수 있도록 생성자를 하나 만들어 준다.)
                ValidateRequest validateRequest = new ValidateRequest(m_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_id = idText.getText().toString();
                String m_pwd= passwordText.getText().toString();
                String name = nameText.getText().toString();
                String m_hp = phoneNumber.getText().toString();
                String m_email = crexidText.getText().toString();
                String m_emailpwd = emailpwdText.getText().toString();
                String m_crexpwd = crexpwdText.getText().toString();

                //중복체크를 하지 않았다면 중복 체크해달라는 메세지 구현
                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요 .")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                //그리고 하나라도 빈공간이 있다면
                if ( !passwordText.getText().toString().equals(passwordText1.getText().toString()) ) {
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    passwordText.setText("");
                    passwordText1.setText("");
                    passwordText.requestFocus();
                    return;
                }

                if(m_id.equals("") || m_pwd.equals("") ||name.equals("")||m_hp.equals("")||m_email.equals("")||m_emailpwd.equals("")||m_crexpwd.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 실패했습니다.")
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
                RegisterRequest registerRequest= new RegisterRequest(m_id,m_pwd,m_hp,name,m_email,m_emailpwd,m_crexpwd,
                        responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
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
}
