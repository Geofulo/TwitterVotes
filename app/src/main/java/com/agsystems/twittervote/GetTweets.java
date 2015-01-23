package com.agsystems.twittervote;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by geovanni on 22/01/15.
 */
public class GetTweets extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... twitter_url) {
        StringBuilder tweet_feed_builder = new StringBuilder();
        for (String search_url : twitter_url){
            HttpClient tweet_client = new DefaultHttpClient();
            try{
                HttpGet tweet_get = new HttpGet(search_url);
                HttpResponse tweet_response = tweet_client.execute(tweet_get);
                StatusLine search_status = tweet_response.getStatusLine();
                if(search_status.getStatusCode() == 200){
                    HttpEntity tweet_entity = tweet_response.getEntity();
                    InputStream tweet_content = tweet_entity.getContent();
                    InputStreamReader tweet_input = new InputStreamReader(tweet_content);
                    BufferedReader tweet_reader = new BufferedReader(tweet_input);
                    String line_in;
                    while ((line_in = tweet_reader.readLine()) != null){
                        tweet_feed_builder.append(line_in);
                    }
                } else{
                    Log.i("ERROR TWITTER: ", "Something wrong! not 200");
                }
            } catch(Exception e){
                Log.i("ERROR TWITTER: ", "Something wrong!");
                e.printStackTrace();
            }
        }
        return tweet_feed_builder.toString();
    }

    protected void onPostExecute(String result){
        StringBuilder tweet_result_builder = new StringBuilder();
        try {
            JSONObject result_object = new JSONObject(result);
            //JSONArray tweet_array = result_object.getJSONArray("id");
            JSONArray tweet_array = result_object.getJSONArray("urls");
            for (int i=0; i<tweet_array.length(); i++){
                JSONObject tweet_object = tweet_array.getJSONObject(i);
                tweet_result_builder.append(tweet_object.getString("url")+": ");
            }
        } catch(Exception e){
            Log.i("ERROR TWITTER POST: ", "Something wrong!");
            e.printStackTrace();
        }
        if(tweet_result_builder.length() > 0){
            Log.i("RESULT BUILDER: ", tweet_result_builder.toString());
        } else{
            Log.i("RESULT BUILDER: ", "No tweets!");
        }
    }
}
