package com.lvtmall.registeration16.Main.Userinfo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ExchangeRequest extends StringRequest {


    final static private String URL = "http://121.78.238.11/app/SELL.php";
    private Map<String, String> parameters;

    public ExchangeRequest(String m_id, String point,String amount,String name,String hp,String api,String secret, Response.Listener<String> listener){
        super(Method.POST,URL , listener ,null);
        parameters = new HashMap<>();
        parameters.put("m_id", m_id);
        parameters.put("point", point);
        parameters.put("amount", amount);
        parameters.put("name", name);
        parameters.put("hp", hp);
        parameters.put("api", api);
        parameters.put("secret", secret);



    }
    @Override

    public Map<String, String> getParams(){
        return parameters;
    }

}
