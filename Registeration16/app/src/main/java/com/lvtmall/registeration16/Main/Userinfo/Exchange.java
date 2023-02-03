package com.lvtmall.registeration16.Main.Userinfo;

import android.content.Intent;
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
import org.json.JSONObject;



public class Exchange extends AppCompatActivity {
    TextView  buyPay;
    EditText exchangePay;
    Button exchangePreveal;
    TextView usecoin;
    public static String m_id;
    public static String name;
    public static String adress;
    public static String hp;
    public static String api;
    public static String secret;
    public static String email;
    public static String point;
    public static String coin;
    public static String amount;
    private AlertDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        point=getIntent().getStringExtra("point");//LoginActivity에서 받아온 m_id값을 MainActivity있는 모든 엑티비티에 m_id값을 줌
        m_id=getIntent().getStringExtra("m_id");
        name=getIntent().getStringExtra("name");
        adress=getIntent().getStringExtra("adress");
        hp=getIntent().getStringExtra("hp");
        api=getIntent().getStringExtra("api");
        secret=getIntent().getStringExtra("secret");
        email=getIntent().getStringExtra("email");
        coin=getIntent().getStringExtra("coin");


        setContentView(R.layout.activity_exchange);


        exchangePreveal= (Button)findViewById(R.id.exchangePreveal);
        final EditText exchangePay = (EditText)findViewById(R.id.exchangePay);
        final TextView buyPay = (TextView)findViewById(R.id.buyPay);
        usecoin = (TextView)findViewById(R.id.usecoin);

        final String coin = intent.getStringExtra("coin");//사용가능한 LVT개수
        usecoin.setText(coin+"LVT");

        exchangePreveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str1 = exchangePay.getText().toString();
                Integer num1 = Integer.parseInt(str1);
                Integer exchangePay1 = num1*100;
                String strexchangepay = Integer.toString(exchangePay1);
                buyPay.setText(strexchangepay);
            }



        });


        Button exchangeButton = (Button) findViewById(R.id.exchangeButton);
        exchangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = exchangePay.getText().toString();
                String point = buyPay.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success==true)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Exchange.this);
                                dialog = builder.setMessage("전환에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                            else if(success ==false )
                            {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Exchange.this);
                                    dialog = builder.setMessage("잔액이 부족합니다.")
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
                ExchangeRequest ExchangeRequest= new ExchangeRequest(m_id,point,amount,name,hp,api,secret, responseListener);

                RequestQueue queue = Volley.newRequestQueue(Exchange.this);
                queue.add(ExchangeRequest);
            }
        });
    }
}
