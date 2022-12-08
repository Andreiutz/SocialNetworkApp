package com.example.socialnetworkgui.domain.friendship;

import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.utils.constants.TimeFormatConstants;

import java.time.LocalDateTime;

public class FriendRequest extends Entity<Integer> {

    private String fromId;
    private String toId;
    private Status status;
    private LocalDateTime timeSent;

    public FriendRequest(String fromId, String toId) {
        this.fromId = fromId;
        this.toId = toId;
        this.status = Status.PENDING;
        timeSent = LocalDateTime.now();
    }

    public FriendRequest(Integer id, String fromId, String toId, Status status, LocalDateTime timeSent) {
        super.setId(id);
        this.fromId = fromId;
        this.toId = toId;
        this.status = status;
        this.timeSent = timeSent;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("To: %s | From: %s | %s", toId, fromId, timeSent.format(TimeFormatConstants.DATE_TIME_FORMAT));
    }
}
