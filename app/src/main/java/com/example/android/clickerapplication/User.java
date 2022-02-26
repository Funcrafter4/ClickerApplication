package com.example.android.clickerapplication;

public class User {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getOnclickbonus() {
        return onclickbonus;
    }

    public void setOnclickbonus(int onclickbonus) {
        this.onclickbonus = onclickbonus;
    }

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
