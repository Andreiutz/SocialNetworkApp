package com.example.socialnetworkgui.utils.events;


import com.example.socialnetworkgui.domain.friendship.Friendship;

public class FriendShipEntityChangedEvent implements Event {
    private final ChangeEventType type;
    private final Friendship data;
    private Friendship oldData;

    public FriendShipEntityChangedEvent(ChangeEventType type, Friendship
            data) {
        this.type = type;
        this.data = data;
    }

    public FriendShipEntityChangedEvent(ChangeEventType type, Friendship data, Friendship oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Friendship getData() {
        return data;
    }

    public Friendship getOldData() {
        return oldData;
    }
}