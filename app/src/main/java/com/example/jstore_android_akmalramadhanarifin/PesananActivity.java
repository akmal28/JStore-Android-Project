package com.example.jstore_android_akmalramadhanarifin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PesananActivity extends AppCompatActivity implements PesananAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private PesananAdapter adapter;
    private ArrayList<Invoice> invoiceArrayList = new ArrayList<>();
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);
        currentUserId = getIntent().getIntExtra("id_customer", 0);
        recyclerView = findViewById(R.id.invoiceList);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(PesananActivity.this);
                    builder.setMessage("You don't have an active invoice!").create().show();
                    finish();
                }
                adapter = new PesananAdapter(invoiceArrayList, getApplicationContext());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PesananActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(PesananActivity.this);
            }
        };
        PesananFetchRequest request = new PesananFetchRequest(currentUserId,responseListener);
        RequestQueue queue = Volley.newRequestQueue(PesananActivity.this);
        queue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent selesaiIntent = new Intent(this, SelesaiPesananActivity.class);
        Invoice invoice = invoiceArrayList.get(position);
        selesaiIntent.putExtra("id_invoice", invoice.getId());
        selesaiIntent.putExtra("customer_name", invoice.getCustomer().getName());
        selesaiIntent.putExtra("item", invoice.getItem());
        selesaiIntent.putExtra("date_order", invoice.getDate());
        selesaiIntent.putExtra("invoice_type", invoice.getInvoiceType());
        selesaiIntent.putExtra("invoice_status", invoice.getInvoiceStatus());
        selesaiIntent.putExtra("total_price", invoice.getTotalPrice());
        selesaiIntent.putExtra("due_date", invoice.getDueDate());
        selesaiIntent.putExtra("installment_price", invoice.getInstallmentPrice());
        selesaiIntent.putExtra("installment_period", invoice.getInstallmentPeriod());
        startActivity(selesaiIntent);
    }
}
