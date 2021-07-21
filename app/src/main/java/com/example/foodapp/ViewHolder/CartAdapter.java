package com.example.foodapp.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.Order;
import com.example.foodapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.R;

class  CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txt_cart_Name, txt_price;
    public ImageView img_cart_count;
    private ItemClickListener itemClickListener;

    public void setTxt_cart_Name(TextView txt_cart_Name) {
        this.txt_cart_Name = txt_cart_Name;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_cart_Name = (TextView) itemView.findViewById(com.example.foodapp.R.id.CartFood_name);

        txt_price = (TextView) itemView.findViewById(com.example.foodapp.R.id.CartFood_price);
    }

    @Override
    public void onClick(View v) {

    }
}
    public class CartAdapter extends  RecyclerView.Adapter<CartViewHolder> {
           private List<Order> listData  =new ArrayList<>();
           private Context context;

        public CartAdapter(List<Order> listData, Context context) {
            this.listData=listData;
            this.context = context;
        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater =LayoutInflater.from(context);
            View itemView =inflater.inflate(com.example.foodapp.R.layout.cart_layout,parent,false);
            return  new CartViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
            holder.txt_price.setText(Integer.toString(Integer.parseInt(listData.get(position).getPrice())*(Integer.parseInt(listData.get(position).getQuantity()))));
            holder.txt_cart_Name.setText(listData.get(position).getProductName());
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }
    }
