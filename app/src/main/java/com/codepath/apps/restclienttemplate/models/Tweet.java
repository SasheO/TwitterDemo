package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {
    public String body;
    public String createdAt;
    public User user;

    public void setBody(String body) {
        this.body = body;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // function to return a tweet populating it with information from a json object
    public static Tweet fromJSONObject(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet(); // unsure if tweet is pass by reference or value, might result in error

        tweet.setBody(jsonObject.getString("text"));
        tweet.setCreatedAt(jsonObject.getString("created_at"));
        tweet.user = new User();
        tweet.user.fromJson(jsonObject.getJSONObject("user"));
        return  tweet;
    }
        // function to return a list of tweets gotten from a json array of tweets
    public static List<Tweet> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweetList = new ArrayList<>();

        for (int i = 0; i<jsonArray.length(); i++){
            tweetList.add(fromJSONObject(jsonArray.getJSONObject(i)));
        }
        return tweetList;
    }
}
