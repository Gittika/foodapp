package com.example.foodapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
public TextView food_name;
public ImageView food_image;
public TextView food_price;
public  TextView food_description;
private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        food_name =(TextView)itemView.findViewById(R.id.foodTxt);
        food_price =(TextView)itemView.findViewById(R.id.priceTxt);
        food_image =(ImageView)itemView.findViewById(R.id.img1);

itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAbsoluteAdapterPosition(),false);

    }
}
