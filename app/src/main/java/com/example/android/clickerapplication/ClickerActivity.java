package com.example.android.clickerapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


public class ClickerActivity extends AppCompatActivity {

    private FirebaseUser User;
    private DatabaseReference reference;
    private String UserId;
    public int currency;
    public int n = 1;
    public int onclickbonus;

    private TextView logout, Clicker, currencyTextView, nameTextView, shop;

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
        Clicker = (Button) findViewById(R.id.Clicker);
        Clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currency = currency + n + onclickbonus;
                currencyTextView.setText(String.valueOf(currency));
                HashMap hashMap = new HashMap();
                hashMap.put("currency", currency);

                reference.child(UserId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(Clicker.this,"Updated",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        shop =(Button) findViewById(R.id.shop);

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClickerActivity.this, ShopActivity.class));
            }
        });
        logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ClickerActivity.this, MainActivity.class));
            }
        });


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
}