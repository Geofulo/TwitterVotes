package com.agsystems.twittervote;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

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


public class TimelineActivity extends ListActivity {

    final TweetViewFetchAdapter adapter = new TweetViewFetchAdapter<CompactTweetView>(TimelineActivity.this);
    List<Long> tweetsIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setListAdapter(adapter);
        cargarTweets();
    }

    public void cargarTweets(){
        TwitterSession active_session = Twitter.getSessionManager().getActiveSession();
        //TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        MyTwitterApiClient twitterApiClient = new MyTwitterApiClient(active_session);
        StatusesService statusesService = twitterApiClient.getStatusesService();
        statusesService.homeTimeline(20, null, null, null, null, null, null, new Callback<List<Tweet>>() {

            @Override
            public void success(Result<List<Tweet>> result) {
                List<Tweet> tweets = result.data;
                TweetEntities tweet_entities;
                List<HashtagEntity> hashtags;
                Tweet tweet;
                Log.i("IDK!!!!!!!", "SUCCESS!!!!");
                Log.i("TWEETS SIZE", String.valueOf(tweets.size()));
                for(int i=0; i<tweets.size(); i++) {
                    tweet = tweets.get(i);
                    Log.i("TWEETS NAME", tweets.get(i).user.name);
                    Log.i("TWEETS TEXT", tweets.get(i).text);

                    tweet_entities = tweets.get(i).entities;
                    hashtags = tweet_entities.hashtags;
                    for(int j=0; j<hashtags.size(); j++){
                        Log.i("TWEET HASHTAG", hashtags.get(j).text);
                    }
                    // Si tiene hashtags y no les haya dado retweet o fav

                    if(!hashtags.isEmpty()){
                       if(!tweet.favorited && !tweet.retweeted){
                           // Agrego el id del tweet
                           tweetsIds.add(tweet.id);
                       }
                    }

                }

                adapter.setTweetIds(tweetsIds, new LoadCallback<List<Tweet>>() {
                    @Override
                    public void success(List<Tweet> tweets) {
                        // my custom actions
                        Log.i("ADAPTER!!!!!!!", "SUCCESS!!!!");
                    }
                    @Override
                    public void failure(TwitterException exception) {
                        // Toast.makeText(...).show();
                        exception.printStackTrace();
                    }
                });
            }

            public void failure(TwitterException exception) {
                //Do something on failure
                Log.i("IDK!!!!!!!", "FAILURE!!!!");
                exception.printStackTrace();
            }
        });

        Log.i("TWEETS IDS SIZE", String.valueOf(tweetsIds.size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.action_reload:
                cargarTweets();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
