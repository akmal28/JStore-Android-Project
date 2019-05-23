package com.example.jstore_android_akmalramadhanarifin;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderHistoryAdapter adapter;
    private ArrayList<Invoice> invoiceArrayList = new ArrayList<>();
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        currentUserId = getIntent().getIntExtra("id_customer", 0);
        recyclerView = findViewById(R.id.historyList);
        addData();
    }

    public void addData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data..");
        progressDialog.show();

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    Invoice invoice1 = null;
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject invoice = jsonResponse.getJSONObject(i);
                        JSONObject customer = invoice.getJSONObject("customer");

                        Customer customer1 = new Customer(customer.getInt("id"), customer.getString("name"), customer.getString("email"), customer.getString("username"), customer.getString("password"), customer.getString("birthDate"));

                        if (invoice.getString("invoiceStatus").equals("PAID")) {
                            invoice1 = new Invoice(invoice.getInt("id"), invoice.getInt("totalPrice"), invoice.getString("date"), invoice.getBoolean("active"), customer1, invoice.getJSONArray("item").toString(), invoice.getString("invoiceStatus"), invoice.getString("invoiceType"));
                        } else if (invoice.getString("invoiceStatus").equals("UNPAID")) {
                            invoice1 = new Invoice(invoice.getInt("id"), invoice.getInt("totalPrice"), invoice.getString("date"), invoice.getBoolean("active"), customer1, invoice.getJSONArray("item").toString(), invoice.getString("invoiceStatus"), invoice.getString("invoiceType"), invoice.getString("dueDate"));
                        } else if (invoice.getString("invoiceStatus").equals("INSTALLMENT")) {
                            invoice1 = new Invoice(invoice.getInt("id"), invoice.getInt("totalPrice"), invoice.getString("date"), invoice.getBoolean("active"), customer1, invoice.getJSONArray("item").toString(), invoice.getString("invoiceStatus"), invoice.getString("invoiceType"), invoice.getInt("installmentPeriod"), invoice.getInt("installmentPrice"));
                        }
                        invoiceArrayList.add(invoice1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderHistoryActivity.this);
                    builder.setMessage("You don't have an active invoice!").create().show();
                    finish();
                }
                adapter = new OrderHistoryAdapter(invoiceArrayList, getApplicationContext());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrderHistoryActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        };
        OrderHistoryRequest request = new OrderHistoryRequest(currentUserId,responseListener);
        RequestQueue queue = Volley.newRequestQueue(OrderHistoryActivity.this);
        queue.add(request);
    }
}
