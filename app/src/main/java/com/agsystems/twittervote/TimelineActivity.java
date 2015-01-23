package com.agsystems.twittervote;

import android.app.ListActivity;
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
import com.twitter.sdk.android.core.models.Tweet;
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

    final TweetViewFetchAdapter adapter =
            new TweetViewFetchAdapter<CompactTweetView>(TimelineActivity.this);
    List<Long> tweetsIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        setListAdapter(adapter);

        TwitterSession active_session = Twitter.getSessionManager().getActiveSession();
        //TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        MyTwitterApiClient twitterApiClient = new MyTwitterApiClient(active_session);
        StatusesService statusesService = twitterApiClient.getStatusesService();
        statusesService.homeTimeline(10, null, null, null, null, null, null, new Callback<List<Tweet>>() {

            @Override
            public void success(Result<List<Tweet>> result) {
                List<Tweet> tweets = result.data;
                Log.i("IDK!!!!!!!", "SUCCESS!!!!");
                Log.i("TWEETS SIZE", String.valueOf(tweets.size()));
                for(int i=0; i<tweets.size(); i++) {
                    Log.i("TWEETS NAME", tweets.get(i).user.name);
                    Log.i("TWEETS TEXT", tweets.get(i).text);
                    tweetsIds.add(tweets.get(i).id);
                }
            }

            public void failure(TwitterException exception) {
                //Do something on failure
                Log.i("IDK!!!!!!!", "FAILURE!!!!");
                exception.printStackTrace();
            }
        });

        Log.i("TWEETS IDS SIZE", String.valueOf(tweetsIds.size()));

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
