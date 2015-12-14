package com.codepath.apps.mysimpletweets;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        lvTweets = (ListView)findViewById(R.id.lvTweets);
        // create array of data source
        tweets = new ArrayList<>();
        //construct adapter from data source
        aTweets = new TweetsArrayAdapter(this, tweets);
        //connect adapter to listview
        lvTweets.setAdapter(aTweets);
        client = TwitterApplication.getRestClient(); // singleton client
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
               aTweets.addAll(Tweet.fromJSONArray(json));
           }

           @Override
           public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
               Log.d("DEBUG", errorResponse.toString());
           }
                               }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        MenuItem composeItem = menu.findItem(R.id.action_compose);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if ( id == R.id.action_compose ) {
            Intent composeInfo = new Intent(TimelineActivity.this, ComposeActivity.class);
            startActivityForResult(composeInfo, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                Bundle bundleData = data.getExtras();
                String editStr = bundleData.getString("ListView.data");

                String usrName = "tammyngo2617";
                String fullName = "Tammy Ngo";

                String imageUrl = "https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678130-profile-alt-4-128.png";
                Tweet tweet = new Tweet();
                tweet.setBody(editStr);

                User user = new User();
                user.setName(fullName);
                user.setScreenName(usrName);
                user.setProfileImageUrl(imageUrl);
                tweet.setUser(user);


                tweets.add(tweet);
                //insert image using picasso
                ImageView imgUser = (ImageView)findViewById(R.id.ivProfileImage);
                Picasso.with(this).load(imageUrl).into(imgUser);
                aTweets.notifyDataSetChanged();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("Cancel", "onActivityResult() with resultCode_cancel = "+ resultCode);
            }
        }
    }
}
