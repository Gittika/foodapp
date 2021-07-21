package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.Common.Common;
import com.example.foodapp.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderActivity extends AppCompatActivity {
   private RecyclerView recyclerView;
private    RecyclerView.LayoutManager layoutManager;
  FirebaseDatabase database;
  DatabaseReference reference;
  FirebaseRecyclerAdapter<Request , OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setTitle("My Orders");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        database=FirebaseDatabase.getInstance();
        reference =database.getReference("Request");
        recyclerView =(RecyclerView)findViewById(R.id.orderList);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhone());

    }

    private void loadOrders(String phone) {
        adapter =new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                reference.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Request request, int i) {
                orderViewHolder.txtOrderId.setText(adapter.getRef(i).getKey());
                orderViewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(request.getStatus()));
                orderViewHolder.txtOrderPhone.setText(request.getPhone());
                orderViewHolder.txtOrderAddress.setText(request.getAddress());
                orderViewHolder.txtTotalAmount.setText(request.getTotal());
                orderViewHolder.txtCustumerName.setText(request.getName());
                orderViewHolder.track.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(OrderActivity.this,"Track is clicked",Toast.LENGTH_SHORT).show();
                    }
                });

                orderViewHolder.detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(OrderActivity.this,OrderDetailActivity.class);
                        Common.currentRequest =request;
                        startActivity(intent);
                    }
                });


            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    }

}