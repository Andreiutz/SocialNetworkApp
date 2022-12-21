package com.example.socialnetworkgui.domain.conversation;

import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.utils.constants.TimeFormatConstants;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message extends Entity<Integer> {
    private int id_conversation;
    private String sentFrom;
    private String sentTo;
    private String text;
    private LocalDateTime timeSent;

    public Message(int id_message, int id_conversation, String sentFrom, String sentTo, String text, LocalDateTime timeSent) {
        super.setId(id_message);
        this.id_conversation = id_conversation;
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
        this.text = text;
        this.timeSent = timeSent;
    }

    public int getId_conversation() {
        return id_conversation;
    }

    public void setId_conversation(int id_conversation) {
        this.id_conversation = id_conversation;
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }

    @Override
    public String toString() {
        return String.format("""
                From: %s | To: %s
                \t%s
                \ttime sent: %s""", sentFrom, sentTo, text, timeSent.format(TimeFormatConstants.DATE_TIME_FORMAT));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Message message = (Message) o;
        return id_conversation == message.id_conversation && sentFrom.equals(message.sentFrom) && sentTo.equals(message.sentTo) && text.equals(message.text) && timeSent.equals(message.timeSent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id_conversation, sentFrom, sentTo, text, timeSent);
    }
}
