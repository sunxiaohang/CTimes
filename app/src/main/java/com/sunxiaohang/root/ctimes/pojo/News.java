package com.sunxiaohang.root.ctimes.pojo;

public class News {
    private String title;
    private String newsAbstract;
    private String url;
    private String author;
    private String published_date;
    private String type;
    private String imageOneUrl;

    public News(String title, String newsAbstract, String url, String author, String published_date, String type, String imageOneUrl) {
        this.title = title;
        this.newsAbstract = newsAbstract;
        this.url = url;
        this.author = author;
        this.published_date = published_date;
        this.type = type;
        this.imageOneUrl = imageOneUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsAbstract() {
        return newsAbstract;
    }

    public void setNewsAbstract(String newsAbstract) {
        this.newsAbstract = newsAbstract;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageOneUrl() {
        return imageOneUrl;
    }

    public void setImageOneUrl(String imageOneUrl) {
        this.imageOneUrl = imageOneUrl;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", newsAbstract='" + newsAbstract + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", published_date='" + published_date + '\'' +
                ", type='" + type + '\'' +
                ", imageOneUrl='" + imageOneUrl + '\'' +
                '}';
    }
}
