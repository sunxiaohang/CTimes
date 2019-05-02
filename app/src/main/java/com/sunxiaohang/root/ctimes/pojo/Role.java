package com.sunxiaohang.root.ctimes.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Role implements Parcelable {
    String id;
    String name;
    String avatarsUrl;

    public Role(String id, String name, String avatarsUrl) {
        this.id = id;
        this.name = name;
        this.avatarsUrl = avatarsUrl;
    }

    protected Role(Parcel in) {
        id = in.readString();
        name = in.readString();
        avatarsUrl = in.readString();
    }

    public static final Creator<Role> CREATOR = new Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel in) {
            return new Role(in);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarsUrl() {
        return avatarsUrl;
    }

    public void setAvatarsUrl(String avatarsUrl) {
        this.avatarsUrl = avatarsUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(avatarsUrl);
    }
}