package com.example.socialnetworkgui.domain.friendship;


import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.domain.OrderedStringPair;
import com.example.socialnetworkgui.utils.constants.TimeFormatConstants;

import java.time.LocalDateTime;

/**
 * Represents the friendship between 2 users
 */

public class Friendship extends Entity<OrderedStringPair> {

    private LocalDateTime friendsFrom;
    /**
     * Constructor, keeps the IDs sorted alphabetically
     * @param first, String, the first ID
     * @param second, String, the second ID
     */
    public Friendship(String first, String second) {
        super.setId(new OrderedStringPair(first, second));
        friendsFrom = LocalDateTime.now();
    }

    public Friendship(String first, String second, LocalDateTime friendsFrom) {
        this(first, second);
        this.friendsFrom = friendsFrom;
    }


    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }


    @Override
    public String toString() {
        return "Friendship{\n" +
                "\tfirst='" + getId().getFirst() + "'\n" +
                "\tsecond='" + getId().getSecond() + "'\n" +
                "\tfriendsFrom='" + this.friendsFrom.format(TimeFormatConstants.DATE_TIME_FORMAT) + "'\n" +
                '}';
    }


}
