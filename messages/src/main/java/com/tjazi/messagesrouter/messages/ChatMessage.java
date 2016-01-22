package com.tjazi.messagesrouter.messages;

/**
 * Created by Krzysztof Wasiak on 22/01/2016.
 */
public class ChatMessage {

    /**
     * String representing ID of the sender
     */
    private String senderId;

    /**
     * String representing ID of the receiver
     */
    private String receiverId;

    /**
     * Correlation ID of the message (aka: unique ID, which allows tracking message from source to destination.
     * It could be used for tracking, debugging, performance measuring, etc.
     */
    private String correlationId;

    /**
     * Actual body of the message
     */
    private String messageBody;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
