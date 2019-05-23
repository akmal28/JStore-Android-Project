package com.example.jstore_android_akmalramadhanarifin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.PesananViewHolder> {
    private ArrayList<Invoice> dataList;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public PesananAdapter(ArrayList<Invoice> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public PesananAdapter.PesananViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_pesanan, parent, false);
        return new PesananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananAdapter.PesananViewHolder holder, int position) {
        holder.txtId.setText(String.format("%d", dataList.get(position).getId()));
        holder.txtItem.setText(dataList.get(position).getItem());
        holder.txtStatus.setText(dataList.get(position).getInvoiceStatus());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class PesananViewHolder extends RecyclerView.ViewHolder{
        private TextView txtId, txtItem, txtStatus;

        public PesananViewHolder(final View itemView){
            super(itemView);
            txtId = itemView.findViewById(R.id.txt_id_inv);
            txtItem = itemView.findViewById(R.id.txt_item);
            txtStatus = itemView.findViewById(R.id.txt_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 if (mListener != null){
                     int position = getAdapterPosition();
                     if (position != RecyclerView.NO_POSITION){
                         mListener.onItemClick(position);
                     }
                 }
                }
            });
        }
    }
}
