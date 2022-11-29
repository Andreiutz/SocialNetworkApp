package com.example.socialnetworkgui.utils.events;


import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.domain.friendship.Friendship;

public class EntityChangedEvent implements Event {
    private final ChangeEventType type;
    private final Entity data;
    private Entity oldData;

    public EntityChangedEvent(ChangeEventType type, Entity
            data) {
        this.type = type;
        this.data = data;
    }

    public EntityChangedEvent(ChangeEventType type, Friendship data, Friendship oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Entity getData() {
        return data;
    }

    public Entity getOldData() {
        return oldData;
    }
}