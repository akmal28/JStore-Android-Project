package com.example.jstore_android_akmalramadhanarifin;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SelesaiPesananActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        final int invoiceId =  getIntent().getIntExtra("id_invoice", 0);
        final String custName = getIntent().getStringExtra("customer_name");
        final String listItem = getIntent().getStringExtra("item");
        final String dateOrder = getIntent().getStringExtra("date_order");
        final String invoiceType = getIntent().getStringExtra("invoice_type");
        final String invoiceStatus = getIntent().getStringExtra("invoice_status");
        final int totalPrice = getIntent().getIntExtra("total_price", 0);
        final String dueDate = getIntent().getStringExtra("due_date");
        final int installmentPrice = getIntent().getIntExtra("installment_price", 0);
        final int installmentPeriod = getIntent().getIntExtra("installment_period", 0);

        final TextView id_invoice = findViewById(R.id.id_invoice1);
        final TextView cust_name = findViewById(R.id.cust_name1);
        final TextView item_name = findViewById(R.id.item_name1);
        final TextView date_order = findViewById(R.id.date_order1);
        final TextView total_price = findViewById(R.id.total_price1);
        final TextView inv_status = findViewById(R.id.inv_status1);
        final TextView inv_type = findViewById(R.id.inv_type1);
        final TextView due_date = findViewById(R.id.due_date1);
        final TextView staticDue = findViewById(R.id.staticDueDate);
        final TextView staticPrice = findViewById(R.id.staticInstallmentPrice);
        final TextView staticPeriod = findViewById(R.id.staticPeriod);
        final TextView installment_period = findViewById(R.id.installment_period1);
        final TextView installment_price = findViewById(R.id.installment_price1);
        final Button buttonCancel = findViewById(R.id.buttonCancel);
        final Button buttonFinish = findViewById(R.id.buttonFinish);

        staticDue.setVisibility(View.INVISIBLE);
        staticPrice.setVisibility(View.INVISIBLE);
        staticPeriod.setVisibility(View.INVISIBLE);
        due_date.setVisibility(View.INVISIBLE);
        installment_period.setVisibility(View.INVISIBLE);
        installment_price.setVisibility(View.INVISIBLE);

        id_invoice.setText(String.format("%d", invoiceId));
        cust_name.setText(custName);
        item_name.setText(listItem);
        date_order.setText(dateOrder.substring(0,10));
        total_price.setText(String.format("%d", totalPrice));
        inv_status.setText(invoiceStatus);
        inv_type.setText(invoiceType);

        if (invoiceStatus.equals("UNPAID")){
            due_date.setText(dueDate.substring(0,10));
            staticDue.setVisibility(View.VISIBLE);
            due_date.setVisibility(View.VISIBLE);
        }else if(invoiceStatus.equals("INSTALLMENT")){
            installment_period.setText(String.format("%d", installmentPeriod));
            installment_price.setText(String.format("%d", installmentPrice));
            staticPeriod.setVisibility(View.VISIBLE);
            staticPrice.setVisibility(View.VISIBLE);
            installment_period.setVisibility(View.VISIBLE);
            installment_price.setVisibility(View.VISIBLE);
        }

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                        builder.setMessage("Success!").create().show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                    builder.setMessage("Failed").create().show();
                    finish();
                }
            }
        };

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PesananBatalRequest batal = new PesananBatalRequest(invoiceId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(batal);
            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PesananSelesaiRequest selesai = new PesananSelesaiRequest(invoiceId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(selesai);
            }
        });
    }

}
