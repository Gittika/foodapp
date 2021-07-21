package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.foodapp.Database.Database;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodDetail extends AppCompatActivity {
TextView food_name ,food_price,food_description;
ImageView food_image;
CollapsingToolbarLayout collapsingToolbarLayout;
FloatingActionButton btnCart;
ElegantNumberButton numberButton;
Button addtoCart;
String foodId="";
FirebaseDatabase database;
DatabaseReference reference;
Model currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        getSupportActionBar().hide();
        database =FirebaseDatabase.getInstance();
        reference=database.getReference("Food");
        numberButton =(ElegantNumberButton)findViewById(R.id.number2Btn);
        btnCart=(FloatingActionButton)findViewById(R.id.btnCarts);
       btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartIntent =new Intent(FoodDetail.this,cartActivity.class);
                startActivity(cartIntent);
            }
        });
        addtoCart=(Button)findViewById(R.id.addCart) ;
        addtoCart.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                addtoCart.setBackgroundColor(R.color.colorPrimary);
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice()
                ));
                Toast.makeText(FoodDetail.this, "Added to cart"+currentFood.getName(), Toast.LENGTH_SHORT).show();

            }
        });

        food_name =(TextView)findViewById(R.id.food_name);
        food_price=(TextView)findViewById(R.id.Food_price);
        food_image=(ImageView)findViewById(R.id.IMG_food);
        food_description=(TextView)findViewById(R.id.TxtDescription);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        //get food id
        if(getIntent()!=null)
            foodId=getIntent().getStringExtra("FoodId");
        if(!foodId.isEmpty())
        {
            getDetailFood(foodId);
        }


    }
    private  void getDetailFood(String foodId){
        reference.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 currentFood =snapshot.getValue(Model.class);
                Glide.with(getBaseContext()).load(currentFood.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}