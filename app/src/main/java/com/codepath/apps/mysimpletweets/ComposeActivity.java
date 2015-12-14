package com.codepath.apps.mysimpletweets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.util.Log;

public class ComposeActivity extends AppCompatActivity {

    EditText etInputItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayShowTitleEnabled(false);
        etInputItem = (EditText) findViewById(R.id.etInput);
        etInputItem.setSelection(etInputItem.getText().length());

    }

    public void onTweet(View v) {

        String itemText = etInputItem.getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("ListView.data", itemText);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
