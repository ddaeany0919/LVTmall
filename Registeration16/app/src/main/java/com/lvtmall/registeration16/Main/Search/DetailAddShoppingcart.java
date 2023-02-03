package com.lvtmall.registeration16.Main.Search;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DetailAddShoppingcart extends StringRequest {

    final static private String URL = "http://121.78.238.11/app/save.php";
    private Map<String, String> parameters;

    public DetailAddShoppingcart(String m_id, String m_no, Response.Listener<String> listener){
        super(Method.POST,URL , listener ,null);
        parameters = new HashMap<>();
        parameters.put("m_id", m_id); parameters.put("m_no", m_no);

    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
