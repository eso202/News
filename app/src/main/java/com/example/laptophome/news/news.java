package com.example.laptophome.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class news {

    @SerializedName("status")

    @Expose
    private String status;

    @SerializedName("totalResults")
    @Expose
    private int totalResults;

    @SerializedName("articles")
    @Expose
    private List<articles> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalresult() {
        return totalResults;
    }

    public void setTotalresult(int totalresult) {
        this.totalResults = totalresult;
    }

    public List<articles> getArticles() {
        return articles;
    }

    public void setArticles(List<articles> articles) {
        this.articles = articles;
    }
}
