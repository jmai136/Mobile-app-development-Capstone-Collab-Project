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

        // https://www.youtube.com/watch?v=aUFdgLSEl0g
        // test variables
        String[] creators = {"Game Programmers:", "Jude Mai", "Sharri Brascher","Music By: Ethan Hermann", "Art By: Aaron McGuire", "Restart"};

        // setListAdapter to intake stuff
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, creators));
    }

    private void goToUri(Uri uri) {
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    protected void onListItemClick (ListView l, View v, int position, long id) {
        Uri[] uri = {Uri.parse("https://github.com/maict24"), Uri.parse("https://github.com/Shar3019"), Uri.parse("https://www.youtube.com/c/BootsBeats"), Uri.parse("https://marketplace.roll20.net/browse/publisher/1943/willow")};

        switch (position) {
            case 0:
                break;
            case 1:
                goToUri(uri[0]);
                break;
            case 2:
                goToUri(uri[1]);
                break;
            case 3:
                goToUri(uri[2]);
                break;
            case 4:
                goToUri(uri[3]);
                break;
            case 5:
                startActivity(new Intent (Credits.this, Splash.class));
                break;
        }
    }
}