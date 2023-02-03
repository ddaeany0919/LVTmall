package com.lvtmall.registeration16.Main.Userinfo;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lvtmall.registeration.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.lvtmall.registeration16.Main.Main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Userinfo1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Userinfo1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Userinfo1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Userinfo1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Userinfo1.
     */
    // TODO: Rename and change types and number of parameters
    public static Userinfo1 newInstance(String param1, String param2) {
        Userinfo1 fragment = new Userinfo1();
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

    String myJSON;
    private static final String TAG_RESULTS = "response";
    private static final String TAG_COIN = "available";
    private static final String TAG_ID = "m_id";
    public static  String TAG_NAME = "name";
    private static final String TAG_SECRET = "secret";
    private static final String TAG_API = "api";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_HP = "hp";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_POINT = "point";
    private Handler mHandler;
    private ProgressDialog mProgressDialog;

    String point = null;
    String secret = null;
    String api = null;
    String hp = null;
    String address = null;
    String email = null;
    String name = null;
    String m_id = null;
    String coin = null;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList1;
    ListView userinfolist;





    public void showList() {
        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                m_id = c.getString("m_id");//아래 코드와 차이없음
                point = c.getString("point");
                secret = c.getString("secret");
                api = c.getString("api");
                hp = c.getString("hp");
                address = c.getString("address");
                email = c.getString("email");
                name = c.getString("name");
                coin = c.getString("available");

                HashMap<String, String> persons = new HashMap<>();

                persons.put(TAG_ID, m_id );//화면에 나오는 부분
                persons.put(TAG_POINT, point);
                persons.put(TAG_COIN, coin);
                persons.put(TAG_HP, hp);
                persons.put(TAG_ADDRESS, address);
                persons.put(TAG_EMAIL, email);
                persons.put(TAG_SECRET, secret);
                persons.put(TAG_API, api);
                persons.put(TAG_NAME, name);
                Set set = persons.entrySet();
                personList1.add(persons);


            }

            ListAdapter adapter1 = new SimpleAdapter(
                    getActivity(), personList1, R.layout.list_info,
                    new String[]{TAG_ID, TAG_POINT, TAG_COIN},
                    new int[]{R.id.id, R.id.point, R.id.coin}

            );

            userinfolist.setAdapter(adapter1);


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo1, container, false);
        Intent intent = new Intent((MainActivity)getActivity(), Userinfo1.class);
        String m_id=getActivity().getIntent().getStringExtra("m_id");
        final String name=getActivity().getIntent().getStringExtra("name");

        //LoginActivity에서 받아온 m_id값을 MainActivity있는 모든 엑티비티에 m_id값을 줌
        // m_pwd=getIntent().getStringExtra("m_pwd");
        userinfolist = (ListView)view.findViewById(R.id.userinfolistView);
        personList1 = new ArrayList<HashMap<String, String>>();
        getData("http://121.78.238.11/app/list1.php?m_id=" + m_id); //수정 필요
        Button exchangeButton = (Button) view.findViewById(R.id.exchangeButton);
        Button purchaseButton = (Button) view.findViewById(R.id.purchaseButton);
        Button changeinformation = (Button) view.findViewById(R.id.changeinformation);

        purchaseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PurchaseDetails.class);
                intent.putExtra("point", point);
                intent.putExtra("secret", secret);
                intent.putExtra("api", api);
                intent.putExtra("name", name);
                intent.putExtra("hp", hp);
                intent.putExtra("address", address);
                intent.putExtra("email", email);
                intent.putExtra("coin", coin);
                Userinfo1.this.startActivity(intent);

            }
        });
        exchangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Exchange.class);
                intent.putExtra("point", point);
                intent.putExtra("secret", secret);
                intent.putExtra("api", api);
                intent.putExtra("name", name);
                intent.putExtra("hp", hp);
                intent.putExtra("address", address);
                intent.putExtra("email", email);
                intent.putExtra("coin", coin);
                Userinfo1.this.startActivity(intent);
            }
        });
        changeinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Changeinfo.class);
                intent.putExtra("point", point);
                intent.putExtra("secret", secret);
                intent.putExtra("api", api);
                intent.putExtra("name", name);
                intent.putExtra("hp", hp);
                intent.putExtra("address", address);
                intent.putExtra("email", email);
                intent.putExtra("coin", coin);
                Userinfo1.this.startActivity(intent);
            }
        });



//잠시만 기다려주세요 화면 나타내는구간
        mHandler = new Handler();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog = ProgressDialog.show(getActivity(), "",
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
                }, 1530);
            }
        });
        return view;

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
}
