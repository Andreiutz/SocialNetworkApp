package com.example.socialnetworkgui.domain.validators;

import com.example.socialnetworkgui.domain.friendship.FriendRequest;

public class FriendRequestValidator implements Validator<FriendRequest> {
    @Override
    public void validate(FriendRequest entity) throws ValidationException {
        if (entity.getFromId().equals(entity.getToId())) throw new ValidationException("Invalid friend request!");
    }
}
