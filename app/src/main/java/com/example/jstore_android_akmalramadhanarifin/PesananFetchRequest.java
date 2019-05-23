package com.example.jstore_android_akmalramadhanarifin;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananFetchRequest extends StringRequest {
    private static final String BASE_URL = "http://10.0.2.2:8080/invoicecustomer/";
    private Map<String, String> params;

    public PesananFetchRequest(int currentUserId, Response.Listener<String> listener){
        super(Method.GET, BASE_URL+currentUserId, listener, null);
        params = new HashMap<>();
        params.put("id_customer", currentUserId+"");
    }

    public Map<String, String> getParams(){
        return params;
    }
}