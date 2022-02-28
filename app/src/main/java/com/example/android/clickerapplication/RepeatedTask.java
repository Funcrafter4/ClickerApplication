package com.example.android.clickerapplication;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.TimerTask;

public class RepeatedTask extends TimerTask {
    private FirebaseUser User;
    private DatabaseReference reference;
    private String UserId;
    public int currency;
    public int income;

    @Override
    public void run() {
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        User = FirebaseAuth.getInstance().getCurrentUser();
        UserId = User.getUid();
        getDataFromDataBase(UserId);
        currency = currency + income;
        HashMap hashMap = new HashMap();
        hashMap.put("currency", currency);

        reference.child(UserId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
            }
        });
    }

    private void getDataFromDataBase(String UserId) {
        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currencystr = snapshot.child("currency").getValue().toString();
                currency = Integer.parseInt(currencystr);
                String incomestr = snapshot.child("income").getValue().toString();
                income = Integer.parseInt(incomestr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}