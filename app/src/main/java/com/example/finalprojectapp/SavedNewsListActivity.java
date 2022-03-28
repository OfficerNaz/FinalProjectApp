package com.example.finalprojectapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SavedNewsListActivity extends BaseActivity {

    private ArrayList<NewsArticle> newsList = new ArrayList<>();
    private MyListAdapter adapter;
    private TextView noSavedArticles;
    private ListView listView;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news_list);
        doLoadingStuff("SavedNewsListActivity");

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        listView = findViewById(R.id.listView);
        noSavedArticles = findViewById(R.id.noSavedArticles);

        adapter = new MyListAdapter(newsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            NewsArticle na = newsList.get(position);

            Bundle b = new Bundle();
            b.putLong("ID", na.getId());
            b.putString("TITLE", na.getWebTitle());
            b.putString("URL", na.getWebUrl());
            b.putString("SECTION", na.getSectionName());
            b.putString("DATE", na.getWebPublicationDate());

            Intent intent = new Intent(this, EmptyActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadDataFromDatabase();
        adapter.notifyDataSetChanged();

        if (!newsList.isEmpty()) {
            noSavedArticles.setVisibility(View.GONE);
        } else {
            noSavedArticles.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Reads stored articles from SQLite database and stores them into the list
     */
    private void loadDataFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        String[] columns = {
                DatabaseHelper.COL_ID,
                DatabaseHelper.COL_TITLE,
                DatabaseHelper.COL_URL,
                DatabaseHelper.COL_DATE,
                DatabaseHelper.COL_SECTION
        };
        Cursor cursor = db.query(false, DatabaseHelper.TABLE_NAME, columns, null, null, null,
                null, null, null);

        int indexId = cursor.getColumnIndex(DatabaseHelper.COL_ID);
        int indexTitle = cursor.getColumnIndex(DatabaseHelper.COL_TITLE);
        int indexUrl = cursor.getColumnIndex(DatabaseHelper.COL_URL);
        int indexDate = cursor.getColumnIndex(DatabaseHelper.COL_DATE);
        int indexSection = cursor.getColumnIndex(DatabaseHelper.COL_SECTION);

        newsList.clear();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(indexId);
            String title = cursor.getString(indexTitle);
            String url = cursor.getString(indexUrl);
            String date = cursor.getString(indexDate);
            String section = cursor.getString(indexSection);

            newsList.add(new NewsArticle(id, title, url, date, section));

        }
    }
}