package com.samhith.anonymess;

/**
 * Created by sam on 12/3/17.
 */

public class Member {

    public boolean isMember;
    public String uid;
    public String proPicUrl;
    public String username;

    public Member(){}

    public Member(boolean isMember, String uid, String proPicUrl, String username) {
        this.isMember = isMember;
        this.uid = uid;
        this.proPicUrl = proPicUrl;
        this.username = username;
    }

    public boolean isMember() { return isMember; }

    public void setMember(boolean member) { isMember = member; }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

    public String getProPicUrl() { return proPicUrl; }

    public void setProPicUrl(String proPicUrl) { this.proPicUrl = proPicUrl; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

}
