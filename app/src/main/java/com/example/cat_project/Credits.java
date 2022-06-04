package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Credits extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] creators = {"Game Programmers:", "Jude Mai", "Sharri Brascher","Music By:", "Boots Beats", "Art By:", "Aaron McGuire"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, creators));
    }
    protected void onListItemClick (ListView l, View v, int position, long id) {
        switch (position) {
            case 0:
                break;
            case 1:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/maict24")));
                break;
            case 2:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Shar3019")));
                break;
            case 3:
                break;
            case 4:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/c/BootsBeats")));
                break;
            case 5:
                break;
            case 6:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://marketplace.roll20.net/browse/publisher/1943/willow")));
                break;
        }
    }
}