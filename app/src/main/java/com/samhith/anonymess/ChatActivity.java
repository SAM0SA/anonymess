package com.samhith.anonymess;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private String conversationId;
    private String creatorUid;
    private String creatorUserName;
    private Toolbar toolbar;
    private CircleImageView propic;
    private TextView usernameTextView;
    private ImageButton cameraBtn;
    private ImageButton insertPhotoBtn;
    private Button sendBtn;
    private EditText textbox;
    private RecyclerView messagesList;
    private CircleImageView creatorProPic;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseManager databaseManager= new DatabaseManager(mAuth, database );
    private DatabaseReference rootRef = database.getReference();

    private final List<Message> messages = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = (Toolbar) findViewById(R.id.chat_activity_app_bar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("");


        conversationId = getIntent().getStringExtra("conversationId");
        creatorUid = getIntent().getStringExtra("creatorUid");

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View chatActionBarView = inflater.inflate(R.layout.chat_costom_bar, null);

        actionBar.setCustomView(chatActionBarView);

        propic = (CircleImageView) findViewById(R.id.chat_costom_bar_img);
        usernameTextView = (TextView) findViewById(R.id.chat_costom_username);

        cameraBtn = (ImageButton) findViewById(R.id.chat_activity_camera_btn);
        insertPhotoBtn = (ImageButton) findViewById(R.id.chat_activity_insert_photo_btn);
        sendBtn = (Button) findViewById(R.id.chat_activity_send_btn);
        textbox = (EditText) findViewById(R.id.chat_activity_message_view);
        creatorProPic = (CircleImageView) findViewById(R.id.chat_costom_bar_img);

        adapter = new MessagesAdapter(messages, conversationId, ChatActivity.this);

        messagesList = (RecyclerView) findViewById(R.id.chat_activity_messages_list);
        linearLayoutManager = new LinearLayoutManager(this);

        messagesList.setHasFixedSize(true);
        messagesList.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                this,linearLayoutManager.getOrientation());
        messagesList.addItemDecoration(itemDecoration);
        messagesList.setAdapter(adapter);
        
        retrieveMessages();

        rootRef.child("members").child(conversationId).child(creatorUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usernameTextView.setText(dataSnapshot.child("username").getValue(String.class));
                String creatorProPicUrl = dataSnapshot.child("proPicUrl").getValue(String.class);
                Picasso.with(ChatActivity.this).load(creatorProPicUrl).placeholder(R.drawable.default_pro_pic).into(creatorProPic);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sendBtn.setOnClickListener((e) -> {
            sendMessage();
        });


    }

    private void retrieveMessages() {
        rootRef.child("messages").child(conversationId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                messages.add(message);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage() {
        String message = textbox.getText().toString();
        if(!TextUtils.isEmpty(message)){
            databaseManager.writeNewMessage(message, conversationId);
            textbox.setText("");
        }
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textbox.getWindowToken(), 0);
        messagesList.scrollToPosition(messages.size()-1);
    }
}
