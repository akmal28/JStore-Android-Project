package com.example.jstore_android_akmalramadhanarifin;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BuatPesananRequest extends StringRequest {
    private static final String PAID_URL = "http://10.0.2.2:8080/createinvoicepaid";
    private static final String UNPAID_URL = "http://10.0.2.2:8080/createinvoiceunpaid";
    private static final String INSTALLMENT_URL = "http://10.0.2.2:8080/createinstallment";
    private Map<String, String> params;

    public BuatPesananRequest(int idItem, int idCust, Response.Listener<String> listener){
        super(Method.POST, PAID_URL, listener, null);
        params = new HashMap<>();
        params.put("listitem", idItem+"");
        params.put("idcust", idCust+"");
    }

    public BuatPesananRequest(int idItem, int idCust, Response.Listener<String> listener, String a){
        super(Method.POST, UNPAID_URL, listener, null);
        params = new HashMap<>();
        params.put("listitem", idItem+"");
        params.put("idcust", idCust+"");
    }

    public BuatPesananRequest(int idItem, int idCust, int installmentPeriod, Response.Listener<String> listener){
        super(Method.POST, INSTALLMENT_URL, listener, null);
        params = new HashMap<>();
        params.put("listitem", idItem+"");
        params.put("idcust", idCust+"");
        params.put("installmentperiod", installmentPeriod+"");
    }

    public Map<String, String> getParams(){
        return params;
    }
}
