package com.java;

import twitter4j.*;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationContext;

import java.io.FileWriter;
import java.io.IOException;
import java.text.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class TwitterAdapter {
    private Twitter twitter;

    TwitterAdapter(){
        OAuthAuthorization authorization = new OAuthAuthorization(ConfigurationContext.getInstance());
        twitter = new TwitterFactory().getInstance(authorization);
    }



    List<TweetDate> makeTweetDates(List<Status> statuses){
        List<TweetDate> tweetDates = new ArrayList<>();

        for(Status status : statuses){
            if(dateExists(status, tweetDates)){
                for (TweetDate current : tweetDates){
                    if(current.getDate().equals(changeDateFormat(status.getCreatedAt()))){
                        current.setLikeTotal(current.getLikeTotal()+status.getFavoriteCount());
                        current.setTotalPosts(current.getTotalPosts()+1);
                    }
                }
            } else {
                String date = changeDateFormat(status.getCreatedAt());
                int likeCount = status.getFavoriteCount();
                TweetDate newTweetDate = new TweetDate(date, likeCount);
                tweetDates.add(newTweetDate);
            }
        }

        tweetDates = updateLikeAverage(tweetDates);

        return tweetDates;
    }

    List<Status> getTimeline(String screenName) { //returns a list of tweets from the last xx minutes
        List<Status> statuses = new ArrayList<>();
        try {
            //Get last 200 tweets from timeline and mentions
            //Paging paging = new Paging().sinceId();
            User user = twitter.showUser(screenName);
            int numStatuses = user.getStatusesCount();
            System.out.println(numStatuses);

            Paging paging = new Paging(1, 200);
            statuses = twitter.getUserTimeline(screenName, paging);
            statuses = removeRetweeets(statuses);

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
        return statuses;
    }

    //help methods

    private List<Status> removeRetweeets(List<Status> statuses){
        List<Status> onlyOwnTweets = new ArrayList<>();
        for(Status status : statuses){
            if (!status.isRetweet()){
                onlyOwnTweets.add(status);
            }
        }


        return onlyOwnTweets;
    }

    private boolean dateExists(Status status, List<TweetDate> tweetDates) {
        for(TweetDate date : tweetDates){
            if(date.getDate().equals(changeDateFormat(status.getCreatedAt()))){
                return true;
            }
        }
        return false;

    }

    private String changeDateFormat (Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = df.format(date);
        return dateString;
    }

    private List<TweetDate> updateLikeAverage (List<TweetDate> tweetDates){

        for(TweetDate current : tweetDates){
            DecimalFormat df = new DecimalFormat("#.##");
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

            double totalLikes = current.getLikeTotal();
            double totalPosts = current.getTotalPosts();
            double average = totalLikes/totalPosts;
            String formatted = df.format(average);
            try {
                Number number = format.parse(formatted);
                average = number.doubleValue();
            }catch(ParseException e){
                e.printStackTrace();
            }
            current.setLikeAverage(average);
        }
        return tweetDates;
    }
}
