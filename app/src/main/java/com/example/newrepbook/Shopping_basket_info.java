package com.example.newrepbook;

import java.io.Serializable;

public class Shopping_basket_info implements Serializable {
    private String reckoning_number;
    private String shopping_amount;
    private String shopping_name;
    private String shopping_image;
    private int shopping_price;

    public Shopping_basket_info() {

    }

    public Shopping_basket_info(String reckoning_number, String shopping_amount, String shopping_name, String shopping_image, int shopping_price) {
        this.reckoning_number = reckoning_number;
        this.shopping_amount = shopping_amount;
        this.shopping_name = shopping_name;
        this.shopping_image = shopping_image;
        this.shopping_price = shopping_price;
    }

    public String getReckoning_number() {
        return reckoning_number;
    }

    public void setReckoning_number(String reckoning_number) {
        this.reckoning_number = reckoning_number;
    }

    public String getShopping_amount() {
        return shopping_amount;
    }

    public void setShopping_amount(String shopping_amount) {
        this.shopping_amount = shopping_amount;
    }

    public String getShopping_name() {
        return shopping_name;
    }

    public void setShopping_name(String shopping_name) {
        this.shopping_name = shopping_name;
    }

    public String getShopping_image() {
        return shopping_image;
    }

    public void setShopping_image(String shopping_image) {
        this.shopping_image = shopping_image;
    }

    public int getShopping_price() {
        return shopping_price;
    }

    public void setShopping_price(int shopping_price) {
        this.shopping_price = shopping_price;
    }
}