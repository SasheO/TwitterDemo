package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TweetDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        TextView tvTweetBody = findViewById(R.id.tvTweetBody);
        TextView tvUsername = findViewById(R.id.tvUsername);

    }
}