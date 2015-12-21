package com.codepath.apps.mysimpletweets.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TimelineActivity;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by tammy.ngo on 12/15/2015.
 */
public class HomeTimelineFragment extends TweetsListFragment implements AdapterView.OnItemClickListener
{

    private TwitterClient client;
    Activity activity;

   ListView lvTweets;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity)context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvTweets = getLvTweets();
        Log.d("onActivityCreated", lvTweets.toString());

        lvTweets.setOnItemClickListener(this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient(); // singleton client
        Log.d("client on create", client.toString());

        populateTimeline();

    }

    // send api request to get Timeline json
    // Fill the listview by creating twitter object from json
    private void populateTimeline(){

        client.getHomeTimeline(new JsonHttpResponseHandler() {
        // on success
               @Override
               public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                   //   Log.d("DEBUG", json.toString());
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {

            ArrayList<Tweet> tweetList = getTweets();
            if ( tweetList != null ) {
                Tweet tweet = tweetList.get(position);

                 User user = tweet.getUser();
                ((OnItemSelectedListener)activity).onItemSelected(position, user);
            }

        }
        catch (ClassCastException e){
          e.toString();
        }
    }

    public interface OnItemSelectedListener{
        public void onItemSelected(int pos, User user);
    }

}
