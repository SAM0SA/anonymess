package com.samhith.anonymess;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sam on 12/6/17.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>{

    private List<Message> messageList;
    private FirebaseAuth mAuth;
    private String conversationId;
    private DatabaseReference membersRef;
    private Context context;

    public MessagesAdapter(List<Message> messageList, String conversationId, Context context){
        this.messageList = messageList;
        mAuth = FirebaseAuth.getInstance();
        this.conversationId = conversationId;
        membersRef = FirebaseDatabase.getInstance().getReference().child("members").child(conversationId);
        this.context = context;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        String currentUserUid = mAuth.getCurrentUser().getUid();

        Message message = messageList.get(position);
        String senderUid = message.getUid();

        if(!senderUid.equals(currentUserUid)){
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#EFF0F1"));
        }else holder.relativeLayout.setBackgroundColor(Color.WHITE);

        holder.messageText.setText(message.getMsg());
        membersRef.child(message.getUid()).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getValue(String.class);
                holder.username.setText(username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        membersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String chatProPic = dataSnapshot.child(message.getUid()).child("proPicUrl").getValue(String.class);
                Picasso.with(context).load(chatProPic).placeholder(R.drawable.default_pro_pic).into(holder.propic);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            } 
        });




        StringBuilder time = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(message.getTimestamp()));
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        time.append((hr % 12) == 0 ? 1: hr % 12)
                .append(":")
                .append((calendar.get(Calendar.MINUTE) < 9 ? "0" + calendar.get(Calendar.MINUTE) :
                        calendar.get(Calendar.MINUTE)))
                .append((hr > 12 ? " PM" : "AM"));
        holder.timestamp.setText(time.toString());


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public TextView username;
        public CircleImageView propic;
        public TextView timestamp;
        public RelativeLayout relativeLayout;

        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_single_text);
            username = (TextView) view.findViewById(R.id.message_single_username);
            propic = (CircleImageView) view.findViewById(R.id.message_single_propic);
            timestamp = (TextView) view.findViewById(R.id.message_single_time_text);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.message_single_layout);

        }
    }
}

