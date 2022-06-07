package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    Context context;
    List<Tweet> listTweet;

    public TweetAdapter(Context context, List<Tweet> listTweet) {
    this.context = context;
    this.listTweet = listTweet;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUserPic;
        TextView tvUsername;
        TextView tvTweetBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserPic = itemView.findViewById(R.id.ivUserPic);
            tvTweetBody = itemView.findViewById(R.id.tvTweetBody);
            tvUsername = itemView.findViewById(R.id.tvUsername);
        }

        public void bind(Tweet tweet) {
            tvUsername.setText(tweet.user.screenName);
            tvTweetBody.setText(tweet.body);
            Glide.with(context).load(tweet.user.publicImageURL).into(ivUserPic);
        }
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



}
