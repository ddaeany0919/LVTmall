package com.lvtmall.registeration16.Main.Cart;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.lvtmall.registeration.R;
import com.lvtmall.registeration16.Main.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Shoppingcart1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Shoppingcart1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Shoppingcart1 extends Fragment implements Serializable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Shoppingcart1.
     */
    // TODO: Rename and change types and number of parameters
    public static Shoppingcart1 newInstance(String param1, String param2) {
        Shoppingcart1 fragment = new Shoppingcart1();
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
  
    private ListView shoppingcartlistView;
    private ShoppingcartlistAdapter adapter;
    private List<Shoppingcart>ShoppingcartList;
    String m_no;

@Override
public  void  onActivityCreated(Bundle b) {
    super.onActivityCreated(b);
    shoppingcartlistView=(ListView) getView().findViewById(R.id.shoppingcartlistView) ;
    ShoppingcartList = new ArrayList<Shoppingcart>();
    adapter = new ShoppingcartlistAdapter(getContext().getApplicationContext(),ShoppingcartList,this);
    shoppingcartlistView.setAdapter(adapter);
   String m_id = MainActivity.m_id;
     String m_no=getActivity().getIntent().getStringExtra("m_no");
    getData("http://121.78.238.11/app/save_list.php?m_id="+m_id);
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppingcart1, container, false);

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
                    ShoppingcartList.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    int count =0;
                    String price;
                    String item;
                    String m_no;
                    String image;


                    while (count<jsonArray.length())
                    {
                        JSONObject object = jsonArray.getJSONObject(count);
                        m_no = object.getString("m_no");
                        price = object.getString("price");
                        item = object.getString("item");
                        image = object.getString("image");

                        Shoppingcart detail = new Shoppingcart(m_no,price,item,image);
                        ShoppingcartList.add(detail);
                        count++;


                    }
                    if (count==0)
                    {
                        /*AlertDialog dialog;
                        AlertDialog.Builder builder = new AlertDialog.Builder(Shoppingcart1.this.getActivity());
                        dialog = builder.setMessage("조회된 내용이 없습니다3434343")
                                .setPositiveButton("확인",null)
                                .create();
                        dialog.show();*/

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
