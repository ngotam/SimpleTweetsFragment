package com.codepath.apps.mysimpletweets;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tammy.ngo on 12/9/2015.
 */
public class TweetsViewHolder {

    TextView tvBody;
    TextView tvUserName;
    TextView tvFullName;
    ImageView ivProfileImage;

    public TweetsViewHolder(View view) {
        tvBody = (TextView)view.findViewById(R.id.tvBody);
        tvUserName = (TextView)view.findViewById(R.id.tvUserName);
        tvFullName = (TextView)view.findViewById(R.id.tvFullName);
        ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
    }
}