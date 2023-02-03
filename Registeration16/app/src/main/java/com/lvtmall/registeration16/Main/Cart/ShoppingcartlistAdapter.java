package com.lvtmall.registeration16.Main.Cart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.lvtmall.registeration.R;

import java.util.List;

import static com.lvtmall.registeration16.Main.Main.MainActivity.m_id;

//위 실행화면에서 각 리스트뷰에 버튼을 추가했는데 그 버튼을 선언 후 리스너를 등록해줍니다. 이때 버튼이 눌리면 PHP서버로 넘겨줄 데이터를 설정해줍니다.
//커스 BaseAdapter 갖다대고 ATL+엔터 쳐서 Implement methods 받은면된다
public class ShoppingcartlistAdapter extends BaseAdapter {
    private int image;
    private Context context;
    TextView amountText = null;
    ImageButton plusButton = null;
    ImageButton minusButton = null;

    boolean isCal = false;
    private List<Shoppingcart> ShoppingcartList;
    private LayoutInflater inflater = null;
    private LruCache<Integer, Bitmap> imageCache;
    private RequestQueue queue;
    private Fragment parent;
    public static final String IMAGE_BASEURL = "";
    //생성자를 만들어 준다. (Activity parentActivity 자식이 불러온 부모액티비티 추가)
    //여기서 Activity parentActivity가 추가됨 회원삭제 및 관리자기능 예제
    public ShoppingcartlistAdapter(Context context, List<Shoppingcart> ShoppingcartList, Fragment parent) {
        this.context = context;
        this.ShoppingcartList = ShoppingcartList;
        this.parent = parent;
        inflater = LayoutInflater.from(context);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
        queue = Volley.newRequestQueue(context);

    }

    public class ViewHolder {
        TextView item;
        TextView total;
        ImageView shoppingImage;
CheckBox checkBox1;
    }

    //출력할 총갯수를 설정하는 메소드
    @Override
    public int getCount() {
        return ShoppingcartList.size();
    }

    @Override
    //특정한 사용자를 반환 할수 있도록 한다.
    public Object getItem(int i) {
        return ShoppingcartList.get(i);
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
        final Shoppingcart shoppingcart = ShoppingcartList.get(i);
        final ViewHolder holder;

        if (view == null) {

            view = inflater.inflate(R.layout.shopping_info, null);
            holder = new ViewHolder();
            holder.item = (TextView) view.findViewById(R.id.item);
            holder.total = (TextView) view.findViewById(R.id.total);
            holder.checkBox1 = (CheckBox)view.findViewById(R.id.checkBox1);

           view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.item.setText(ShoppingcartList.get(i).getitem());
        holder.total.setText(ShoppingcartList.get(i).getprice()+"P");
        Bitmap bitmap = imageCache.get(Integer.parseInt(ShoppingcartList.get(i).getm_no()));
        holder.checkBox1.setTag(i);

        holder.checkBox1.setOnClickListener(buttonClickListener);
        holder.shoppingImage = (ImageView) view.findViewById(R.id.shoppingImage);
        if (bitmap != null) {

            holder.shoppingImage.setImageBitmap(bitmap);
        }else {

            String imageURL = IMAGE_BASEURL +shoppingcart.getImage();
            ImageRequest request = new ImageRequest(imageURL,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {

                            holder.shoppingImage.setImageBitmap(response);
                            imageCache.put(Integer.parseInt(shoppingcart.getm_no()), response);

                        }
                    },
                    400, 400,//화질조절
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
            public void onClick(View v) {
                TextView amountText = ((RelativeLayout)v.getParent()).findViewById(R.id.amountText);
                String count = amountText.getText().toString();

                Intent intent = new Intent(context, BuyActivity.class);
                intent.putExtra("m_no",ShoppingcartList.get(i).getm_no());
                intent.putExtra("image",ShoppingcartList.get(i).getImage());
                intent.putExtra("item",ShoppingcartList.get(i).getitem());
                intent.putExtra("price",ShoppingcartList.get(i).getprice());
                intent.putExtra("count",count);
                intent.putExtra("m_id",m_id);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
//서비스는 태스크가 없기 때문에 액티비티를 시작하려면 new task 플래그를 줘야 한다고 한다.
            }
        });
        plusButton = (ImageButton) view.findViewById(R.id.plusButton);
        minusButton = (ImageButton) view.findViewById(R.id.minusButton);

        plusButton.setOnClickListener(listener);
        minusButton.setOnClickListener(listener);

        return view;

    }
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // CheckBox
                case R.id.checkBox1:
                    Toast.makeText(
                            context,
                            "체크박스 Tag = " + v.getTag(),
                            Toast.LENGTH_SHORT
                    ).show();
                    break;

                default:
                    break;
            }
        }
    };
      View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            TextView amountText = ((RelativeLayout)v.getParent()).findViewById(R.id.amountText);
            int count = 0;
            String str1 = amountText.getText().toString();
            Integer num1 = Integer.parseInt(str1);
            count = num1;
            if(amountText == null){
                Log.e("TAG", "amountText is null");
            }else {
                switch (v.getId()) {
                    case R.id.plusButton:
                        if (count <= 9) {
                            count++;
                            amountText.setText("" + count);
                            isCal = true;

                        }
                        break;
                    case R.id.minusButton:
                        if (count >= 1) {
                            count--;
                            amountText.setText("" + count);
                            isCal = true;
                        }
                        break;
                }

                if (!isCal) {

                }
            }
        }


    };
}

