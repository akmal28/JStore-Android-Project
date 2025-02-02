package com.example.jstore_android_akmalramadhanarifin;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/newcustomer";
    private Map<String, String> params;

    public RegisterRequest(String name, String email, String username, String password, Response.Listener<String> listener){
        super(Method.POST, Regis_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);
    }

    public Map<String, String> getParams(){
        return params;
    }
}
