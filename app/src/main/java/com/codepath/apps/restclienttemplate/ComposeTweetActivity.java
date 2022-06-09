package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import okhttp3.Headers;

public class ComposeTweetActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        // get reference to client parameter
        client = TwitterApp.getRestClient(ComposeTweetActivity.this);
        EditText etTypeTweet = findViewById(R.id.etTypeTweet);
        Button btnPostTweet = findViewById(R.id.btnPostTweet);
        btnPostTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // what will happen when the user clicks on the button to Post Tweets?
                // todo: When the button is clicked, it should check if the text in the text component is empty or too long (max Tweet length is 280 characters). Use a Toast to display the error, or display the composed Tweet if it fits the length constraints.

                // get the text input
                String tweetBody = etTypeTweet.getText().toString();
                if (tweetBody.length() == 0){
                    Toast.makeText(ComposeTweetActivity.this, "Cannot make empty tweet", Toast.LENGTH_LONG).show();
                }
                else if (tweetBody.length() >= 80){
                    Toast.makeText(ComposeTweetActivity.this, " Character limit: 280 characters", Toast.LENGTH_LONG).show();

                }
                else{
                    // tweet whatever they typed in

                    // the toast below it temporary, comment out or delete later
                    // Toast.makeText(ComposeTweetActivity.this, tweetBody, Toast.LENGTH_LONG).show();
                    client.publishTweet(new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i("ComposeTweetActivity", "successfully published tweet");
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.e("ComposeTweetActivity", "could not publish tweet");

                        }
                    }, tweetBody);
                    finish();
                }

            }
        });


    }



}