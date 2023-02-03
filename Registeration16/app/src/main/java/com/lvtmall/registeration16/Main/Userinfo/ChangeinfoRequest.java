package com.lvtmall.registeration16.Main.Userinfo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChangeinfoRequest extends StringRequest {

    final static private String URL = "http://121.78.238.11/app/edit1.php";
    private Map<String, String> parameters;

    public ChangeinfoRequest(String m_id, String m_pwd, String addr1, String addr2, String addr3, String phone1, String email1,
                             Response.Listener<String> listener){
        super(Method.POST,URL , listener ,null);
        parameters = new HashMap<>();
        parameters.put("m_id", m_id); parameters.put("m_pwd", m_pwd);parameters.put("addr1",addr1);parameters.put("addr2", addr2);
        parameters.put("addr3", addr3);parameters.put("phone1",phone1);parameters.put("email1", email1);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
