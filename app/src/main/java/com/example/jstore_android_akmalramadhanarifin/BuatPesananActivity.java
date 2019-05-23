package com.example.jstore_android_akmalramadhanarifin;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BuatPesananActivity extends AppCompatActivity {
    private int installmentPeriod = 0;
    private String selectedPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        final int currentUserId = getIntent().getIntExtra("id_customer", 0);
        final int itemId = getIntent().getIntExtra("id_item", 0);
        final String itemName = getIntent().getStringExtra("name");
        final String itemCategory = getIntent().getStringExtra("category");
        final String itemStatus = getIntent().getStringExtra("status");
        final int itemPrice = getIntent().getIntExtra("price", 0);

        final TextView item_name = findViewById(R.id.item_name1);
        final TextView item_category = findViewById(R.id.item_category);
        final TextView item_status = findViewById(R.id.item_status);
        final TextView item_price = findViewById(R.id.item_price);
        final TextView total_price = findViewById(R.id.total_price1);
        final TextView textPeriod = findViewById(R.id.textPeriod);
        final EditText installment_period = findViewById(R.id.installment_period1);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final Button hitung = findViewById(R.id.hitung);
        final Button pesan = findViewById(R.id.pesan);


        pesan.setVisibility(View.INVISIBLE);
        textPeriod.setVisibility(View.INVISIBLE);
        installment_period.setVisibility(View.INVISIBLE);

        item_name.setText(itemName);
        item_category.setText(itemCategory);
        item_status.setText(itemStatus);
        item_price.setText(String.format("%s", itemPrice));
        total_price.setText("0");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(checkedId);
                selectedPayment = radioButton.getText().toString();
                if (selectedPayment.equals("Installment")){
                    textPeriod.setVisibility(View.VISIBLE);
                    installment_period.setVisibility(View.VISIBLE);
                }else {
                    textPeriod.setVisibility(View.INVISIBLE);
                    installment_period.setVisibility(View.INVISIBLE);
                }
            }
        });

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton btnPaid = findViewById(R.id.paid);
                RadioButton btnUnpaid = findViewById(R.id.unpaid);
                RadioButton btnInstallment = findViewById(R.id.installment);
                if (radioGroup.getCheckedRadioButtonId() == R.id.paid){
                    total_price.setText(item_price.getText());
                    selectedPayment = "Paid";
                    btnUnpaid.setVisibility(View.INVISIBLE);
                    btnInstallment.setVisibility(View.INVISIBLE);
                }else if (radioGroup.getCheckedRadioButtonId() == R.id.unpaid){
                    total_price.setText(item_price.getText());
                    selectedPayment = "Unpaid";
                    btnPaid.setVisibility(View.INVISIBLE);
                    btnInstallment.setVisibility(View.INVISIBLE);
                }else if (radioGroup.getCheckedRadioButtonId() == R.id.installment && installment_period != null){
                    installmentPeriod = Integer.parseInt(installment_period.getText().toString());
                    total_price.setText(String.format("%d", itemPrice/installmentPeriod));
                    selectedPayment = "Installment";
                    btnPaid.setVisibility(View.INVISIBLE);
                    btnUnpaid.setVisibility(View.INVISIBLE);
                }
                hitung.setVisibility(View.INVISIBLE);
                pesan.setVisibility(View.VISIBLE);
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                                builder.setMessage("Success!").create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                            builder.setMessage("Failed").create().show();
                        }
                    }
                };
                if (selectedPayment.equals("Paid")){
                    BuatPesananRequest buatPesananRequest = new BuatPesananRequest(itemId, currentUserId, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                    queue.add(buatPesananRequest);
                }else if (selectedPayment.equals("Unpaid")){
                    BuatPesananRequest buatPesananRequest = new BuatPesananRequest(itemId, currentUserId, responseListener, "a");
                    RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                    queue.add(buatPesananRequest);
                }else if(selectedPayment.equals("Installment")){
                    BuatPesananRequest buatPesananRequest = new BuatPesananRequest(itemId, currentUserId, installmentPeriod, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                    queue.add(buatPesananRequest);
                }

            }
        });
    }
}
