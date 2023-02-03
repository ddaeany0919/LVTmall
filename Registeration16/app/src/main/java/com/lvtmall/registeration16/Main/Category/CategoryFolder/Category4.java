package com.lvtmall.registeration16.Main.Category.CategoryFolder;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.lvtmall.registeration.R;
import com.lvtmall.registeration16.Main.Category.Category;
import com.lvtmall.registeration16.Main.Category.CategorylistAdapter;
import com.lvtmall.registeration16.Main.Search.Detail1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Category4 extends AppCompatActivity {
    private GridView categoryListView;
    private CategorylistAdapter adapter;
    private List<Category> CategoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getData("http://121.78.238.11/app/category.php?category=atom");
        setContentView(R.layout.activity_category);
        CategoryList= new ArrayList<Category>();
        adapter = new CategorylistAdapter(this.getApplicationContext(),CategoryList);
        categoryListView=(GridView)findViewById(R.id.categoryListView) ;
        categoryListView.setAdapter(adapter);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Detail1.class);
                intent.putExtra("m_no",CategoryList.get(i).getm_no());
                intent.putExtra("price",CategoryList.get(i).getprice());
                intent.putExtra("item",CategoryList.get(i).getprice());
                startActivity(intent);

            }

        });


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
                    CategoryList.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count =0;
                    String price;
                    String m_no ;
                    String explan;
                    String category;
                    String image;
                    String item;
                    while (count<jsonArray.length())
                    {
                        JSONObject object = jsonArray.getJSONObject(count);
                        category = object.getString("category");
                        m_no = object.getString("m_no");
                        price = object.getString("price");
                        explan = object.getString("explan");
                        image = object.getString("image");
                        item = object.getString("item");
                        Category category1 = new Category(category,m_no,price,explan,item,image);
                        CategoryList.add(category1);
                        count++;
                    }
                    if (count==0)
                    {
                        AlertDialog dialog;
                        AlertDialog.Builder builder = new AlertDialog.Builder(Category4.this);
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