package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

/**
 * Created by tammy.ngo on 12/15/2015.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient(); // singleton client
        populateTimeline();

    }

    // send api request to get Timeline json
    // Fill the listview by creating twitter object from json
    private void populateTimeline(){


        client.getMentionsTimeline(new JsonHttpResponseHandler() {
                                   // on success

               @Override
               public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                      Log.d("DEBUG_Mentions", json.toString());
                   // load model data in listview
                   addAll(Tweet.fromJSONArray(json));
               }

               @Override
               public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                   Log.d("DEBUG", errorResponse.toString());
               }
           }
        );
    }
}
