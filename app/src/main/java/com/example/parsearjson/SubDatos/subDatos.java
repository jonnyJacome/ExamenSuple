package com.example.parsearjson.SubDatos;

public class subDatos {
    String title,date,pdf;

    public subDatos(String title, String date, String pdf) {
        this.title = title;
        this.date = date;
        this.pdf = pdf;
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

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
