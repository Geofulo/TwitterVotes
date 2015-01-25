package com.agsystems.twittervote;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import io.fabric.sdk.android.Fabric;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.HashtagEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetEntities;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.LoadCallback;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;
import com.twitter.sdk.android.tweetui.TweetViewFetchAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TimelineActivity extends ActionBarActivity {

    final String HASHTAG_TWIITERVOTE = "FCBLIVE";
    VoteAdapter vote_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_timeline);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            android.support.v7.app.ActionBar action_bar =  getSupportActionBar();
        }
        cargarTweets();
    }

    public void cargarTweets(){
        TwitterSession active_session = Twitter.getSessionManager().getActiveSession();
        //TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        MyTwitterApiClient twitterApiClient = new MyTwitterApiClient(active_session);
        StatusesService statusesService = twitterApiClient.getStatusesService();
        statusesService.homeTimeline(100, null, null, null, null, null, null, new Callback<List<Tweet>>() {

            @Override
            public void success(Result<List<Tweet>> result) {
                List<Tweet> tweets = result.data;
                TweetEntities tweet_entities;
                List<HashtagEntity> hashtags;
                List<Tweet> vote_tweets = new ArrayList<Tweet>();
                Tweet tweet;
                Log.i("IDK!!!!!!!", "SUCCESS!!!!");
                Log.i("TWEETS SIZE", String.valueOf(tweets.size()));
                for(int i=0; i<tweets.size(); i++) {
                    tweet = tweets.get(i);
                    Log.i("TWEETS NAME", tweet.user.name);
                    Log.i("TWEETS TEXT", tweet.text);

                    tweet_entities = tweets.get(i).entities;
                    hashtags = tweet_entities.hashtags;

                    // Si tiene hashtags y no les haya dado retweet o fav
                    if(!hashtags.isEmpty() && !tweet.favorited && !tweet.retweeted){
                        Boolean has_twittervote_hashtag = false;
                        String tweet_hashtag;
                        for(int j=0; j<hashtags.size(); j++){
                            tweet_hashtag = hashtags.get(j).text.toUpperCase();
                            Log.i("TWEET HASHTAG", tweet_hashtag);
                            // Revisamos si tiene nuestro hashtag
                            if(tweet_hashtag.equals(HASHTAG_TWIITERVOTE)){
                                // Agrego el id del tweet
                                Log.i("TWEET HASHTAG MATCH", tweet.text);
                                vote_tweets.add(tweet);
                            }
                        }
                    }
                }

                Log.i("VOTE TWEETS SIZE", String.valueOf(vote_tweets.size()));

                vote_adapter = new VoteAdapter(getBaseContext(), vote_tweets);
                ListView listview_tweets = (ListView) findViewById(R.id.listview_tweets);
                listview_tweets.setAdapter(vote_adapter);
                //setListAdapter(vote_adapter);
            }

            public void failure(TwitterException exception) {
                Log.i("IDK!!!!!!!", "FAILURE!!!!");
                exception.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.action_reload:
                cargarTweets();
                break;
            case R.id.action_new_tweet:
                startActivity(new Intent(this, NewVoteTweet.class));
            case R.id.action_my_tweets:
                Toast.makeText(getApplicationContext(), "My Vote Tweets", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
