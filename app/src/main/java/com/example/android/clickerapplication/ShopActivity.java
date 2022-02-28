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

public class ShopActivity extends AppCompatActivity {

    private FirebaseUser User;
    private DatabaseReference reference;
    private String UserId;
    public int onclickbonus, income, currency;

    private TextView Upgrade1, Upgrade2, Upgrade3, Upgrade4, currencyTextView, incomeTextView, onclickbonusTextView;
    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        currencyTextView =(TextView) findViewById(R.id.currency);
        incomeTextView=(TextView) findViewById(R.id.income);
        onclickbonusTextView=(TextView) findViewById(R.id.onclickbonus);

        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        User = FirebaseAuth.getInstance().getCurrentUser();
        UserId = User.getUid();
        getDataFromDataBase(UserId);

        Back = (ImageView) findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, ClickerActivity.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
        Upgrade1 = (Button) findViewById(R.id.updarade1);

        Upgrade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currency >= 10){
                    if((currency - 10) > 0){
                        currency = currency - 10;
                        currencyTextView.setText(String.valueOf(currency));
                        onclickbonus = ++onclickbonus;
                        onclickbonusTextView.setText(String.valueOf(onclickbonus));
                        HashMap hashMap = new HashMap();
                        hashMap.put("onclickbonus", onclickbonus);
                        hashMap.put("currency", currency);
                        reference.child(UserId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(ShopActivity.this, "Upgraded", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
        Upgrade2 = (Button) findViewById(R.id.updarade2);

        Upgrade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currency >= 10){
                    if((currency - 10) > 0){
                        currency = currency - 10;
                        currencyTextView.setText(String.valueOf(currency));
                        income = ++income;
                        incomeTextView.setText(String.valueOf(income));
                        HashMap hashMap = new HashMap();
                        hashMap.put("income", income);
                        hashMap.put("currency", currency);
                        reference.child(UserId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(ShopActivity.this, "Upgraded", Toast.LENGTH_LONG).show();
                            }
                        });
                    }}
            }
        });
        Upgrade3 = (Button) findViewById(R.id.updarade3);

        Upgrade3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currency >= 100){
                    if((currency - 100) > 0){
                        currency = currency - 100;
                        currencyTextView.setText(String.valueOf(currency));
                        income = income + 10;
                        incomeTextView.setText(String.valueOf(income));
                        HashMap hashMap = new HashMap();
                        hashMap.put("income", income);
                        hashMap.put("currency", currency);
                        reference.child(UserId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(ShopActivity.this, "Upgraded", Toast.LENGTH_LONG).show();
                            }
                        });
                    }}
            }
        });
        Upgrade4 = (Button) findViewById(R.id.updarade4);

        Upgrade4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currency >= 500){
                    if((currency - 500) > 0){
                        currency = currency - 500;
                        currencyTextView.setText(String.valueOf(currency));
                        income = income + 25;
                        incomeTextView.setText(String.valueOf(income));
                        HashMap hashMap = new HashMap();
                        hashMap.put("income", income);
                        hashMap.put("currency", currency);
                        reference.child(UserId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(ShopActivity.this, "Upgraded", Toast.LENGTH_LONG).show();
                            }
                        });
                    }}
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
                onclickbonusTextView.setText(onclickbonusstr);
                String incomestr = snapshot.child("income").getValue().toString();
                income = Integer.parseInt(incomestr);
                incomeTextView.setText(incomestr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}