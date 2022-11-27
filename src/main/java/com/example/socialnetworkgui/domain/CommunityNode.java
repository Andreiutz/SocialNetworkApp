package com.example.socialnetworkgui.domain;


import com.example.socialnetworkgui.domain.user.User;

import java.util.HashMap;
import java.util.Map;

/**
 * The Node of the community graph, represents a User and all of their friends
 */
public class CommunityNode {
    private User user;
    private Map<String, User> friends;

    /**
     * Constructor
     * @param user, The User of the node
     */
    public CommunityNode(User user) {
        this.user = user;
        friends = new HashMap<>();
    }

    public User getUser() {
        return user;
    }

    /**
     * Adds a friend to the friends list of the Node
     * @param userFriend, another User, friend with this.User
     */
    public void addFriend(User userFriend) {
        friends.put(userFriend.getId(), userFriend);
    }

    /**
     * @return The list of Users who are friends with this.User
     */
    public Iterable<User> getFriends() {
        return friends.values();
    }
}
