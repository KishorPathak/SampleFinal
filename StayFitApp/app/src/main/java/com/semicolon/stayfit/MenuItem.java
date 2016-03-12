package com.semicolon.stayfit;

/**
 * Created by ahmed_anwar on 11/17/2015.
 */
public class MenuItem {
    private String mName;
    private String mDes;

    public String getWieght() {
        return wieght;
    }

    public void setWieght(String wieght) {
        this.wieght = wieght;
    }

    private String wieght;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;
    private int mThumbnail;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDes() {
        return mDes;
    }

    public void setDes(String des) {
        this.mDes = des;
    }

    public int getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.mThumbnail = thumbnail;
    }
}
