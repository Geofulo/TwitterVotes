package com.agsystems.twittervote;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;


public class ProfileActivity extends ActionBarActivity {

    TextView tv_username;
    TextView tv_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TwitterSession active_session = Twitter.getSessionManager().getActiveSession();
        active_session.getUserId();

        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_username.setText("Username: " + active_session.getUserName());
        tv_id.setText("Id: " + active_session.getUserId());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                Twitter.getSessionManager().clearActiveSession();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.action_timeline:
                //startActivity(new Intent(this, TweetListActivity.class));
                startActivity(new Intent(this, TimelineActivity.class));
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
