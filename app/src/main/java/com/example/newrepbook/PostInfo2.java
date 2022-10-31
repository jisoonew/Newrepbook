package com.example.newrepbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PostInfo2 implements Serializable {
    private String title;
    private ArrayList<String> contents;
    private String publisher;
    private Date createdAt;
    private String profile;

    public PostInfo2(String title, ArrayList<String> contents, String publisher, Date createdAt, String profile){
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.profile = profile;
    }

    public String getTitle(){return this.title;}
    public void setTitle(String title){this.title = title;}
    public ArrayList<String> getContents(){return this.contents;}
    public void setContents(ArrayList<String> contents){this.contents = contents;}
    public String getPublisher(){return this.publisher;}
    public void setPublisher(String publisher){this.publisher = publisher;}
    public Date getCreatedAt(){return this.createdAt;}
    public void setCreatedAt(Date createdAt){this.createdAt = createdAt;}
    public String getProfile(){return this.profile;}
    public void setProfile(String profile){this.profile = profile;}
}
