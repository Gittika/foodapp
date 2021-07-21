package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Common.Common;
import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home2Activity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    NavigationView nav;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    FirebaseDatabase database;
    DatabaseReference reference;
    LinearLayout container;

  TextView currentUserName;
    TextView currentUserEmail;
    Toolbar toolbar;
    RecyclerView recycler_menu;
    FloatingActionButton floatingActionButton;
  RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<CategoryModel,MenuViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home2);
        nav=(NavigationView)findViewById(R.id.navmenu);
        nav.setNavigationItemSelectedListener(this);
        View headerView=nav.getHeaderView(0);
        currentUserName =(TextView)headerView.findViewById(R.id.UserFullName);
        currentUserEmail=(TextView)headerView.findViewById(R.id.UserEmailId);







        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        toolbar =(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("CATEGORY");
        getSupportActionBar().hide();






        floatingActionButton=(FloatingActionButton)findViewById(R.id.cartFbBtn);
floatingActionButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent cartIntent =new Intent(Home2Activity.this,cartActivity.class);
          startActivity(cartIntent);
    }
});

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Category");
        toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //Load menu
        recycler_menu =(RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        loadMenu();
        //Register Services
        Intent intent =new Intent(Home2Activity.this,ListenOrder.class);
        startService(intent);




    }
    private  void loadMenu() {

        adapter =new FirebaseRecyclerAdapter<CategoryModel, MenuViewHolder>(CategoryModel.class,R.layout.menulist,MenuViewHolder.class,reference) {
           @Override
           protected void populateViewHolder(MenuViewHolder viewHolder, CategoryModel model, int i) {
              viewHolder.textMenuName.setText(model.getName());
               Glide.with(viewHolder.imageView.getContext()).load(model.getImage()).into(viewHolder.imageView);
               final CategoryModel clickItem=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        //Get CategoryId and send to new Activity
        Intent foodlist =new Intent(Home2Activity.this, CategoryListActivity.class);
        foodlist.putExtra("CategoryId",adapter.getRef(position).getKey());

         startActivity(foodlist);
    }
});
           }





        };
        recycler_menu.setAdapter(adapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       int id =item.getItemId();
       if(id==R.id.menu_home){
           Intent intent =new Intent(Home2Activity.this,Home2Activity.class);
           startActivity(intent);

       }
         if(id==R.id.menu_cart){
            Intent intent =new Intent(Home2Activity.this,cartActivity.class);
            startActivity(intent);


        }
      if(id==R.id.menu_order){
            Intent intent =new Intent(Home2Activity.this,OrderActivity.class);
            startActivity(intent);

        }
        if(id==R.id.menu_logout){
           Intent intent =new Intent(Home2Activity.this,SignInActivity.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
           finish();

        }
       return super.onOptionsItemSelected(item);
    }
}