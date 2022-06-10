package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String imageUrl;
    public String id_str;

    public void setBody(String body) {
        this.body = body;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(String id_str) {
        this.id_str = id_str;
    }

    public Tweet(){}

    // function to return a tweet populating it with information from a json object
    public static Tweet fromJSONObject(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet(); // unsure if tweet is pass by reference or value, might result in error

        tweet.setBody(jsonObject.getString("full_text"));
        tweet.setCreatedAt(jsonObject.getString("created_at"));
        if (jsonObject.getJSONObject("entities").has("media")) {
            tweet.setImageUrl(jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https"));
        }
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.setId(jsonObject.getString("id_str"));
//        tweet.user = new User();
//        tweet.user.fromJson(jsonObject.getJSONObject("user"));
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
