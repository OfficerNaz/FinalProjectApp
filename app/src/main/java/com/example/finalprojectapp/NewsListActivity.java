package com.example.finalprojectapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsListActivity extends BaseActivity {

    private ArrayList<NewsArticle> newsList = new ArrayList<>();
    private MyListAdapter adapter;
    private ListView listView;
    private CircularProgressIndicator progressLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        doLoadingStuff("NewsListActivity");

        listView = findViewById(R.id.listView);
        progressLoading = findViewById(R.id.progressLoading);

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

        String searchText = getIntent().getStringExtra("SEARCH_TEXT");

        GuardianNewsApi req = new GuardianNewsApi();
        req.execute("https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=" + searchText);
    }

    /**
     * AsyncTask which finds articles from internet and stores it into the list
     */
    public class GuardianNewsApi extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                //create a URL object of what server to contact:
                URL url = new URL(strings[0]);
                Bitmap myBitmap = null;

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                JSONObject obj = new JSONObject(content.toString());
                JSONObject jsonResponse = obj.getJSONObject("response");
                JSONArray resultsJsonArray = jsonResponse.getJSONArray("results");

                for (int i = 0; i < resultsJsonArray.length(); i++) {
                    JSONObject jsonObject = resultsJsonArray.getJSONObject(i);
                    String webTitle = jsonObject.getString("webTitle");
                    String webUrl = jsonObject.getString("webUrl");
                    String webPublicationDate = jsonObject.getString("webPublicationDate");
                    String sectionName = jsonObject.getString("sectionName");

                    NewsArticle newsArticle = new NewsArticle(webTitle, webUrl, webPublicationDate, sectionName);
                    newsList.add(newsArticle);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            adapter.notifyDataSetChanged();
            progressLoading.setVisibility(View.GONE);

        }
    }
}