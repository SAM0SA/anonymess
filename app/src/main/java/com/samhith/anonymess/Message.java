package com.samhith.anonymess;

/**
 * Created by sam on 12/3/17.
 */

public class Message {

    public String uid;
    public String msg;
    public Long timestamp;
    public String messageID;

    public Message(){}

    public Message(String uid, String msg, Long timestamp, String messageID) {
        this.uid = uid;
        this.msg = msg;
        this.timestamp = timestamp;
        this.messageID = messageID;
    }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }

    public Long getTimestamp() { return timestamp; }

    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public String getMessageID() { return messageID; }

    public void setMessageID(String messageID) { this.messageID = messageID; }

}
