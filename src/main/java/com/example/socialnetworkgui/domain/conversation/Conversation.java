package com.example.socialnetworkgui.domain.conversation;

import com.example.socialnetworkgui.domain.Entity;

public class Conversation extends Entity<Integer> {
    private final String firstUser;
    private final String secondUser;

    public Conversation(int id_conversaton, String firstUser, String secondUser) {
        super.setId(id_conversaton);
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }

    public String getFirstUser() {
        return firstUser;
    }

    public String getSecondUser() {
        return secondUser;
    }
}
