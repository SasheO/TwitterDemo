package com.codepath.apps.restclienttemplate;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = "TimelineActivity";
    TwitterClient client;
    List<Tweet> tweetList;
    TweetAdapter adapter;
    // this is the request code for startActivityForResult
    public final int REQUEST_CODE = 20;


    // this is for getting the result from composeTweetActivity, revisit this for a guide: https://guides.codepath.com/android/Using-Intents-to-Create-Flows#returning-data-result-to-parent-activity
    // i put it here because of an error that I got similar to this: https://stackoverflow.com/questions/64476827/how-to-resolve-the-error-lifecycleowners-must-call-register-before-they-are-sta
    ActivityResultLauncher<Intent> editActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // If the user comes back to this activity from EditActivity
                    // with no error or cancellation
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        // Get the data passed from EditActivity
                        Tweet newTweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
                        tweetList.add(0, newTweet);
                        adapter.notifyDataSetChanged();
                        Log.i("TimelineActivity", "tweet added to homepage");
                    }
                }
            });

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

        // added for logout
        Button btnLogout = findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TimelineActivity.this, "logging out...", Toast.LENGTH_LONG);
                onLogoutButton();
            }
        });
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

    void onLogoutButton()
    {
    // forget who is logged in
        TwitterApp.getRestClient(this).clearAccessToken();
        // navigate backwards to Login screen
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // this makes sure the Back button won't work
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // same as above
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.miComposeTweet:
                // Toast.makeText(TimelineActivity.this, "Compose Tweet button clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TimelineActivity.this, ComposeTweetActivity.class);
                editActivityResultLauncher.launch(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    

}