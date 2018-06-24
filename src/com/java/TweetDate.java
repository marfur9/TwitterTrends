package com.java;

public class TweetDate {
    private String date;
    private double likeTotal, totalPosts, likeAverage;

    public TweetDate(String date, double initialLikeCount){
        this.date = date;
        this.likeTotal = initialLikeCount;
        this.totalPosts = 1;
        this.likeAverage = 0;
    }


    //getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(double totalPosts) {
        this.totalPosts = totalPosts;
    }

    public double getLikeTotal() {
        return likeTotal;
    }

    public void setLikeTotal(double likeTotal) {
        this.likeTotal = likeTotal;
    }

    public double getLikeAverage() {
        return likeAverage;
    }

    public void setLikeAverage(double likeAverage) {
        this.likeAverage = likeAverage;
    }
}
