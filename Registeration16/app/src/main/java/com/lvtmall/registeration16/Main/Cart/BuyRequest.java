package com.lvtmall.registeration16.Main.Cart;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BuyRequest extends StringRequest {

    final static private String URL = "http://121.78.238.11/app/buy1.php";
    private Map<String, String> parameters;

    public BuyRequest(String m_id, String name,String email,String m_no,String item,String Cnt,String price,
                      String delivery_price,String image1, String hp,String address,String address1,String address2,Response.Listener<String> listener){
        super(Method.POST,URL , listener ,null);
        parameters = new HashMap<>();parameters.put("m_id", m_id);parameters.put("rname", name);parameters.put("oemail1", email); parameters.put("product", m_no);
        parameters.put("item", item);parameters.put("num", Cnt); parameters.put("price", price);parameters.put("delivery", delivery_price);
        parameters.put("image", image1);parameters.put("rphone", hp);parameters.put("addr", address);parameters.put("addr2", address1);parameters.put("addr3", address2);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
