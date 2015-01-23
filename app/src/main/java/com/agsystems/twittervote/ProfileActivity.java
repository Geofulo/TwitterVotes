package com.agsystems.twittervote;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.twitter.sdk.android.Twitter;


public class ProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
