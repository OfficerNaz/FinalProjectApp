package com.example.finalprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends BaseActivity {

    private EditText editTextSearch;
    private Button searchButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doLoadingStuff("MainActivity");

        sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);

        editTextSearch = findViewById(R.id.editTextSearch);
        searchButton = findViewById(R.id.searchButton);

        String previousSearchTerm = sharedPreferences.getString("SEARCH_TEXT", "");
        editTextSearch.setText(previousSearchTerm);

        searchButton.setOnClickListener(view -> {
            String searchText = editTextSearch.getText().toString();

            // Checking if search field is empty or not
            if (searchText.isEmpty()) {
                Snackbar.make(view, "Please enter some text in Search", Snackbar.LENGTH_SHORT).show();
            } else {

                // Storing into the shared preference
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("SEARCH_TEXT", searchText);
                editor.commit();

                // Opening new activity to perform AsyncTask
                Intent i = new Intent(this, NewsListActivity.class);
                i.putExtra("SEARCH_TEXT", searchText);
                startActivity(i);
            }
        });
    }
}