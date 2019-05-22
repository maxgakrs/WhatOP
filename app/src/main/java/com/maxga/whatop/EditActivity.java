package com.maxga.whatop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.maxga.whatop.R.color.common_google_signin_btn_text_light;

public class EditActivity extends AppCompatActivity {

    public ArrayList<String> nameAnime = new ArrayList<String>();
    public ArrayList<String> LinkOP = new ArrayList<String>();
    public ArrayList<String> nameAnimeL = new ArrayList<String>();
    public ArrayList<String> LinkOPL = new ArrayList<String>();
    private EditText nameedit;
    private EditText passedit;
    private String nameED;
    private String passED;
    private Button logch;
    private Button addCh;
    private Button delCh;
    private Button listCh;
    private String Dataget;
    public WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        DatabaseReference mdb;
        Bundle bundle = getIntent().getExtras();
        Dataget = bundle.getString("nameselected");
        mdb = FirebaseDatabase.getInstance().getReference().child("ZOP");

        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    AnimeOp animeOp = data.getValue(AnimeOp.class);
                    nameAnime.add(animeOp.name);
                    LinkOP.add(animeOp.link);
                }
                //if(nameAnime.isEmpty()){ ans.setText("No Data"); }
                //else {ans.setText(0);}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        mdb = FirebaseDatabase.getInstance().getReference().child("challenge").child(Dataget).child("list");
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    AnimeOp animeOp = data.getValue(AnimeOp.class);
                    nameAnimeL.add(animeOp.name);
                    LinkOPL.add(animeOp.link);
                }
                //if(nameAnime.isEmpty()){ ans.setText("No Data"); }
                //else {ans.setText(0);}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
    public void Gotoadd(View v) {
        logch = (Button)findViewById(R.id.log);
        logch.setText("Add OP in Challenge");
        AddList(nameAnime);

    }
    public void GotoDel(View v){
        logch = (Button)findViewById(R.id.log);
        logch.setText("Delete OP in Challenge");
        DelList(nameAnimeL);

    }
    public void GotoList(View v){
        logch = (Button)findViewById(R.id.log);
        logch.setText("All OP in Challenge");
        List(nameAnimeL);
    }

    public void GotoChallenge(View v){
        Intent myIntent = new Intent(this, ChallengeActivity.class);
        startActivity(myIntent);
    }
    void AddList(final ArrayList<String> tasklist){
        final ListView listView = findViewById(R.id.listop);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,android.R.id.text1,tasklist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                DatabaseReference mdb;
                mdb = FirebaseDatabase.getInstance().getReference().child("challenge").child(Dataget);
                AnimeOp myTask = new AnimeOp(selectedItem ,"https://folkertrip.com/anime/"+selectedItem+".mp3" );
                mdb.child("list").child(selectedItem).setValue(myTask);
                // Display the selected item text on TextView
                //btn = (Button) findViewById(R.id.list);
                //webView.loadUrl("https://folkertrip.com/anime/"+selectedItem+".mp3");
                // btn.setText(selectedItem);
            }
        });

    }
    void DelList(final ArrayList<String> tasklist){
        final ListView listView = findViewById(R.id.listop);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,android.R.id.text1,tasklist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                DatabaseReference mdb;
                mdb = FirebaseDatabase.getInstance().getReference().child("challenge").child(Dataget);
                //AnimeOp myTask = new AnimeOp(selectedItem ,"https://folkertrip.com/anime/"+selectedItem+".mp3" );
                //mdb.child("list").child(selectedItem).setValue(myTask);
                mdb.child("list").child(selectedItem).removeValue();
                // Display the selected item text on TextView
                //btn = (Button) findViewById(R.id.list);
                //webView.loadUrl("https://folkertrip.com/anime/"+selectedItem+".mp3");
                // btn.setText(selectedItem);
            }
        });

    }
    void List(final ArrayList<String> tasklist){
        final ListView listView = findViewById(R.id.listop);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,android.R.id.text1,tasklist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                web = (WebView) findViewById(R.id.weblist) ;
                String selectedItem = (String) parent.getItemAtPosition(position);
                 web.loadUrl("https://folkertrip.com/anime/"+selectedItem+".mp3");
                //AnimeOp myTask = new AnimeOp(selectedItem ,"https://folkertrip.com/anime/"+selectedItem+".mp3" );
                //mdb.child("list").child(selectedItem).setValue(myTask);

                // Display the selected item text on TextView
                //btn = (Button) findViewById(R.id.list);
                //webView.loadUrl("https://folkertrip.com/anime/"+selectedItem+".mp3");
                // btn.setText(selectedItem);
            }
        });

    }


}
