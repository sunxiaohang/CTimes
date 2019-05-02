package com.sunxiaohang.root.ctimes.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable{
    private String title;
    private String author;
    private String publishDate;
    private String publisher;
    private String price;
    private String rateStar;
    private String numberRaters;
    private String imgUrl;
    private String authorIntroduction;
    private String bookIntroduction;

    public Book(String title, String author, String publishDate, String publisher, String price, String rateStar, String numberRaters,String imgUrl,String authorIntroduction,String bookIntroduction) {
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.price = price;
        this.rateStar = rateStar;
        this.numberRaters = numberRaters;
        this.imgUrl = imgUrl;
        this.bookIntroduction = bookIntroduction;
        this.authorIntroduction = authorIntroduction;
    }

    public String getAuthorIntroduction() {
        return authorIntroduction;
    }

    public void setAuthorIntroduction(String authorIntroduction) {
        this.authorIntroduction = authorIntroduction;
    }

    public String getBookIntroduction() {
        return bookIntroduction;
    }

    public void setBookIntroduction(String bookIntroduction) {
        this.bookIntroduction = bookIntroduction;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRateStar() {
        return rateStar;
    }

    public void setRateStar(String rateStar) {
        this.rateStar = rateStar;
    }

    public String getNumberRaters() {
        return numberRaters;
    }

    public void setNumberRaters(String numberRaters) {
        this.numberRaters = numberRaters;
    }

    //parcelable default creator
    public static final Parcelable.Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString());
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publishDate);
        dest.writeString(publisher);
        dest.writeString(price);
        dest.writeString(rateStar);
        dest.writeString(numberRaters);
        dest.writeString(imgUrl);
        dest.writeString(authorIntroduction);
        dest.writeString(bookIntroduction);
    }
}
