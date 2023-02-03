package com.lvtmall.registeration16.Main.Main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lvtmall.registeration.R;
import com.lvtmall.registeration16.Main.Category.CategoryButton;
import com.lvtmall.registeration16.Main.Search.Search1;
import com.lvtmall.registeration16.Main.Cart.Shoppingcart1;
import com.lvtmall.registeration16.Main.Userinfo.Userinfo1;


public class MainActivity extends AppCompatActivity {
    String myJSON;
    private static final String TAG_RESULTS = "response";
    public static final String TAG_ID = "m_id";
    private static final String TAG_NAME = "name";
    private static final String TAG_SECRET = "secret";
    private static final String TAG_API = "api";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_HP = "hp";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_ADDRESS1 = "address1";
    private static final String TAG_ADDRESS2 = "address2";
    private static final String TAG_POINT = "point";
    private static final String TAG_COIN = "available";

    private FragmentManager fragmentManager = getSupportFragmentManager();

    public static String  point;
    public static String secret;
    public static String api;
    public static String hp;
    public static String address;
    public static String address1;
    public static String address2;
    public static String email;
    public static String m_id;
    public static String name;
    public static String coin;

   String available = null ;
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

   // public static String m_id; intent써서 값 주고받을때
   // public static String m_pwd;
    @Override //메인 버튼
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        m_id=getIntent().getStringExtra("m_id");//LoginActivity에서 받아온 m_id값을 MainActivity있는 모든 엑티비티에 m_id값을 줌
       // m_pwd=getIntent().getStringExtra("m_pwd");

        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();

        FragmentTransaction transaction = fragmentManager.beginTransaction();



        getData("http://121.78.238.11/app/list1.php?m_id="+m_id); //수정 필요

        final ImageButton userinfoButton = (ImageButton) findViewById(R.id.userinfoButton);
        final ImageButton categoryButton = (ImageButton) findViewById(R.id.categoryButton);
        final  ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        final  ImageButton shoppingcartButton = (ImageButton) findViewById(R.id.shoppingcartButton);
        final  ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryButton.class);
                intent.putExtra("point",point);
                intent.putExtra("secret",secret);
                intent.putExtra("api",api);
                intent.putExtra("m_id",m_id);
                intent.putExtra("name",name);
                intent.putExtra("hp",hp);
                intent.putExtra("address",address);
                intent.putExtra("address1",address1);
                intent.putExtra("address2",address2);
                intent.putExtra("email",email);
                intent.putExtra("coin",coin);

                categoryButton.setBackgroundColor(getResources().getColor(R.color.white));
                userinfoButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                searchButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                shoppingcartButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                homeButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new CategoryButton());
                fragmentTransaction.commit();
            }
        });
        userinfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Userinfo1.class);
                intent.putExtra("point",point);
                intent.putExtra("secret",secret);
                intent.putExtra("api",api);
                intent.putExtra("m_id",m_id);
                intent.putExtra("name",name);

                intent.putExtra("hp",hp);
                intent.putExtra("address",address);
                intent.putExtra("address1",address1);
                intent.putExtra("address2",address2);
                intent.putExtra("email",email);
                intent.putExtra("coin",coin);

                categoryButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                userinfoButton.setBackgroundColor(getResources().getColor(R.color.white));
                searchButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                shoppingcartButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                homeButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new Userinfo1());
                fragmentTransaction.commit();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("point",point);
                        intent.putExtra("secret",secret);
                        intent.putExtra("api",api);
                        intent.putExtra("m_id",m_id);
                        intent.putExtra("name",name);
                        intent.putExtra("hp",hp);
                        intent.putExtra("address",address);
                        intent.putExtra("address1",address1);
                        intent.putExtra("address2",address2);
                intent.putExtra("email",email);
                intent.putExtra("coin",coin);
                MainActivity.this.startActivity(intent);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search1.class);
                intent.putExtra("point",point);
                intent.putExtra("secret",secret);
                intent.putExtra("api",api);
                intent.putExtra("m_id",m_id);
                intent.putExtra("name",name);
                intent.putExtra("hp",hp);
                intent.putExtra("address",address);
                intent.putExtra("address1",address1);
                intent.putExtra("address2",address2);
                intent.putExtra("email",email);
                intent.putExtra("coin",coin);
                categoryButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                userinfoButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                searchButton.setBackgroundColor(getResources().getColor(R.color.white));
                shoppingcartButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                homeButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new Search1());
                fragmentTransaction.commit();

            }
        });
        shoppingcartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Shoppingcart1.class);
                intent.putExtra("point",point);
                intent.putExtra("secret",secret);
                intent.putExtra("api",api);
                intent.putExtra("m_id",m_id);
                intent.putExtra("name",name);
                intent.putExtra("hp",hp);
                intent.putExtra("address",address);
                intent.putExtra("address1",address1);
                intent.putExtra("address2",address2);
                intent.putExtra("email",email);
                intent.putExtra("coin",coin);
                categoryButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                userinfoButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                searchButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                shoppingcartButton.setBackgroundColor(getResources().getColor(R.color.white));
                homeButton.setBackgroundColor(getResources().getColor(R.color.gonggaPink));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new Shoppingcart1());
                fragmentTransaction.commit();
            }
        });


    }



    public void showList() {


        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++){
                JSONObject c = peoples.getJSONObject(i);
                m_id = c.getString("m_id");//아래 코드와 차이없음
                point = c.getString("point");
                secret = c.getString("secret");
                api = c.getString("api");
                hp = c.getString("hp");
                address = c.getString("address");
                address1 = c.getString("address1");
                address2 = c.getString("address2");
                email = c.getString("email");
                name = c.getString("name");
                coin = c.getString("available");
                HashMap<String, String> persons = new HashMap<>();

                persons.put(TAG_ID,"ID : "+m_id);//화면에 나오는 부분
                persons.put(TAG_POINT,point+"P");
                persons.put(TAG_COIN, coin+"LVT");
                persons.put(TAG_HP, hp);
                persons.put(TAG_ADDRESS, address);
                persons.put(TAG_ADDRESS1, address1);
                persons.put(TAG_ADDRESS2, address2);
                persons.put(TAG_EMAIL, email);
                persons.put(TAG_SECRET, secret);
                persons.put(TAG_API, api);
                persons.put(TAG_NAME, name);

                Set set = persons.entrySet();
                personList.add(persons);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}





