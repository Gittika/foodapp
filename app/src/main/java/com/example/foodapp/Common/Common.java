package com.example.foodapp.Common;

import com.example.foodapp.Request;
import com.example.foodapp.User;


public class Common  {
    public static User currentUser;
    public static Request currentRequest;
    public static  String convertCodeToStatus(String status){
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On the way";
        else
            return  "Shipped";
    }


}

