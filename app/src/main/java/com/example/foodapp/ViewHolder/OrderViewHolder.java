package com.example.foodapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.R;

import info.hoang8f.widget.FButton;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtOrderAddress,txtTotalAmount,txtCustumerName;

   public FButton detail,track;


    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrderId =(TextView)itemView.findViewById(R.id.OrderId);
        txtOrderStatus=(TextView)itemView.findViewById(R.id.OrderStatus);
        txtOrderPhone=(TextView)itemView.findViewById(R.id.OrderPhone);
        txtOrderAddress=(TextView)itemView.findViewById(R.id.OrderAddress);
        txtTotalAmount=(TextView)itemView.findViewById(R.id.totalAmount);
        txtCustumerName=(TextView)itemView.findViewById(R.id.CustumerName);
        detail=(FButton)itemView.findViewById(R.id.detailbtn);
        track=(FButton)itemView.findViewById(R.id.trackBtn);

    }




}
