package com.samhith.anonymess;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sam on 12/5/17.
 */

public class Anonymess extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
