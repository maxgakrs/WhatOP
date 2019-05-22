package com.maxga.whatop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
     private WebView webview;
     private TextView ans;
     public ArrayList<String> nameAnime = new ArrayList<String>();
     public ArrayList<String> LinkOP = new ArrayList<String>();
     private int n=-1;
     private String[] Name;
     private String[] Link;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference mdb;

        mdb = FirebaseDatabase.getInstance().getReference().child("ZOP");
           //adddata
           /* AnimeOp myTask = new AnimeOp(data1,data2);
            mdb.child(namechild).setValue(myTask);
            //2 lv node
             DatabaseReference mdb;
        mdb = FirebaseDatabase.getInstance().getReference().child("ZOP");
        for(int i =0;i<LinkOP.size();i++){
            if(nameAnime.get(i)!="ZOP"){
            AnimeOp myTask = new AnimeOp(nameAnime.get(i),LinkOP.get(i));
            mdb.child(nameAnime.get(i)).setValue(myTask);}
        }
            */


        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data: dataSnapshot.getChildren()){
                    AnimeOp animeOP = data.getValue(AnimeOp.class);
                    webview.loadUrl(animeOP.link);
                    nameAnime.add(animeOP.name);
                    LinkOP.add(animeOP.link);
                    n++;
                }
                //if(nameAnime.isEmpty()){ ans.setText("No Data"); }
                  //else {ans.setText(0);}

                //createList(nameAnime);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        webview = (WebView) findViewById(R.id.webplay);
        webview.setWebViewClient(new WebViewClient());


        //webview.loadUrl("https://www.youtube.com/watch?v=9Q1rTavZBJo");


        Random rand = new Random();

        // n = rand.nextInt(LinkOP.size());
        //String getLink = LinkOP.get(0);
        //webview.loadUrl(getLink);

    }

    /*void createList(ArrayList<String> tasklist){
        ListView listView = findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,android.R.id.text1,tasklist);
        listView.setAdapter(adapter);
    }*/

   //this method is actually fetching the json string


    public void random(View v){


        Random rand = new Random();
        n = rand.nextInt(LinkOP.size());
        webview.loadUrl(LinkOP.get(n));
        ans = (TextView) findViewById(R.id.Answer);
        ans.setText("Answer");
    }
    public void Ans(View v){
        ans = (TextView) findViewById(R.id.Answer);
        ans.setText(nameAnime.get(n));
    }
    public void Gotolist(View v) {
        Intent myIntent = new Intent(this, ListActivity.class);
        startActivity(myIntent);
    }
    public void Gotochallenge(View v) {
        Intent myIntent = new Intent(this, ChallengeActivity.class);
        startActivity(myIntent);
    }
}
