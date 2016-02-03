package com.bvmiste.vision16;

/**
 * Created by arvind on 1/2/16.
 */
public class Message {
    private String message;
    private long timestamp;

    public Message() {
    }

    public Message(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
