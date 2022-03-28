package com.example.finalprojectapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class MyListAdapter extends BaseAdapter {

    private ArrayList<NewsArticle> newsList;

    public MyListAdapter(ArrayList<NewsArticle> newsList) {
        this.newsList = newsList;
    }

    public int getCount() {
        return newsList.size();
    }

    public Object getItem(int position) {
        return newsList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View old, ViewGroup parent) {
        View newView = old;
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (newView == null) {
            newView = inflater.inflate(R.layout.news_article, parent, false);
        }

        TextView newsTitle = newView.findViewById(R.id.newsTitle);
        TextView newsDate = newView.findViewById(R.id.newsDate);
        NewsArticle newsArticle = (NewsArticle) getItem(position);
        newsTitle.setText(newsArticle.getWebTitle());
        newsDate.setText(newsArticle.getWebPublicationDate());

        return newView;
    }
}