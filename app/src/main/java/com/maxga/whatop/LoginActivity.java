package com.maxga.whatop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    public TextView namee ;
    public EditText passwordtx;
    private TextView log;
    private String passED;
    public ArrayList<String> namechallenge = new ArrayList<String>();
    public ArrayList<String> passchallenge = new ArrayList<String>();
    private String Dataget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle bundle = getIntent().getExtras();
        Dataget = bundle.getString("nameselected");
        namee = (TextView) findViewById(R.id.name);
        namee.setText(" "+Dataget);

    }
    public void  onclickOk(View v){
        passwordtx = (EditText) findViewById(R.id.passdx);

    log = (TextView) findViewById(R.id.log);
    DatabaseReference mdb;

        mdb = FirebaseDatabase.getInstance().getReference().child("challenge");
        final Intent myIntent = new Intent(this, EditActivity.class);
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String passss = passwordtx.getText().toString();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    challenge challengeop = data.getValue(challenge.class);
                    if(Dataget.equals(challengeop.name) && passss.equals(challengeop.password)){
                        myIntent.putExtra("nameselected", Dataget);
                        startActivity(myIntent);
                        log.setText("");
                    }
                    else{
                        log.setText("                                                 Password Wrong!                                               ");
                    }


                }
                //if(nameAnime.isEmpty()){ ans.setText("No Data"); }
                //else {ans.setText(0);}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
