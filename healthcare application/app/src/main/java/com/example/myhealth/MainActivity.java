package com.example.myhealth;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private TextView msgTxt;
    private TextView heartbeat;
    private TextView sugar;
    private TextView oxygen;




    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
     private DatabaseReference mRootReference=firebaseDatabase.getReference();
     private DatabaseReference mChildReference = mRootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heartbeat=(TextView)findViewById(R.id.heartbeat);
        msgTxt=(TextView)findViewById(R.id.msgTxt);
        sugar=(TextView)findViewById(R.id.sugar);
        oxygen=(TextView)findViewById(R.id.oxygen);




        Intent intent=getIntent();
        String m=intent.getStringExtra("keyname").toString();
        mChildReference = mRootReference.child(m);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mChildReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                 String bp = dataSnapshot.child("bp").getValue().toString();
                String he = dataSnapshot.child("heartbeat").getValue().toString();
                String su = dataSnapshot.child("glucose").getValue().toString();
                String ox = dataSnapshot.child("oxygen").getValue().toString();



                heartbeat.setText(he);
                msgTxt.setText(bp);
                sugar.setText(su);
                oxygen.setText(ox);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}