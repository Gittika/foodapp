package com.example.foodapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.foodapp.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final int DB_VERSION= 1;
    private static final  String DB_NAME="finall.db";
    public Database(Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }
    public List<Order> getCarts(){
        SQLiteDatabase db=getReadableDatabase();
        db.isOpen();

        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String[] sqlSelect ={"ProductName","ProductId","Quantity","Price"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c=qb.query(db,sqlSelect,null,null,null,null,null);

        final  List<Order> result=new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                c.getString(c.getColumnIndex("Quantity")),
                c.getString(c.getColumnIndex("Price"))
                ));

            }while (c.moveToNext());
            c.close();

        }
db.close();
        return result;
    }
    public  void  addToCart(Order order){
        SQLiteDatabase db = getReadableDatabase();
        db.isOpen();
        String query=String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price) VALUES('%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice());
        db.execSQL(query);
        db.close();

    }
    public  void  cleanCart(){
        SQLiteDatabase db=getReadableDatabase();

        String query=String.format("DELETE FROM OrderDetail");

        db.execSQL(query);
        db.close();

    }
}

