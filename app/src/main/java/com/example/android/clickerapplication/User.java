package com.example.android.clickerapplication;

public class User {

    public String name;

    public String email;

    public int currency;

    public int income;

    public int onclickbonus;


    public  User(String name, String email, int currency, int income, int onclickbonus){
        this.name = name;
        this.email = email;
        this.currency = currency;
        this.income = income;
        this.onclickbonus = onclickbonus;
    }
}
