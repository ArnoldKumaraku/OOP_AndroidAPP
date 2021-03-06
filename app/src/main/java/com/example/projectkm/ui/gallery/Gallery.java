package com.example.projectkm.ui.gallery;
public class Gallery {
    private String title;
    private String date;
    private String time;
    private String memo;
    private String address;

    public Gallery(String title, String date, String time, String memo, String address) {
        this.title=title;
        this.date=date;
        this.time=time;
        this.memo=memo;
        this.address=address;
    }

    public Gallery() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }
}
