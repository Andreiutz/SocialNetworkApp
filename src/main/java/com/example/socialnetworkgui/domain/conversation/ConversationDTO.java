package com.example.socialnetworkgui.domain.conversation;



public class ConversationDTO {
    private int id;
    private String userName;

    public ConversationDTO(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return userName;
    }
}
