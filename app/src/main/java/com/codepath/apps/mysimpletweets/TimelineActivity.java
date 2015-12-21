package com.codepath.apps.mysimpletweets;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity implements HomeTimelineFragment.OnItemSelectedListener{

 //   HomeTimelineFragment homeTimelineFragment;
 //   SmartFragmentStatePagerAdapter adapterViewpager;
    LayoutInflater mLayoutInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // get viewpager
        ViewPager vpPager = (ViewPager)findViewById(R.id.viewpager);
        // set view adatper for viewpager
        TweetsPagerAdapter pagerAdapter = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(pagerAdapter);

        // find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // attached tabstrip to viewpager
        tabStrip.setViewPager(vpPager);

    }

    @Override
    public void onItemSelected(int position, User user) {

        Log.d("position select = ", Integer.toString(position));
        Intent i = new Intent(TimelineActivity.this, UserProfileActivity.class);
        String screenName = user.getScreenName();
        String tag = user.getTagline();
        String name = user.getName();
        String image = user.getProfileImageUrl();
        int followers = user.getFollowersCount();
        int following = user.getFriendsCount();

        i.putExtra("screen_name", screenName);
        i.putExtra("tagLine", tag);
        i.putExtra("full_name", name);
        i.putExtra("imageUrl", image);
        i.putExtra("followers", followers);
        i.putExtra("following", following);

        startActivity(i);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_timeline, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if ( id == R.id.miProfile ) {
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


  // return order of fragments in view pager
    public class TweetsPagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = {"Home", "Mentions"};

        // adatper get manager to insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if ( position == 0 ){
                return new HomeTimelineFragment();
            }
            else if ( position == 1 ){
                return new MentionsTimelineFragment();
            }
            else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }


  }
}
