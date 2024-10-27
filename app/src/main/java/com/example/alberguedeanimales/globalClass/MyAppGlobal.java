package com.example.alberguedeanimales.globalClass;

import android.app.Application;

public class MyAppGlobal extends Application {
    private String userDataGmail;

    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializa tu dato global si es necesario
        userDataGmail = "";
    }

    public String getUserData() {
        return userDataGmail;
    }

    public void setUserData(String userData) {
        this.userDataGmail = userData;
    }
}