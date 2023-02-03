package com.lvtmall.registeration16.Main.Search;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.lvtmall.registeration.R;
import com.lvtmall.registeration16.Main.Main.MainActivity;
import com.lvtmall.registeration16.Main.Cart.Shoppingcart1;

import org.json.JSONObject;

import java.util.List;


//위 실행화면에서 각 리스트뷰에 버튼을 추가했는데 그 버튼을 선언 후 리스너를 등록해줍니다. 이때 버튼이 눌리면 PHP서버로 넘겨줄 데이터를 설정해줍니다.
//커스 BaseAdapter 갖다대고 ATL+엔터 쳐서 Implement methods 받은면된다
public class DetailAdapter extends BaseAdapter {
    private int image;
    private AlertDialog dialog;
    PhotoView main_image1;
    PhotoViewAttacher mAttacher;
    private Detail1 parent;
    private Context context;
    private List<Detail> DetailList;
    private Handler mHandler;
    private ProgressDialog mProgressDialog;
    private LayoutInflater inflater = null;
    private LruCache<Integer, Bitmap> imageCache;
    private RequestQueue queue;
    public static final String IMAGE_BASEURL = "";
    public static final String IMAGE_BASEURL1 = "";
    public static final String IMAGE_BASEURL2 = "";
    public static final String IMAGE_BASEURL3 = "";

    //생성자를 만들어 준다. (Activity parentActivity 자식이 불러온 부모액티비티 추가)
    //여기서 Activity parentActivity가 추가됨 회원삭제 및 관리자기능 예제
    public DetailAdapter(Context applicationContext, List<Detail> DetailList, Detail1 parent) {
        this.context = applicationContext;
        this.DetailList = DetailList;
        this.parent = parent;

        inflater = LayoutInflater.from(context);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
        queue = Volley.newRequestQueue(context);

    }


    public class ViewHolder {
        TextView price;
        TextView item;
        PhotoView image;
        PhotoView detailimages1;
        PhotoView detailimages2;
        PhotoView detailimages3;

    }

    //출력할 총갯수를 설정하는 메소드
    @Override
    public int getCount() {
        return DetailList.size();
    }

    @Override
    //특정한 사용자를 반환 할수 있도록 한다.
    public Object getItem(int i) {
        return DetailList.get(i);
    }

    //아이템별 아이디를 반환하는 메소드
    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    //가장 중요한 부분
    //int i 에서 final int i 로 바뀜 이유는 deleteButton.setOnClickListener에서 이 값을 참조하기 때문
    //하나의 사용자에 대한 뷰를 보여준다.
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final Detail deatil = DetailList.get(i);
        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.detail_data, null);
            holder = new ViewHolder();
            holder.price = (TextView) view.findViewById(R.id.price);
            holder.item = (TextView) view.findViewById(R.id.item);
            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }
        holder.price.setText(DetailList.get(i).getprice()+"P");
        holder.item.setText(DetailList.get(i).getitem());
        Bitmap bitmap = imageCache.get(Integer.parseInt(DetailList.get(i).getm_no()));
        holder.image = (PhotoView) view.findViewById(R.id.image);
        holder.detailimages1 = (PhotoView) view.findViewById(R.id.detailimages1);
        holder.detailimages2 = (PhotoView) view.findViewById(R.id.detailimages2);
        holder.detailimages3 = (PhotoView) view.findViewById(R.id.detailimages3);
        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap);
        }else {
            String imageURL = IMAGE_BASEURL +deatil.getImage();
            ImageRequest request = new ImageRequest(imageURL,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            holder.image.setImageBitmap(response);
                            imageCache.put(Integer.parseInt(deatil.getm_no()), response);

                        }
                    }
                    , 1600, 1600,//화질조절
                    Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error",error.getMessage().toString());
                        }
                    });
            queue.add(request);
        }
        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap);
        }else {
            String imageURL = IMAGE_BASEURL1 +deatil.getmain_image1();
            ImageRequest request = new ImageRequest(imageURL,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            holder.detailimages1.setImageBitmap(response);
                            imageCache.put(Integer.parseInt(deatil.getm_no()), response);

                        }
                    }
                    , 1600, 1600,//화질조절
                    Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error",error.getMessage().toString());
                        }
                    });
            queue.add(request);
        }
        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap);
        }else {
            String imageURL = IMAGE_BASEURL2 +deatil.getmain_image1();
            ImageRequest request = new ImageRequest(imageURL,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            holder.detailimages2.setImageBitmap(response);
                            imageCache.put(Integer.parseInt(deatil.getm_no()), response);

                        }
                    }
                    , 1600, 1600,//화질조절
                    Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error",error.getMessage().toString());
                        }
                    });
            queue.add(request);
        }
        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap);
        }else {
            String imageURL = IMAGE_BASEURL3 +deatil.getmain_image1();
            ImageRequest request = new ImageRequest(imageURL,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            holder.detailimages3.setImageBitmap(response);
                            imageCache.put(Integer.parseInt(deatil.getm_no()), response);

                        }
                    }
                    , 1600, 1600,//화질조절
                    Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error",error.getMessage().toString());
                        }
                    });
            queue.add(request);
        }

        Button addButton = (Button)view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String m_id = MainActivity.m_id;
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        mHandler = new Handler();
                        parent.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog = ProgressDialog.show(parent, "",
                                        "잠시만 기다려 주세요.", true);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                                mProgressDialog.dismiss();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 1030);
                            }
                        });
                    }

                };

                //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분(실지적으로 접속 할수 있도록 생성자를 하나 만들어 준다.)

                DetailAddShoppingcart DetailAddShoppingcart = new DetailAddShoppingcart(m_id,DetailList.get(i).getm_no()+"",responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getApplicationContext());
                queue.add(DetailAddShoppingcart);


                FragmentManager fragmentManager = (parent.getSupportFragmentManager());
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new Shoppingcart1());
                fragmentTransaction.commit();
            }
        }

        );

        ImageButton shoppingcartButton = (ImageButton)view.findViewById(R.id.shoppingcartButton);
        shoppingcartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String m_id = MainActivity.m_id;
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                        //중복체크에 성공하였다면
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){//사용할 수 있는 아이디라면
                                switch (v.getId()) {
                                    case R.id.shoppingcartButton:
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                parent);

                                        // 제목셋팅
                                        alertDialogBuilder.setTitle("장바구니에 추가되었습니다");

                                        // AlertDialog 셋팅
                                        alertDialogBuilder
                                                .setMessage("쇼핑을 계속 하시겠습니까?")
                                                .setCancelable(false)
                                                .setPositiveButton("장바구니로 이동",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(
                                                                    DialogInterface dialog, int id) {
                                                                // 프로그램을 종료한다
                                                                FragmentManager fragmentManager = (parent.getSupportFragmentManager());
                                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                fragmentTransaction.replace(R.id.fragment,new Shoppingcart1());
                                                                fragmentTransaction.commit();//Response.Listener 완료
                                                            }
                                                        })
                                                .setNegativeButton("확인",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(
                                                                    DialogInterface dialog, int id) {
                                                                // 다이얼로그를 취소한다
                                                                dialog.cancel();
                                                            }
                                                        });

                                        // 다이얼로그 생성
                                        AlertDialog alertDialog = alertDialogBuilder.create();

                                        // 다이얼로그 보여주기
                                        alertDialog.show();

                                        break;
                                    default:
                                        break;
                                }



                            }else{//사용할 수 없는 아이디라면(중복체크에 실패 하였다면)
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                                AlertDialog dialog = builder.setMessage("장바구니에 추가하지 못 하였습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분(실지적으로 접속 할수 있도록 생성자를 하나 만들어 준다.)
                DetailAddShoppingcart DetailAddShoppingcart = new DetailAddShoppingcart(m_id,DetailList.get(i).getm_no()+"", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getApplicationContext());
                queue.add(DetailAddShoppingcart);

            }
        });

       /* Button addButton = (Button) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_id = MainActivity.m_id;
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                        //중복체크에 성공하였다면
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){//사용할 수 있는 아이디라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getApplicationContext());
                                AlertDialog dialog = builder.setMessage("추가 되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();


                            }else{//사용할 수 없는 아이디라면(중복체크에 실패 하였다면)
                              AlertDialog.Builder builder = new AlertDialog.Builder(parent.getApplicationContext());
                                AlertDialog dialog = builder.setMessage("추가하지 못 하였습니다.")
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
                BuyRequest addRequest = new BuyRequest(m_id,DetailList.get(i).getm_no()+"", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getApplicationContext());
                queue.add(addRequest);
            }
        });*/


        return view;
    }
}
