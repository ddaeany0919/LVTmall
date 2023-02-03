package com.lvtmall.registeration16.Main.Search;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.lvtmall.registeration.R;
import com.lvtmall.registeration16.Main.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Detail1 extends AppCompatActivity  {
    private ListView detaillistView;
    private DetailAdapter adapter;
    private List<Detail> DetailList;
    private androidx.appcompat.app.AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         final String m_no;      final String m_id = MainActivity.m_id;
        m_no= getIntent().getStringExtra("m_no");
        getData("http://121.78.238.11/app/detail.php?m_no="+m_no);
        Button shoppingcartButton = (Button)findViewById(R.id.shoppingcartButton);

        setContentView(R.layout.activity_detail);
        DetailList= new ArrayList<Detail>();
        adapter = new DetailAdapter(this.getApplicationContext(),DetailList,this);
        detaillistView=(ListView)findViewById(R.id.detaillistView) ;
        detaillistView.setAdapter(adapter);
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


                try {
                    DetailList.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count =0;
                    String price;
                    String item;
                    String m_no;
                    String image;
                    String main_image1 ;   String main_image2 ;   String main_image3;

                    while (count<jsonArray.length())
                    {
                        JSONObject object = jsonArray.getJSONObject(count);
                        m_no = object.getString("m_no");
                        price = object.getString("price");
                        item = object.getString("item");
                        image = object.getString("image");
                        main_image1 = object.getString("main_image1"); main_image2= object.getString("main_image2"); main_image3 = object.getString("main_image3");
                        Detail detail = new Detail(m_no,image,price,item,main_image1,main_image2,main_image3);
                        DetailList.add(detail);
                        count++;

                       /* SharedPreferences pref= getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();// editor에 put 하기
                        editor.putString("m_no1",m_no); //First라는 key값으로 id 데이터를 저장한다.
                        editor.putString("price1",price);    editor.putString("item1",item);    editor.putString("image1",image);
                        editor.commit(); //완료한다.*/

                    }
                    if (count==0)
                    {
                        AlertDialog dialog;
                        AlertDialog.Builder builder = new AlertDialog.Builder(Detail1.this);
                        dialog = builder.setMessage("조회된 내용이 없습니다")
                                .setPositiveButton("확인",null)
                                .create();
                        dialog.show();

                    }

                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}