package com.example.foodapp;

public class Model {
    private String name, image, description, menuId, price;

    public Model() {

    }

    public Model(String Name, String Imageitem, String Description, String Price, String MenuId) {
        this.description = Description;
        this.price = Price;
        this.image = Imageitem;
        this.menuId = MenuId;
        this.name = Name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}





