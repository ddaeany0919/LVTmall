package com.lvtmall.registeration16.Main.Search;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.lvtmall.registeration.R;
import com.lvtmall.registeration16.Main.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Search1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Search1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search1 extends Fragment implements Serializable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Search1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search1.
     */
    // TODO: Rename and change types and number of parameters
    public static Search1 newInstance(String param1, String param2) {
        Search1 fragment = new Search1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private TextView itemsearchText;
    private GridView itemListView;
    private SearchlistAdapter adapter;
    private List<Search>searchList;
    String m_id = null;
@Override
public  void  onActivityCreated(Bundle b) {
    super.onActivityCreated(b);
    itemListView=(GridView)getView().findViewById(R.id.itemListView) ;
    searchList = new ArrayList<Search>();
    adapter = new SearchlistAdapter(getContext().getApplicationContext(),searchList);
    itemListView.setAdapter(adapter);
    itemsearchText = (EditText)getView().findViewById(R.id.itemsearchText);
   String m_id = MainActivity.m_id;
    Button itemsearchButton = (Button) getView().findViewById(R.id.itemsearchButton);
    itemsearchButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new BackgroundTask().execute();
        }
    });
    itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
            Intent intent = new Intent(getActivity(), Detail1.class);
            intent.putExtra("m_no",searchList.get(i).getm_no());
            startActivity(intent);
        }
    });

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search1, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    class BackgroundTask extends AsyncTask<Void, Void, String> {
  //      Bitmap bitmap;
        String target;
        @Override
        protected void onPreExecute() {
try {
    //List.php은 파싱으로 가져올 웹페이지
    target = "http://121.78.238.11/app/search.php?item="+(itemsearchText.getText().toString());
} catch (Exception e) {
    e.printStackTrace();
} }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                URL url = new URL(target);//URL 객체 생성
                URLConnection conn = url.openConnection();
                //URL을 이용해서 웹페이지에 연결하는 부분
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                //바이트단위 입력스트림 생성 소스는 httpURLConnection
                InputStream inputStream = httpURLConnection.getInputStream();

                //웹페이지 출력물을 버퍼로 받음 버퍼로 하면 속도가 더 빨라짐
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                String temp;

                //문자열 처리를 더 빠르게 하기 위해 StringBuilder클래스를 사용함

                StringBuilder stringBuilder = new StringBuilder();

                //한줄씩 읽어서 stringBuilder에 저장함

                while ((temp = bufferedReader.readLine()) != null) {

                    stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌

                }
                //사용했던 것도 다 닫아줌

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함

            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void... values) { super.onProgressUpdate(); }

        @Override
        public void onPostExecute(String result) {


            try {
                searchList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count =0;
                String price;
                String explan;
                String item;
                String m_no;
                String image;

                while (count<jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    m_no = object.getString("m_no");
                    price = object.getString("price");
                    explan = object.getString("explan");
                    item = object.getString("item");
                    image = object.getString("image");
                    Search search = new Search(m_no,price,explan,item,image);
                    searchList.add(search);
                    count++;

                }
                if (count==0)
                {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(Search1.this.getActivity());
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
}
