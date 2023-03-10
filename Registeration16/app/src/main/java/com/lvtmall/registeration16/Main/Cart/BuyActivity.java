package com.lvtmall.registeration16.Main.Cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import static com.lvtmall.registeration16.Main.Main.MainActivity.name;
import static com.lvtmall.registeration16.Main.Main.MainActivity.m_id;
import static com.lvtmall.registeration16.Main.Main.MainActivity.email;
import static com.lvtmall.registeration16.Main.Main.MainActivity.hp;
import static com.lvtmall.registeration16.Main.Main.MainActivity.address;
import static com.lvtmall.registeration16.Main.Main.MainActivity.address1;
import static com.lvtmall.registeration16.Main.Main.MainActivity.address2;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.lvtmall.registeration.R;


import org.json.JSONObject;

public class BuyActivity extends AppCompatActivity {
    private ArrayAdapter adapter;
    TextView amountText1 = null;
    ImageButton plusButton = null;
    ImageButton minusButton = null;
    boolean isCal = false;

    private ImageView ivImage;
    private AlertDialog dialog;
    public String m_no;
    public String priceText;
    public String totalText;
    public String delivery_priceText;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selllist_data);


        final TextView itemText = (TextView) findViewById(R.id.itemText);
        final TextView priceText = (TextView) findViewById(R.id.priceText);
        final TextView totalText = (TextView)findViewById(R.id.totalText);
        ivImage = findViewById(R.id.image);
        amountText1 = (TextView) findViewById(R.id.amountText);

       TextView delivery_priceText = (TextView) findViewById(R.id.delivery_priceText);


        final Button addButton = (Button) findViewById(R.id.addButton);
         final String m_no ;//m_no1??? Detail1?????? ????????????
        final String item ;  final String price ; final String image1 ;

       /* pref = getSharedPreferences("pref", MODE_PRIVATE);
        String m_no1=pref.getString("m_no1",null); //????????? ???????????? ???, ???????????? ?????? ?????? null??????
        String image1=pref.getString("image1",null);  String item1=pref.getString("item1",null);
        String price1=pref.getString("price1",null);
        System.out.println(image1);*/ //SharedPreferences ???????????? ???????????? ????????????

        Intent intent = getIntent();
        m_no=intent.getStringExtra("m_no");
        item=intent.getStringExtra("item");
        price=intent.getStringExtra("price");
        image1=intent.getStringExtra("image");

        String cnt = null;
        cnt = intent.getStringExtra("count");
        final int count = cnt==null?0:Integer.parseInt(cnt);


        itemText.setText("?????????: " + item); priceText.setText("?????? ??????: "+price+"p");
        String imageUrl =image1 ;//    implementation 'com.github.bumptech.glide:glide:4.9.0', annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0' ???????????? ???????????? ??????????????? ??????
        Glide.with(this).load(imageUrl).into(ivImage);


        Integer delivery_price1 = 2500;
        final String delivery_price = Integer.toString(delivery_price1);
        delivery_priceText.setText("???????????? "+delivery_price+"P?????????");

         final TextView amountText = (TextView) findViewById(R.id.amountText);


        amountText.setText(String.valueOf("??????: "+count));

        String str2 = price;
        Integer num2 = Integer.parseInt(str2);
        Integer delivery_price2 = 2500;
        Integer exchangePay1 = count*num2+delivery_price2;
        final String strexchangepay = Integer.toString(exchangePay1);
        totalText.setText("??? ??????: "+strexchangepay+"P");

        final String Cnt = cnt;


        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuyActivity.this);
                                dialog = builder.setMessage("?????? ???????????????.")
                                        .setPositiveButton("??????", null)
                                        .create();
                                dialog.show();
                                finish();
                            }

                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuyActivity.this);
                                dialog = builder.setMessage("????????? ??????????????????.")
                                        .setNegativeButton("??????",null)
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
                System.out.println(m_id); System.out.println(name); System.out.println(email); System.out.println(m_no); System.out.println(Cnt);System.out.println(item);
                System.out.println(price);System.out.println(delivery_price);System.out.println(image1);System.out.println(hp);
                System.out.println(address);System.out.println(address1);System.out.println(address2);
                BuyRequest buyRequest= new BuyRequest(m_id, name,email,m_no,item,Cnt,price,delivery_price,image1,hp,address,address1,address2,
                        responseListener);
                RequestQueue queue = Volley.newRequestQueue(BuyActivity.this);
                queue.add(buyRequest);
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
