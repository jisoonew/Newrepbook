package com.example.newrepbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class material_info implements Serializable {
    private String material_name;
    private String material_image;
    private String material_UID;
    private int material_price;

    public material_info(String material_name) {
        this.material_name = material_name;
    }

    public String getMaterial_name(){return this.material_name;}
    public void setMaterial_name(String material_name){this.material_name = material_name;}

    public String getMaterial_image(){return this.material_image;}
    public void setMaterial_image(String material_image){this.material_image = material_image;}

    public int getMaterial_price(){return this.material_price;}
    public void setMaterial_price(int material_price){this.material_price = material_price;}

    public String getMaterial_UID(){return this.material_UID;}
    public void setMaterial_UID(String material_UID){this.material_UID = material_UID;}

}
