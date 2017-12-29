package com.samhith.anonymess;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sam on 12/5/17.
 */

public class ChatsViewHolder extends RecyclerView.ViewHolder {

    View view;

    public ChatsViewHolder(View itemView) {
        super(itemView);

        view = itemView;
    }

    public void setTitle(String title){
        TextView chatTitle = (TextView) view.findViewById(R.id.chat_single_text);
        chatTitle.setText(title);
    }

    public void setLastMsg(String lastMsg){
        TextView lastMessage = (TextView) view.findViewById(R.id.chat_single_LastMsg);
        lastMessage.setText("Last message: " + lastMsg);
    }

    public void setProPic(Context context, String url){
        if(url != null && url.length() > 0){
            CircleImageView propic = (CircleImageView) view.findViewById(R.id.chat_single_image);
            Picasso.with(context).load(url).placeholder(R.drawable.default_pro_pic).into(propic);
        }
    }

    public void setTime(Date date){
        StringBuilder time = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        time.append((hr % 12) == 0 ? 1: hr % 12)
                .append(":")
                .append((calendar.get(Calendar.MINUTE) < 9 ? "0"+calendar.get(Calendar.MINUTE) :
                        calendar.get(Calendar.MINUTE)))
                .append((hr > 12 ? " PM" : "AM"));
        TextView timeText = (TextView) view.findViewById(R.id.chat_single_time);
        timeText.setText(time.toString());
    }

}
