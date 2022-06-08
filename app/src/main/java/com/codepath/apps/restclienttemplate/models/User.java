package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {
    public String name;
    public String screenName;
    public String publicImageURL;

    public User(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setPublicImageURL(String publicImageURL) {
        this.publicImageURL = publicImageURL;
    }

    // method to return a user with parameters gotten from a json object
    public static User fromJson(JSONObject jsonObject) throws JSONException {
//        String name = jsonObject.getString("name");
//        String screenName = jsonObject.getString("screen_name");
//        String publicImageURL = jsonObject.getString("profile_image_url_https");
        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.publicImageURL = jsonObject.getString("profile_image_url_https");
//        user.setName(name);
//        user.setPublicImageURL(publicImageURL);
//        user.setScreenName(screenName);
        return user;
    }
}
