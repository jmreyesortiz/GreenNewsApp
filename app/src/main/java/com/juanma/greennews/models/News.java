package com.juanma.greennews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Model class for news.

public class News{


    private int newsTrigger;

    @SerializedName("source")
    @Expose()
    private Sources source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    @Override
    public String toString() {
        return "News{" +
                "source=" + source +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Sources getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public int getNewsTrigger() {
        return newsTrigger;
    }

    public void setNewsTrigger(int newsTrigger) {
        this.newsTrigger = newsTrigger;
    }

    public void setSource(Sources source) {
        this.source = source;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


}





//    private String author;
//    private String title;
//    private String description;
//    private String url;
//    private String urlToImage;
//    private String publishedAt;
//    private String [] source;