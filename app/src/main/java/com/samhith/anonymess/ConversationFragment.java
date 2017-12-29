package com.samhith.anonymess;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends Fragment {

    private View mainView;
    private RecyclerView chatsList;
    private FloatingActionButton fabBtn;
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference().child("chats");
    public ConversationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_conversation, container, false);
        chatsList = (RecyclerView) mainView.findViewById(R.id.chats_list);
        chatsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        chatsList.setLayoutManager(linearLayoutManager);
        fabBtn = (FloatingActionButton) mainView.findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(e -> {
            Intent newChatIntent = new Intent(getActivity(), CreateNewChatActivity.class);
            startActivity(newChatIntent);
        });
        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                getContext(),linearLayoutManager.getOrientation());
//        itemDecoration.setDrawable();
        chatsList.addItemDecoration(itemDecoration);
        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Chat, ChatsViewHolder> adapter = new FirebaseRecyclerAdapter<Chat, ChatsViewHolder>(
                Chat.class,
                R.layout.chat_single_layout,
                ChatsViewHolder.class,
                chatsRef
        ) {
            @Override
            protected void populateViewHolder(ChatsViewHolder viewHolder, Chat model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setLastMsg(model.getLastMessage());
                viewHolder.setTime(new Date(model.getTimestamp()));
                rootRef.child("members").child(model.getConversationId()).child(model.getCreatorUID())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String url = dataSnapshot.child("proPicUrl").getValue(String.class);
//                                Log.w("URL VALUE", url);
                                viewHolder.setProPic(getContext(), url);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                viewHolder.view.setOnClickListener((e) -> {
                    Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                    chatIntent.putExtra("conversationId", model.getConversationId());
                    chatIntent.putExtra("creatorUid",model.getCreatorUID());
                    startActivity(chatIntent);
                });
            }
        };

        chatsList.setAdapter(adapter);
    }

}

