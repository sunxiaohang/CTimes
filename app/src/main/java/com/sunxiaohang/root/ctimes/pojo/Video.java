package com.sunxiaohang.root.ctimes.pojo;

public class Video {
    private String name;
    private String singer;
    private String playCount;
    private String picUrl;
    private String videoUrl;

    public Video(String name, String singer, String playCount, String picUrl, String videoUrl) {
        this.name = name;
        this.singer = singer;
        this.playCount = playCount;
        this.picUrl = picUrl;
        this.videoUrl = videoUrl;
    }

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

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
