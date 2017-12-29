package com.samhith.anonymess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 2000;
    private static final String TAG = "MAIN_ACTIVITY";

    private TextView logoText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.statusbarColorWhite));

        logoText = (TextView) findViewById(R.id.logoText);
        Typeface logoFont = Typeface.createFromAsset(getAssets(),"fonts/logoFont.TTF");
        logoText.setTypeface(logoFont);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null) goToHomeActivity();
        else authenticate();
    }

    private void authenticate() {
        Map<String,String> userDetails = retrieveLoginInfo();
        if(userDetails != null) logIn(userDetails);
        else createAccount();
    }

    private Map<String, String> retrieveLoginInfo() {
        SharedPreferences userDetails = getSharedPreferences("USER_DETAILS",MODE_PRIVATE);
        String accountStatus = userDetails.getString("account_status",null);
        if(accountStatus != null){
            HashMap<String, String> loginInfo = new HashMap<>();
            loginInfo.put("email", userDetails.getString("email", ""));
            loginInfo.put("password", userDetails.getString("password", ""));
            return loginInfo;
        }
        return null;
    }

    private void createAccount() {
        //logoText.setText("Account Is being Created");
        final String email = FirebaseInstanceId.getInstance().getId() + "@anonymess.com";
        final String password = PasswordGenerator.getInstance().generatePassword(15);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        storeUserDetails(email, password, "Active");
                        //logoText.setText("Account was created");
                        logIn(retrieveLoginInfo());

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void storeUserDetails(String email, String password, String accountStatus){
        SharedPreferences userDetails = getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetails.edit();
        editor.clear();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("account_status", accountStatus);
        editor.apply();
    }

    private void logIn(Map<String, String> userDetails) {
        mAuth.signInWithEmailAndPassword(userDetails.get("email"),userDetails.get("password"))
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        Log.d(TAG, "signInWithEmail:success");
                        goToHomeActivity();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToHomeActivity(){
        new Handler().postDelayed(() -> {
            Intent homeActivityIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(homeActivityIntent);
            finish();
        }, SPLASH_TIME_OUT);
    }
}
