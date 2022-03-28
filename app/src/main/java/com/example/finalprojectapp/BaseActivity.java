package com.example.finalprojectapp;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

/**
 * Common activity to handle toolbar and navigation view
 */
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * This function performs common logic to set toolbar, drawer layout and navigation view
     * @param headerTitleString header for the navigation bar
     */
    void doLoadingStuff(String headerTitleString) {
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView headerTitle = navigationView.getHeaderView(0).findViewById(R.id.headerTitle);
        headerTitle.setText(headerTitleString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu_nav_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onNavigationItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawerItemAction1:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finishAffinity();
                break;
            case R.id.help:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.help)
                        .setMessage(R.string.help_description)
                        .show();
                break;
            case R.id.menuSavedArticles:
                Intent i1 = new Intent(this, SavedNewsListActivity.class);
                startActivity(i1);
                break;
        }
        return false;
    }
}
