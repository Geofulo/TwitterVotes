package com.agsystems.twittervote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;


public class InitialActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "eusEd78KqUDYsClSqYAKjdeeU";
    private static final String TWITTER_SECRET = "C4oARBEXPHY3h99FWzcTdk1l9NT4CgH2XvVehFw7A4AlggiNIs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());

        setContentView(R.layout.activity_initial);

        TwitterSession active_session = Twitter.getSessionManager().getActiveSession();
        if (active_session != null) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}
