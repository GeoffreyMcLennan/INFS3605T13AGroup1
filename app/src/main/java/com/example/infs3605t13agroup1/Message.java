package com.example.infs3605t13agroup1;

public class Message {

    String message;
    String senderId;
    long timestamp;
    String currentTime;

    boolean isReceived;

    public Message(String message, String currentTime, long timestamp, boolean isReceived){
        this.message = message;
        this.senderId = senderId;
        this.currentTime = currentTime;
        this.timestamp = timestamp;
        this.isReceived = isReceived;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getSenderId(){
        return senderId;
    }
    public void setSenderId(String senderId){
        this.senderId = senderId;
    }
    public String getCurrentTime(){
        return currentTime;
    }
    public void setCurrentTime(String currentTime){
        this.currentTime = currentTime;
    }
    public long getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }
    public boolean getIsReceived(boolean isReceived){
        return isReceived;
    }
}

