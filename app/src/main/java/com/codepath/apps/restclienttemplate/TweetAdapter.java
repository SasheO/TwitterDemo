package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    Context context;
    List<Tweet> listTweet;
    private static final String TAG = "TweetAdapter";
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public TweetAdapter(Context context, List<Tweet> listTweet) {
    this.context = context;
    this.listTweet = listTweet;
    }

    // this inflates the layout for each row
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_tweet, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // this binds the data to the viewholder based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Tweet tweet = listTweet.get(position);
    holder.bind(tweet); // this is defined elsewhere in the class
    }

    // this tells android how many items in total the recycler view is dealing with
    @Override
    public int getItemCount() {
        return listTweet.size();
    }

    // this is to convert time created at to relative time, got this from https://gist.github.com/nesquena/f786232f5ef72f6e10a7
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {
            long time = sf.parse(rawJsonDate).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (ParseException e) {
            Log.i(TAG, "getRelativeTimeAgo failed");
            e.printStackTrace();
        }

        return "";
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUserPic;
        TextView tvUsername;
        TextView tvTweetBody;
        ImageView ivMedia;
        TextView tvCreatedAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserPic = itemView.findViewById(R.id.ivUserPic);
            tvTweetBody = itemView.findViewById(R.id.tvTweetBody);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
        }

        public void bind(Tweet tweet) {
            tvUsername.setText(tweet.user.screenName);
            tvTweetBody.setText(tweet.body);
            tvCreatedAt.setText(getRelativeTimeAgo(tweet.createdAt));
            Glide.with(context).load(tweet.user.publicImageURL).into(ivUserPic);
            // if there is an image url i.e. there is a json response for a url of the pic
            if (tweet.imageUrl != null) {
                // set the image
                Glide.with(context).load(tweet.imageUrl).into(ivMedia);
            }
            else{
                // make the imageView invisible so that it doesn't cause issues in the layout of the page
                ivMedia.setVisibility(View.GONE);
            }
        }
    }


    // helper function to implement pull to refresh
    public void clear() {
        listTweet.clear();
        notifyDataSetChanged();
    }

    // helper function to implement pull to refresh
    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        listTweet.addAll(list);
        notifyDataSetChanged();
    }

}
