package com.agsystems.twittervote;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geovanni on 23/01/15.
 */
public class VoteAdapter extends BaseAdapter{

    private Context context;
    private List<Tweet> vote_tweets;

    public VoteAdapter(Context context, List<Tweet> vote_tweets){
        this.context = context;
        this.vote_tweets = vote_tweets;
    }

    @Override
    public int getCount() {
        return vote_tweets.size();
    }

    @Override
    public Object getItem(int position) {
        return vote_tweets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vote_tweets.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater layout_inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layout_inflater.inflate(R.layout.item_vote_tweet, null);

            //v = inflater.inflate(R.layout.item_vote_tweet, parent, false);
        }
        //VoteTweet vote_tweet = vote_tweets.get(position);
        final Tweet vote_tweet = vote_tweets.get(position);

        TextView tv_username_vote_tweet = (TextView) v.findViewById(R.id.tv_username_vote_tweet);
        TextView tv_email_vote_tweet = (TextView) v.findViewById(R.id.tv_email_vote_tweet);
        TextView tv_text_vote_tweet = (TextView) v.findViewById(R.id.tv_text_vote_tweet);
        final Button btn_hashtag1_vote_tweet = (Button) v.findViewById(R.id.btn_hashtag1_vote_tweet);
        final Button btn_hashtag2_vote_tweet = (Button) v.findViewById(R.id.btn_hashtag2_vote_tweet);

        tv_username_vote_tweet.setText(vote_tweet.user.name);
        tv_email_vote_tweet.setText("@" + vote_tweet.user.screenName);
        tv_text_vote_tweet.setText(vote_tweet.text);
        //btn_hashtag1_vote_tweet.setText(vote_tweet.getHashtag1());
        //btn_hashtag2_vote_tweet.setText(vote_tweet.getHashtag2());

        final String choice2 = String.valueOf(vote_tweets.get(position));
        btn_hashtag1_vote_tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = btn_hashtag1_vote_tweet.getText().toString();
                Toast.makeText(context, choice + "" + choice2, Toast.LENGTH_SHORT).show();
            }
        });

        btn_hashtag2_vote_tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = btn_hashtag2_vote_tweet.getText().toString();
                Toast.makeText(context, choice + "" + choice2, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
