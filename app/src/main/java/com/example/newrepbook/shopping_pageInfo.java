package com.example.newrepbook;

import java.io.Serializable;

public class shopping_pageInfo implements Serializable {
    private String food_name;
    private String food_image;
    private int food_price;
    private String food_amount;
    private String food_area_of_production;
    private String food_feature;
    private String food_storage;

    public shopping_pageInfo(String food_name, String food_image, int food_price, String food_amount, String food_area_of_production, String food_feature, String food_storage) {
        this.food_name = food_name;
        this.food_image = food_image;
        this.food_price = food_price;
        this.food_amount = food_amount;
        this.food_area_of_production = food_area_of_production;
        this.food_feature = food_feature;
        this.food_storage = food_storage;
    }

    public String getFood_name(){return this.food_name;}
    public void setFood_name(String food_name){this.food_name = food_name;}

    public String getFood_image(){return this.food_image;}
    public void setFood_image(String food_image){this.food_image = food_image;}

    public int getFood_price(){return this.food_price;}
    public void setFood_price(int food_price){this.food_price = food_price;}

    public String getFood_amount(){return this.food_amount;}
    public void setFood_amount(String food_amount){this.food_amount = food_amount;}

    public String getFood_area_of_production(){return this.food_area_of_production;}
    public void setFood_area_of_production(String food_area_of_production){this.food_area_of_production = food_area_of_production;}

    public String getFood_feature(){return this.food_feature;}
    public void setFood_feature(String food_feature){this.food_feature = food_feature;}

    public String getFood_storage(){return this.food_storage;}
    public void setFood_storage(String food_storage){this.food_storage = food_storage;}

}
