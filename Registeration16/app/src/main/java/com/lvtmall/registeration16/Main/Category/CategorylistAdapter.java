package com.lvtmall.registeration16.Main.Category;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.lvtmall.registeration.R;

import java.util.List;

//위 실행화면에서 각 리스트뷰에 버튼을 추가했는데 그 버튼을 선언 후 리스너를 등록해줍니다. 이때 버튼이 눌리면 PHP서버로 넘겨줄 데이터를 설정해줍니다.
//커스 BaseAdapter 갖다대고 ATL+엔터 쳐서 Implement methods 받은면된다
public class CategorylistAdapter extends BaseAdapter {
    private int image;
    private Context context;
    private List<Category> CategoryList;
    private LayoutInflater inflater = null;
    private LruCache<Integer, Bitmap> imageCache;
    private RequestQueue queue;
    public static final String IMAGE_BASEURL = "";
    //생성자를 만들어 준다. (Activity parentActivity 자식이 불러온 부모액티비티 추가)
    //여기서 Activity parentActivity가 추가됨 회원삭제 및 관리자기능 예제
    public CategorylistAdapter(Context context, List<Category> CategoryList) {
        this.context = context;
        this.CategoryList = CategoryList;
        inflater = LayoutInflater.from(context);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
        queue = Volley.newRequestQueue(context);
    }

    public class ViewHolder {
        TextView item;
        TextView price;
        TextView explan;
        ImageView categoryImages;

    }

    //출력할 총갯수를 설정하는 메소드
    @Override
    public int getCount() {
        return CategoryList.size();
    }

    @Override
    //특정한 사용자를 반환 할수 있도록 한다.
    public Object getItem(int i) {
        return CategoryList.get(i);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Category category = CategoryList.get(i);
        final CategorylistAdapter.ViewHolder holder;
        if (view == null) {

            view = inflater.inflate(R.layout.categorylist_data, null);
            holder = new CategorylistAdapter.ViewHolder();
            holder.item = (TextView) view.findViewById(R.id.item);
            holder.price = (TextView) view.findViewById(R.id.price);
            holder.explan = (TextView) view.findViewById(R.id.explan);
            view.setTag(holder);
        } else {

            holder = (CategorylistAdapter.ViewHolder) view.getTag();
        }

        holder.item.setText(CategoryList.get(i).getitem());
        holder.price.setText(CategoryList.get(i).getprice()+"P");
        holder.explan.setText(CategoryList.get(i).getexplan());
        Bitmap bitmap = imageCache.get(Integer.parseInt(CategoryList.get(i).getm_no()));

        holder.categoryImages = (ImageView) view.findViewById(R.id.categoryImages);
        if (bitmap != null) {

            holder.categoryImages.setImageBitmap(bitmap);
        }else {

            String imageURL = IMAGE_BASEURL +category.getImage();
            ImageRequest request = new ImageRequest(imageURL,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {

                            holder.categoryImages.setImageBitmap(response);
                            imageCache.put(Integer.parseInt(category.getm_no()), response);

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
        return view;
    }
}
