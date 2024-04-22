package com.helloworldapps.campusbuzz;

public class Data {
    public String subject;
    public String message;
    public String imageUrl;
    public String date,time;

    public Data() {
    }

    public Data(String subject, String message, String imageUrl, String date, String time) {
        this.subject = subject;
        this.message=message;
        this.imageUrl = imageUrl;
        this.date = date;
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}