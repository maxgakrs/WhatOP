package com.maxga.whatop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddChallengeActivity extends AppCompatActivity {
    private EditText namech;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent myIntent = new Intent( this, ChallengeActivity.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namech = (EditText) findViewById(R.id.nameedit);
                pass = (EditText) findViewById(R.id.passedit);
                String dataname = namech.getText().toString();
                String datapass = pass.getText().toString();
                DatabaseReference mdb;
                mdb = FirebaseDatabase.getInstance().getReference().child("challenge");
                challenge myTask = new challenge(dataname ,datapass );
                mdb.child(namech.getText().toString()).setValue(myTask);
                startActivity(myIntent);

            }
        });
    }

}
