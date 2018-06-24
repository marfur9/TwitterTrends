package com.java;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import twitter4j.Status;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@WebServlet(name = "TrendServlet")
public class TrendServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TwitterAdapter twitterAdapter = new TwitterAdapter();
        String screenName = request.getParameter("screenname");
        int numberOfTweets = Integer.parseInt(request.getParameter("range"));
        List<Status> statuses = twitterAdapter.getTimeline(screenName, numberOfTweets);
        List<TweetDate> tweetDates = twitterAdapter.makeTweetDates(statuses);
        makeJSON(tweetDates, screenName);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        response.setHeader("screenname", screenName);
        request.getRequestDispatcher("/WEB-INF/results.jsp").forward(request, response);
    }

    private void makeJSON(List<TweetDate> tweetDates, String screenName){
        JSONObject jsonTweetDatesTable = new JSONObject();
        JSONArray cols = new JSONArray();
        JSONObject col1 = new JSONObject();
        col1.put("id", "");
        col1.put("label", "Date");
        col1.put("pattern", "");
        col1.put("type", "string");
        cols.add(col1);
        JSONObject col2 = new JSONObject();
        col2.put("id", "");
        col2.put("label", "Average likes");
        col2.put("pattern", "");
        col2.put("type", "number");
        cols.add(col2);
        jsonTweetDatesTable.put("cols", cols);
        JSONArray rows = new JSONArray();
        List<JSONObject> rowsArray = new ArrayList<>();
        for(TweetDate tweetDate : tweetDates){
            JSONObject row = new JSONObject();
            JSONArray c = new JSONArray();
            JSONObject date = new JSONObject();
            JSONObject average = new JSONObject();
            date.put("v", tweetDate.getDate());
            date.put("f", null);
            average.put("v", tweetDate.getLikeAverage());
            average.put("f", null);
            c.add(date);
            c.add(average);
            row.put("c", c);
            rowsArray.add(row);

        }
        ListIterator li = rowsArray.listIterator(rowsArray.size());
        while(li.hasPrevious()){
            rows.add(li.previous());
        }
        jsonTweetDatesTable.put("rows", rows);
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));

        String path = getServletContext().getRealPath("/json/"+ screenName + ".json");

        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonTweetDatesTable.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            //System.out.println("\nJSON Object: " + jsonTweetDates);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
