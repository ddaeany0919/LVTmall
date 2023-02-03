package com.lvtmall.registeration16.Main.Userinfo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lvtmall.registeration.R;

public class findAdress extends AppCompatActivity {

    private WebView webView_address;
    private TextView txt_address;
    private TextView txt_address1;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findadress);

        txt_address = findViewById(R.id.txt_address);
        txt_address1 = findViewById(R.id.txt_address1);
        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();
    }
    public void init_webView() {
        // WebView 설정
        webView_address = (WebView) findViewById(R.id.webView_address);

        // JavaScript 허용
        webView_address.getSettings().setJavaScriptEnabled(true);
        webView_address.getSettings().setDomStorageEnabled(true);
        // JavaScript의 window.open 허용
        webView_address.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView_address.addJavascriptInterface(new AndroidBridge(), "TestApp");

        // web client 를 chrome 으로 설정
        webView_address.setWebChromeClient(new WebChromeClient());

        // webview url load. php 파일 주소
        webView_address.loadUrl("http://121.78.238.11/app/adress.php");

    }


    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    txt_address.setText(String.format("%s %s",  arg2, arg3));//주소
                    txt_address1.setText(String.format("%s", arg1));//우편번호
                    init_webView();
                     String txt_address0 = txt_address.getText().toString();
                    String txt_address2 = txt_address1.getText().toString();
                    Intent intent = new Intent(findAdress.this,Changeinfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("txt_address0",txt_address0);
                    bundle.putString("txt_address2",txt_address2);
                    intent.putExtras(bundle);
                    setResult(Activity.RESULT_OK, intent);
                    finish();



                    // WebView를 초기화 하지않으면 재사용할 수 없음

                }

            });

        }
    }
}
