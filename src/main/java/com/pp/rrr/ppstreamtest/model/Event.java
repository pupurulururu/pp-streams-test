package com.pp.rrr.ppstreamtest.model;

public class Event {

    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isLoggined(){
        return uid == null ? false : true;
    }
}
