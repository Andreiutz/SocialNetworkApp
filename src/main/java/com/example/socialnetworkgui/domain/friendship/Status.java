package com.example.socialnetworkgui.domain.friendship;

public enum Status {

    ACCEPTED,
    REJECTED,
    PENDING;


    public static Status fromString(String status) {
        status = status.toLowerCase();
        switch (status) {
            case "accepted" : return ACCEPTED;
            case "pending" : return PENDING;
            default : return REJECTED;
        }
    }

}
