package com.example.foodapp;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;


import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.foodapp.Database.Database;
import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CategoryListActivity extends AppCompatActivity {
    RecyclerView recview;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference reference;
    String categoryId="";
    Toolbar toolbar;
    TextView Food_name,Food_price,Food_description;
    TextView food_name ,food_price;
    ImageView food_image;
    ImageView Food_img;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton floatingActionButton;
    ElegantNumberButton numberButton;
    Button addtoCartBtn;
    Model currentFood;
    String foodId="";





    FirebaseRecyclerAdapter<Model, FoodViewHolder> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_layout);
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Food");

        Food_name=(TextView)findViewById(R.id.foodTxt);


        Food_img=(ImageView)findViewById(R.id.img1);
        Food_price=(TextView)findViewById(R.id.priceTxt);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fbBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartIntent =new Intent(CategoryListActivity.this,cartActivity.class);
                startActivity(cartIntent);
            }
        });






        recview = (RecyclerView) findViewById(R.id.recview);
        recview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(layoutManager);
        //GetiNTENT
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListFood(categoryId);


        }
    }


    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Model, FoodViewHolder>(Model.class, R.layout.list, FoodViewHolder.class, reference.orderByChild("menuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Model model, int i) { foodViewHolder.food_name.setText(model.getName());
                foodViewHolder.food_price.setText(model.getPrice());
                Glide.with(foodViewHolder.food_image.getContext()).load(model.getImage()).into(foodViewHolder.food_image);
                final Model local = model;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        foodId=adapter.getRef(position).getKey();
                        Intent intent =new Intent(CategoryListActivity.this ,FoodDetail.class);
                   intent.putExtra("FoodId",foodId);
                        startActivity(intent);
                    }
                });
            }
        };
        recview.setAdapter(adapter);
    }

}
