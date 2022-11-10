package com.example.newrepbook;

import java.io.Serializable;
import java.util.List;

public class Model {
    String title, item1, item2, item3;

    public Model() {}

    public Model(String title, String item1, String item2, String item3) {
        this.title = title;
        this.item1 = item1;
        this.item1 = item2;
        this.item1 = item3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

}
