package com.ua.hotels.models;

public class OutcommingMessage {
    private String outMsg;

    public OutcommingMessage() {
    }

    public OutcommingMessage(String outMsg) {
        this.outMsg = outMsg;
    }

    public String getOutMsg() {
        return outMsg;
    }

    public void setOutMsg(String outMsg) {
        this.outMsg = outMsg;
    }

    @Override
    public String toString() {
        return "OutcommingMessage{" +
                "outMsg='" + outMsg + '\'' +
                '}';
    }
}
