package com.samhith.anonymess;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewChatActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    private Window window;
    private Button createNewButton;
    private EditText messageBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_chat);

        window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        databaseManager = new DatabaseManager(mAuth, FirebaseDatabase.getInstance());

        messageBox = (EditText) findViewById(R.id.create_new_textbox);

        createNewButton = (Button) findViewById(R.id.create_new_activity_button);
        createNewButton.setOnClickListener((v) -> {
            String message = messageBox.getText().toString();


                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                if(message.length() == 0 || message.equals("")){
                    alertBuilder.setTitle("Please Check Forms")
                                .setMessage("Message box is empty, please try again")
                                .setCancelable(false)
                                .setPositiveButton(
                                        "OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                    alertBuilder.show();
                }else if (message. length() > 200) {
                    alertBuilder.setTitle("Please Check Forms")
                            .setMessage("Message must be less than 200 charaters")
                            .setCancelable(false)
                            .setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                    alertBuilder.show();
                }else {
                    databaseManager.writeNewChat(message);
                    Intent homeActivityIntent = new Intent(CreateNewChatActivity.this, HomeActivity.class);
                    startActivity(homeActivityIntent);
                    finish();
                }

        });
    }
}
