package com.lvtmall.registeration16.Register;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://121.78.238.11/app/UserRegister10.php";
    private Map<String, String> parameters;

    public RegisterRequest(String m_id,String m_pwd,String name,String m_hp,String m_email,String m_emailpwd,String m_crexpwd,
                           Response.Listener<String> listener){
        super(Method.POST,URL , listener ,null);
        parameters = new HashMap<>();
        parameters.put("m_id", m_id); parameters.put("m_pwd", m_pwd);parameters.put("name",name);parameters.put("m_hp", m_hp);
        parameters.put("m_email", m_email);parameters.put("m_email_pwd",m_emailpwd);parameters.put("m_crexpwd", m_crexpwd);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
