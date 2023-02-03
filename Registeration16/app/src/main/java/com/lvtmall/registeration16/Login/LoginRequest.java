package com.lvtmall.registeration16.Login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
       final static private String URL = "http://121.78.238.11/app/UserLogin10.php";
    private Map<String, String> parameters;

    public LoginRequest(String m_id, String m_pwd, Response.Listener<String> listener){
        super(Method.POST,URL , listener ,null);
        parameters = new HashMap<>();
        parameters.put("m_id", m_id);
        parameters.put("m_pwd", m_pwd);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
