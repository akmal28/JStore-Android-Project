package com.example.jstore_android_akmalramadhanarifin;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananSelesaiRequest extends StringRequest {
    private static final String BASE_URL = "http://10.0.2.2:8080/finishtransaction";
    private Map<String, String> params;

    public PesananSelesaiRequest(int idInvoice, Response.Listener<String> listener){
        super(Method.POST, BASE_URL, listener, null);
        params = new HashMap<>();
        params.put("idinvoice", String.format("%d", idInvoice));
    }

    public Map<String, String> getParams(){
        return params;
    }
}
