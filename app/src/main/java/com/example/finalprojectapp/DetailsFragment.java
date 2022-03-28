package com.example.finalprojectapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class DetailsFragment extends Fragment {

    private TextView titleTextView;
    private TextView dateTextView;
    private TextView sectionTextView;
    private Button loadUrl;
    private Button saveButton;
    private Button deleteButton;
    private SQLiteDatabase db;
    private long id = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        db = dbHelper.getWritableDatabase();

        titleTextView = v.findViewById(R.id.title);
        dateTextView = v.findViewById(R.id.date);
        loadUrl = v.findViewById(R.id.loadUrl);
        sectionTextView = v.findViewById(R.id.section);
        saveButton = v.findViewById(R.id.saveButton);
        deleteButton = v.findViewById(R.id.deleteButton);

        Bundle b = getArguments();

        id = b.getLong("ID", -1);
        String title = b.getString("TITLE");
        String url = b.getString("URL");
        String section = b.getString("SECTION");
        String date = b.getString("DATE");

        titleTextView.setText(title);
        dateTextView.setText(date);
        sectionTextView.setText(section);

        if (id == -1) { // Record Not stored
            saveButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
        } else { // It is stored
            saveButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
        }

        loadUrl.setOnClickListener(view -> {
            // https://stackoverflow.com/a/2201999
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        saveButton.setOnClickListener(view -> {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.COL_TITLE, title);
            contentValues.put(DatabaseHelper.COL_URL, url);
            contentValues.put(DatabaseHelper.COL_DATE, date);
            contentValues.put(DatabaseHelper.COL_SECTION, section);
            id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues );

            Toast.makeText(getContext(), getString(R.string.article_saved), Toast.LENGTH_SHORT).show();
            saveButton.setVisibility(View.GONE);

        });

        deleteButton.setOnClickListener(view -> {

            db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COL_ID + " = ?", new String[]{Long.toString(id)});

            Toast.makeText(getContext(), getString(R.string.article_deleted), Toast.LENGTH_SHORT).show();
            deleteButton.setVisibility(View.GONE);
        });

        return v;

    }
}