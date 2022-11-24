package com.example.newrepbook;

import java.io.Serializable;

public class shopping_basket_info implements Serializable {
    private String reckoning_number;
    private String shopping_amount;
    private String shopping_name;

    public shopping_basket_info(String reckoning_number, String shopping_amount, String shopping_name) {
        this.reckoning_number = reckoning_number;
        this.shopping_amount = shopping_amount;
        this.shopping_name = shopping_name;
    }

    public String getReckoning_number(){return this.reckoning_number;}
    public void setReckoning_number(String reckoning_number){this.reckoning_number = reckoning_number;}

    public String getShopping_amount(){return this.shopping_amount;}
    public void setShopping_amount(String shopping_amount){this.shopping_amount = shopping_amount;}

    public String getShopping_name(){return this.shopping_name;}
    public void setShopping_name(String shopping_name){this.shopping_name = shopping_name;}
}
