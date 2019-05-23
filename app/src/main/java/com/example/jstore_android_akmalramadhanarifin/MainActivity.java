package com.example.jstore_android_akmalramadhanarifin;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Supplier> listSupplier = new ArrayList<>();
    private ArrayList<Item> listItem = new ArrayList<>();
    private HashMap<Supplier, ArrayList<Item>> childMapping = new HashMap<>();
    private int currentUserId;
    private MainListAdapter listAdapter;
    private ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentUserId = getIntent().getExtras().getInt("currentUserId");
        // get the listview
        expListView = findViewById(R.id.expListView);

        // preparing list data
        refreshList();
        final Button pesanan = findViewById(R.id.pesanan);
        pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent invoice = new Intent(MainActivity.this, PesananActivity.class);
            invoice.putExtra("id_customer", currentUserId);
            startActivity(invoice);
            }
        });

        final Button history = findViewById(R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderHistory = new Intent(MainActivity.this, OrderHistoryActivity.class);
                orderHistory.putExtra("id_customer", currentUserId);
                startActivity(orderHistory);
            }
        });
    }

    protected void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i=0; i<jsonResponse.length(); i++){
                        boolean flag = false;
                        JSONObject item = jsonResponse.getJSONObject(i);
                        JSONObject supplier = item.getJSONObject("supplier");
                        JSONObject location = supplier.getJSONObject("location");

                        Location location1 = new Location(location.getInt("id"), location.getString("province"), location.getString("description"), location.getString("city"));
                        Supplier supplier1 = new Supplier(supplier.getInt("id"), supplier.getString("name"), supplier.getString("email"), supplier.getString("phoneNumber"), location1);
                        Item item1 = new Item(item.getInt("id"),item.getString("name"), item.getInt("price"), item.getString("category"), item.getString("status"), supplier1);

                        if(listSupplier.size()>0){
                            for(Supplier sup : listSupplier){
                                if(sup.getId() == supplier1.getId()){
                                    flag = true;
                                }else {
                                    Log.d("", "onResponse else: "+supplier1.getId());
                                }
                            }
                            if (!flag){
                                Log.d("aa", "onResponse else: "+supplier1.getId());
                                listSupplier.add(supplier1);
                            }
                        }
                        else{
                            listSupplier.add(supplier1);
                        }

                        listItem.add(item1);
                    }
                    for(Supplier sup : listSupplier){
                        ArrayList<Item> temp = new ArrayList<>();
                        for(Item item : listItem){
                            if(item.getSupplier().getName().equals(sup.getName()) || item.getSupplier().getEmail().equals(sup.getEmail()) || item.getSupplier().getPhoneNumber().equals(sup.getPhoneNumber())){
                                temp.add(item);
                            }
                        }
                        childMapping.put(sup,temp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Refresh Failed!").create().show();
                }
                Log.d("bcd", "onResponse: "+listSupplier);
                Log.d("bcd", "onResponse: "+childMapping);
                // setting list adapter
                listAdapter = new MainListAdapter(MainActivity.this, listSupplier, childMapping);

                // setting list adapter
                expListView.setAdapter(listAdapter);
                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Item item = childMapping.get(listSupplier.get(groupPosition)).get(childPosition);
                        Intent itemIntent = new Intent(MainActivity.this, BuatPesananActivity.class);
                        itemIntent.putExtra("id_item", item.getId());
                        itemIntent.putExtra("name", item.getName());
                        itemIntent.putExtra("category", item.getCategory());
                        itemIntent.putExtra("status", item.getStatus());
                        itemIntent.putExtra("price", item.getPrice());
                        itemIntent.putExtra("id_customer", currentUserId);
                        startActivity(itemIntent);
                        return false;
                    }
                });
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }

}
