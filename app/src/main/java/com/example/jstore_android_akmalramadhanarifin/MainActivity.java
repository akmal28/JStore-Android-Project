package com.example.jstore_android_akmalramadhanarifin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.android.volley.Response;

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
    MainListAdapter listAdapter;
    List<String> listDataHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expListView);
        refreshList();
        
        listAdapter = new MainListAdapter(MainActivity.this, listDataHeader, childMapping);
        expListView.setAdapter(listAdapter);
    }

    protected void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i=0; i<jsonResponse.length(); i++){
                        JSONObject item = jsonResponse.getJSONObject(i);
                        JSONObject supplier = item.getJSONObject("supplier");
                        JSONObject location = supplier.getJSONObject("location");

                        String province = location.getString("province");
                        String description = location.getString("description");
                        String city = location.getString("city");

                        Location location1 = new Location(province, description, city);

                        int idSup = supplier.getInt("id");
                        String nameSup = supplier.getString("name");
                        String emailSup = supplier.getString("email");
                        String phoneNumberSup = supplier.getString("phoneNumber");

                        Supplier supplier1 = new Supplier(idSup, nameSup, emailSup, phoneNumberSup, location1);
                        listSupplier.add(supplier1);

                        int idItem = item.getInt("id");
                        String nameItem = item.getString("name");
                        String category = item.getString("category");
                        String status = item.getString("status");

                        Item item1 = new Item(idItem, nameItem, category, status, supplier1);
                        listItem.add(item1);

                        childMapping.put(listSupplier.get(i), listItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
