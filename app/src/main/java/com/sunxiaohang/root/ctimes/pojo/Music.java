package com.sunxiaohang.root.ctimes.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Music implements Parcelable {
    private String name;
    private String singer;
    private String imageUrl;
    private String lrcUrl;
    private String musicUrl;
    private int musicDuration;

    public Music(String name, String singer, String imageUrl, String lrcUrl, String musicUrl,int musicDuration) {
        this.name = name;
        this.singer = singer;
        this.imageUrl = imageUrl;
        this.lrcUrl = lrcUrl;
        this.musicUrl = musicUrl;
        this.musicDuration = musicDuration;
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLrcUrl() {
        return lrcUrl;
    }

    public void setLrcUrl(String lrcUrl) {
        this.lrcUrl = lrcUrl;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public int getMusicDuration() {
        return musicDuration;
    }

    public void setMusicDuration(int musicDuration) {
        this.musicDuration = musicDuration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.singer);
        dest.writeString(this.imageUrl);
        dest.writeString(this.lrcUrl);
        dest.writeString(this.musicUrl);
        dest.writeInt(this.musicDuration);
    }
    protected Music(Parcel in) {
        name = in.readString();
        singer = in.readString();
        imageUrl = in.readString();
        lrcUrl = in.readString();
        musicUrl = in.readString();
        musicDuration = in.readInt();
    }
}
