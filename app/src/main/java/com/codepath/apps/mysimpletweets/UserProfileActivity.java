package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        client = TwitterApplication.getRestClient();

        Bundle bd = getIntent().getExtras();
        String name = bd.getString("full_name");
        String imageUrl = bd.getString("imageUrl");
        String screenName = bd.getString("screen_name");
        String tagLine = bd.getString("tagLine");
        int followers = bd.getInt("followers");
        int following = bd.getInt("following");
        user = new User();
        user.setName(name);
        user.setScreenName(screenName);
        user.setTagline(tagLine);
        user.setProfileImageUrl(imageUrl);
        user.setFollowersCount(followers);
        user.setFollowingCount(following);

        populateProfileHeader(user);

        if ( savedInstanceState == null ) {
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }


    private void populateProfileHeader(User user) {
        TextView tvName = (TextView)findViewById(R.id.tvFullName);
        TextView tvTagline = (TextView)findViewById(R.id.tvTagLine);
        TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFriendsCount() + " Following");
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);

    }

}
