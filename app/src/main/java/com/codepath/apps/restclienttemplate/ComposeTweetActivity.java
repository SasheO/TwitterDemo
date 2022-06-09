package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeTweetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

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

                    // this toast it temporary, comment out or delete later
                    Toast.makeText(ComposeTweetActivity.this, tweetBody, Toast.LENGTH_LONG).show();

                }

            }
        });


    }


}