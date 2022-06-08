package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = "TimelineActivity";
    TwitterClient client;
    List<Tweet> tweetList;
    TweetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        // to find the recycler view then use the adapter to populate it
        RecyclerView rvHomeTimeline = findViewById(R.id.rvHomeTimeline);
        // init the list of tweets and adapter
        tweetList = new ArrayList<>();
        adapter = new TweetAdapter(this, tweetList);
        // Recycler view setup: layout and manager
        rvHomeTimeline.setAdapter(adapter);

        // set a Layout manager on the adapter view
        rvHomeTimeline.setLayoutManager(new LinearLayoutManager(this));

        client = TwitterApp.getRestClient(TimelineActivity.this);
        populateHomeTimeline();
    }

        private void populateHomeTimeline() {
        // here, you will be populating the tweet list

            client.getHomeTimeline(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    JSONArray jsonArray = json.jsonArray;
                    try {
                        tweetList.addAll(Tweet.fromJSONArray(jsonArray)); // insert a json array
                        // notify adapter data set changed
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e(TAG, "Json exception");
                        e.printStackTrace();
                    }
                    Log.i(TAG, "onSuccess" + json.toString());
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.e(TAG, "onFailure" + response, throwable);

                }
            });
    }

}