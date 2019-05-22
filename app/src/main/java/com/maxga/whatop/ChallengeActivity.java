package com.maxga.whatop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
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

public class ChallengeActivity extends AppCompatActivity {

    public ListView listView ;
    public ArrayList<String> namechallenge = new ArrayList<String>();
    public ArrayList<String> passchallenge = new ArrayList<String>();
    ArrayList<String> nameAnime = new ArrayList<String>();
    ArrayList<String> LinkOP = new ArrayList<String>();
    public int sizeAinme;
    private WebView web;
    private Button next;
    private Button back;
    private Button reset;
    private Button ans;
    private String nameselect;
    private String nameselectedItem;
    private int indexop = 1;
    private int coutnt;


    public Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        DatabaseReference mdb;
        mdb = FirebaseDatabase.getInstance().getReference().child("challenge");

        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data: dataSnapshot.getChildren()){
                    challenge challengeop = data.getValue(challenge.class);
                    namechallenge.add(challengeop.name);
                    passchallenge.add(challengeop.password);
                }
                //if(nameAnime.isEmpty()){ ans.setText("No Data"); }
                //else {ans.setText(0);}
                createList(namechallenge);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final Intent myIntent = new Intent( this, AddChallengeActivity.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(myIntent);
            }
        });
    }
    public void createList(ArrayList<String> tasklist){
        ListView listView = findViewById(R.id.listch);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,android.R.id.text1,tasklist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);

                // Display the selected item text on TextView
                btn = (Button) findViewById(R.id.name);
                DatabaseReference mdb;
                mdb = FirebaseDatabase.getInstance().getReference().child("challenge").child(selectedItem).child("list");
                mdb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         coutnt = 0;
                         nameAnime.clear();
                         LinkOP.clear();
                         nameAnime.add("");
                         LinkOP.add("");
                        for (DataSnapshot data: dataSnapshot.getChildren()){
                            AnimeOp animeOP = data.getValue(AnimeOp.class);
                            // webView.loadUrl(animeOP.link);

                            nameAnime.add(animeOP.name);
                            LinkOP.add(animeOP.link);
                            web = (WebView) findViewById(R.id.webop) ;
                            web.loadUrl(LinkOP.get(1));
                            ans = (Button) findViewById(R.id.ans) ;
                            ans.setText("Answer");
                            coutnt=coutnt+1;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                nameselect=selectedItem;
                nameselectedItem = selectedItem;
                btn.setText(nameselectedItem+" : 1 is Play!");
            }
        });


    }
    public void Gotoedit(View v) {

             Intent myIntent = new Intent(this, LoginActivity.class);
             myIntent.putExtra("nameselected", nameselectedItem);
             startActivity(myIntent);



    }
    public void GotoHome(View v){
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putExtra("nameselected", nameselectedItem);
        startActivity(myIntent);
    }
    public void next(View v) {
        btn = (Button) findViewById(R.id.name);

        if(indexop<nameAnime.size()-1) {indexop++;
            web = (WebView) findViewById(R.id.webop) ;
            web.loadUrl(LinkOP.get(indexop));
            ans = (Button) findViewById(R.id.ans) ;
            ans.setText("Answer");
        }
        btn.setText(nameselectedItem+" : "+(indexop)+"/"+(nameAnime.size()-1));
    }
    public void back(View v) {
        btn = (Button) findViewById(R.id.name);

        if(indexop>1) {indexop--;
            web = (WebView) findViewById(R.id.webop) ;
            web.loadUrl(LinkOP.get(indexop));
            ans = (Button) findViewById(R.id.ans) ;
            ans.setText("Answer");
        }
        btn.setText(nameselectedItem+" : "+(indexop)+"/"+(nameAnime.size()-1));
    }
    public void reset(View v) {
        web = (WebView) findViewById(R.id.webop) ;
        web.loadUrl(LinkOP.get(indexop));
        ans = (Button) findViewById(R.id.ans) ;
        ans.setText("Answer");
    }
    public void ansop(View v) {

        ans = (Button) findViewById(R.id.ans) ;
        ans.setText(nameAnime.get(indexop));
    }

}
