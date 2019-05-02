package com.sunxiaohang.root.ctimes.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Movies implements Parcelable{
    private String title;
    private String genres;
    private String ratingScore;
    private String collectionCount;
    private ArrayList<Role> roles;
    private ArrayList<Role> directors;
    private String years;
    private String imgUrl;
    private String duration;

    public Movies(String title, String genres, String ratingScore, String collectionCount, ArrayList<Role> roles, ArrayList<Role> directors, String years, String imgUrl,String duration) {
        this.title = title;
        this.genres = genres;
        this.ratingScore = ratingScore;
        this.collectionCount = collectionCount;
        this.roles = roles;
        this.directors = directors;
        this.years = years;
        this.imgUrl = imgUrl;
        this.duration = duration;
    }

    protected Movies(Parcel in) {
        title = in.readString();
        genres = in.readString();
        ratingScore = in.readString();
        collectionCount = in.readString();
        roles = in.createTypedArrayList(Role.CREATOR);
        directors = in.createTypedArrayList(Role.CREATOR);
        years = in.readString();
        imgUrl = in.readString();
        duration = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(String ratingScore) {
        this.ratingScore = ratingScore;
    }

    public String getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(String collectionCount) {
        this.collectionCount = collectionCount;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public ArrayList<Role> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<Role> directors) {
        this.directors = directors;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(genres);
        dest.writeString(ratingScore);
        dest.writeString(collectionCount);
        dest.writeTypedList(roles);
        dest.writeTypedList(directors);
        dest.writeString(years);
        dest.writeString(imgUrl);
        dest.writeString(duration);
    }
}
