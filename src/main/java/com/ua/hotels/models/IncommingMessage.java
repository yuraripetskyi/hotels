package com.ua.hotels.models;

public class IncommingMessage {
    private String msg;

    public IncommingMessage() {
    }

    public IncommingMessage(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "IncommingMessage{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
