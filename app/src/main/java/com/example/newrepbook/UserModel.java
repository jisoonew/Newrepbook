package com.example.newrepbook;

public class UserModel{
    private String name;
    private String id;
    private String purl;

    public UserModel() {
        super();
    }

    public UserModel(String name, String purl, String id) {
        this.name = name;
        this.id = id;
        this.purl = purl;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", purl='" + purl + '\'' +
                '}';
    }
}
