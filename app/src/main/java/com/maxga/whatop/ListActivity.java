package com.maxga.whatop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    public WebView webView;
    public ArrayList<String> nameAnime = new ArrayList<String>();
    public ArrayList<String> LinkOP = new ArrayList<String>();
    public Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        webView = (WebView) findViewById(R.id.webplay);
        webView.setWebViewClient(new WebViewClient());

        DatabaseReference mdb;
        mdb = FirebaseDatabase.getInstance().getReference().child("ZOP");
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data: dataSnapshot.getChildren()){
                    AnimeOp animeOP = data.getValue(AnimeOp.class);
                   // webView.loadUrl(animeOP.link);
                    nameAnime.add(animeOP.name);
                    LinkOP.add(animeOP.link);

                }

                createList(nameAnime);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    void createList(final ArrayList<String> tasklist){
        final ListView listView = findViewById(R.id.listop);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,android.R.id.text1,tasklist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);

                // Display the selected item text on TextView
                btn = (Button) findViewById(R.id.list);
                webView.loadUrl("https://folkertrip.com/anime/"+selectedItem+".mp3");
                btn.setText(selectedItem);
            }
        });

    }
    public void Gotohome(View v) {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
