package com.example.a79875.news;

public class Title {
    private String title;
    private String description;
    private String imageUrl;
    private String uri;

    public Title(String title, String description, String imageUrl, String uri){
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.uri = uri;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getUri(){
        return uri;
    }

}
