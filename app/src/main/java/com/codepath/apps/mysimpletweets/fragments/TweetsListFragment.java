package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tammy.ngo on 12/15/2015.
 */
public class TweetsListFragment extends Fragment {

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public TweetsArrayAdapter getaTweets() {
        return aTweets;
    }

    public ListView getLvTweets() {
        return lvTweets;
    }

    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;


    //inflation logic
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView)v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        return v;
    }

    // creation of lifecycle event
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create array of data source
        tweets = new ArrayList<>();
        //construct adapter from data source
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
        //connect adapter to listview

    }
    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }



}
