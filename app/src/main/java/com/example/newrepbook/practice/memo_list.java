package com.example.newrepbook.practice;

public class memo_list {
    private String image;
    private String name; //제목
    private String address; //내용
    private String uid;

    public memo_list() {}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    //값을 추가할때 쓰는 함수
    public memo_list(String image, String name, String address, String uid){
        this.image = image;
        this.name = name;
        this.address = address;
        this.uid = uid;
    }
}
