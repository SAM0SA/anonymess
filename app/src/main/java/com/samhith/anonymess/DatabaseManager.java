package com.samhith.anonymess;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by sam on 12/4/17.
 */

public class DatabaseManager {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    DatabaseReference rootRef;
    Random r;

    public DatabaseManager(FirebaseAuth mAuth, FirebaseDatabase database){
        this.mAuth = mAuth;
        this.database = database;
        rootRef = database.getReference();
        rootRef.keepSynced(true);
        r = new Random();
    }

    public void writeNewChat(String msg){
        String lastMessage = "Be the first to send a message!";
        String title = msg;
        String conversationId = rootRef.child("chats").push().getKey();
        Chat chat = new Chat(title, lastMessage, new Date().getTime(),
                conversationId, mAuth.getCurrentUser().getUid());
        rootRef.child("chats").child(conversationId).setValue(chat);
        writeNewMessage(msg, conversationId);
        addMemberToChat(conversationId);
    }

    public void writeNewMessage(String msg, String conversationId) {
        String uid = mAuth.getCurrentUser().getUid();
        String messageId = rootRef.child("messages").child(conversationId).push().getKey();
        Message message = new Message(uid, msg, new Date().getTime(),messageId);
        rootRef.child("messages").child(conversationId).child(messageId).setValue(message);
        updateLastMessage(msg, conversationId);
    }

    private void updateLastMessage(String lastMessage, String conversationId){
        Map<String, Object> lastMsg = new HashMap<>();
        lastMsg.put("lastMessage", lastMessage);
        rootRef.child("chats").child(conversationId).updateChildren(lastMsg);
    }

    public void addMemberToChat(final String conversationId){
        final String uid = mAuth.getCurrentUser().getUid();
        final Member member = new Member(true, uid,"","");
        rootRef.child("members").child(conversationId).child(uid).setValue(member);
    }
}
