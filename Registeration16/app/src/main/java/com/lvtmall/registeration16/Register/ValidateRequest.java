package com.lvtmall.registeration16.Register;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {

    //현재 안드로이드앱을 에뮬레이터로 돌리므로 에뮬레이터가 설치된 서버에 있는 mysql에 접근하려면
    //다음과 같이 주소로 로 접근해야합니다
    final static private String URL = "http://194.246.42.14/app/UserValidate10.php";
    private Map<String, String> parameters;

    //생성자
    public ValidateRequest(String m_id, Response.Listener<String> listener){

        super(Method.POST, URL, listener, null); //해당 URL에 POST방식으로 파마미터들을 전송함

        parameters = new HashMap<>();
        parameters.put("m_id", m_id);
    }

    //추후 사용을 위한 부분

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        return parameters;

    }

}