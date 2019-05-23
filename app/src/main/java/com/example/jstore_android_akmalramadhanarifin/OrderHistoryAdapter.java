package com.example.jstore_android_akmalramadhanarifin;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>{
    private ArrayList<Invoice> dataList;
    private Context context;

    public OrderHistoryAdapter(ArrayList<Invoice> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public OrderHistoryAdapter.OrderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_history, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.OrderHistoryViewHolder holder, int position) {
        holder.txtId.setText(String.format("%d", dataList.get(position).getId()));
        holder.txtItem.setText(dataList.get(position).getItem());
        holder.txtStatus.setText(dataList.get(position).getInvoiceStatus());
        holder.txtPrice.setText(String.format("%d", dataList.get(position).getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView txtId, txtItem, txtStatus, txtPrice;

        public OrderHistoryViewHolder(final View itemView){
            super(itemView);
            txtId = itemView.findViewById(R.id.txt_id_inv1);
            txtItem = itemView.findViewById(R.id.txt_item1);
            txtStatus = itemView.findViewById(R.id.txt_status1);
            txtPrice = itemView.findViewById(R.id.txt_price);
        }
    }
}
