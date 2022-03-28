package com.example.finalprojectapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class EmptyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_activity);
        doLoadingStuff("EmptyActivity");

        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle b = getIntent().getExtras();
        detailsFragment.setArguments(b);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, detailsFragment)
                .commit();
    }
}