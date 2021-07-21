package com.example.foodapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textMenuName;
    public ImageView imageView;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        textMenuName=(TextView)itemView.findViewById(R.id.Menu_name);
        imageView=(ImageView)itemView.findViewById(R.id.menuImage);
    itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
       itemClickListener.onClick(view,getAbsoluteAdapterPosition(),false);

    }
}
