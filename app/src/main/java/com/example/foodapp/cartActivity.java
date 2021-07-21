package com.example.foodapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Common.Common;
import com.example.foodapp.Database.Database;
import com.example.foodapp.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

public class cartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference reference;
EditText editAddress,editPhone,editName;
    TextView txtTotalPrice;
    FButton placeOrderBtn;
    CartAdapter adapter;
    List<Order> cart =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        database = FirebaseDatabase.getInstance();
        getSupportActionBar().setTitle("Cart");

        reference = database.getReference("Request");
        txtTotalPrice = (TextView) findViewById(R.id.cartTotal);
        placeOrderBtn = (FButton) findViewById(R.id.orderBtn);
        recyclerView =(RecyclerView)findViewById(R.id.cartRecycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadListFood();
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }
               private void showAlertDialog(){
               AlertDialog.Builder alertDialog=new AlertDialog.Builder(cartActivity.this);
            alertDialog.setTitle("ONE MORE STEP");
             alertDialog.setMessage("Please fill the information");

                   LayoutInflater inflater =this.getLayoutInflater();
                   View add_delivery_layout=inflater.inflate(R.layout.add_delivery_detail,null);

             editName =add_delivery_layout.findViewById(R.id.newNameTxt);
             editAddress=add_delivery_layout.findViewById(R.id.newAddressTxt);
             editPhone=add_delivery_layout.findViewById(R.id.newPhoneTxt);


             alertDialog.setView(add_delivery_layout);
             alertDialog.setIcon(R.drawable.order);



               alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Request request =new Request(
                             Common.currentUser.getPhone(),
                               Common.currentUser.getUsername(),
                               editAddress.getText().toString(),
                               txtTotalPrice.getText().toString(),
                               cart

                       );

                       reference.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                       new Database(getBaseContext()).cleanCart();
                       Toast.makeText(cartActivity.this,"Thank you, order Placed",Toast.LENGTH_LONG).show();
                       finish();
                   }

               });
               alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               });
               alertDialog.show();


           }



    private void loadListFood(){
        cart =new Database(this).getCarts();
        adapter =new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //Calculate total price :
        int total=0;
        for(Order order:cart)
            total +=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        txtTotalPrice.setText(Integer.toString(total));



    }
}