package com.example.android.clickerapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Timer;


public class ClickerActivity extends AppCompatActivity {
    Timer timer;
    private FirebaseUser User;
    private DatabaseReference reference;
    private String UserId;
    public int currency = 0;
    public int n = 1;
    public int onclickbonus;

    private TextView logout, Clicker, currencyTextView, nameTextView;
    private ImageView shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);
        currencyTextView =(TextView) findViewById(R.id.currency);
        nameTextView =(TextView) findViewById(R.id.name);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        User = FirebaseAuth.getInstance().getCurrentUser();
        UserId = User.getUid();
        getDataFromDataBase(UserId);

        Clicker = (Button) findViewById(R.id.clicker);
        Clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String currencystr = snapshot.child("currency").getValue().toString();
                        currency = Integer.parseInt(currencystr) + n + onclickbonus;
                        currencyTextView.setText(currencystr);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                HashMap hashMap = new HashMap();
                hashMap.put("currency", currency);

                reference.child(UserId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(ClickerActivity.this,"Updated",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        shop = (ImageView) findViewById(R.id.shop);

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClickerActivity.this, ShopActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            }
        });
        logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //timer.cancel();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ClickerActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
        //setTimer();

    }

    private void getDataFromDataBase(String UserId) {
        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currencystr = snapshot.child("currency").getValue().toString();
                currencyTextView.setText(currencystr);
                currency = Integer.parseInt(currencystr);
                String onclickbonusstr = snapshot.child("onclickbonus").getValue().toString();
                onclickbonus = Integer.parseInt(onclickbonusstr);
                String namestr = snapshot.child("name").getValue().toString();
                nameTextView.setText(namestr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setTimer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new RepeatedTask(),500, 10000);
    }
}