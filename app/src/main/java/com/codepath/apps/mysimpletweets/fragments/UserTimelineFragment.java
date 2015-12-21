package com.codepath.apps.mysimpletweets.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by tammy.ngo on 12/16/2015.
 */
public class UserTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    Activity activity;
    ArrayList<Tweet> arrTweet;
    String fullName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity)context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient(); // singleton client
        populateTimeline();


    }

    public static UserTimelineFragment newInstance(String screenName) {

        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userFragment.setArguments(args);
        return userFragment;
    }

    // send api request to get Timeline json
    // Fill the listview by creating twitter object from json
    private void populateTimeline() {

        final String screenName = getArguments().getString("screen_name");
        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
               // on success

               @Override
               public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                   Log.d("DEBUG", json.toString());
                   arrTweet = Tweet.fromJSONArray(json);
                   User user;

                   for (int i = 0; i < arrTweet.size(); i++) {
                       user = arrTweet.get(i).getUser();
                       String name = user.getScreenName();
                       if (name.equals(screenName)) {
                            fullName = user.getName();
                            break;
                       }
                   }
                   // load model data in listview
                   addAll(arrTweet);

               }

               @Override
               public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                   Log.d("DEBUG", errorResponse.toString());
               }
           }
        );
    }

}
