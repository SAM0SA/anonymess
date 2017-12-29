package com.samhith.anonymess;

/**
 * Created by sam on 12/3/17.
 */

public class Chat {

    public String lastMessage;
    public Long timestamp;
    public String conversationId;
    public String creatorUID;
    public String title;

    public Chat(){

    }

    public Chat(String title, String lastMessage, Long timestamp, String conversationId, String creatorUID) {
        this.title = title;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.conversationId = conversationId;
        this.creatorUID = creatorUID;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getLastMessage() { return lastMessage; }

    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }

    public Long getTimestamp() { return timestamp; }

    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public String getConversationId() { return conversationId; }

    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getCreatorUID() { return creatorUID; }

    public void setCreatorUID(String creatorUID) { this.creatorUID = creatorUID; }


}
