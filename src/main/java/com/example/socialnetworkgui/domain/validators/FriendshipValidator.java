package com.example.socialnetworkgui.domain.validators;


import com.example.socialnetworkgui.domain.friendship.Friendship;

/**
 * Friendship Validator, used for validating a Friendship Object
 */
public class FriendshipValidator implements Validator<Friendship> {

    /**
     * Validates an Friendship Object
     * @param entity, Friendship
     * @throws ValidationException, if entity is not valid
     */
    @Override
    public void validate(Friendship entity) throws ValidationException {
        String errorMessage = "";
        String firstId = entity.getId().getFirst();
        String secondId = entity.getId().getSecond();
        if (firstId == null || secondId == null || firstId.equals(secondId)) {
            errorMessage += "Invalid friendship!\n";
        }
        if (errorMessage.length() > 0) {
            throw new ValidationException(errorMessage);
        }
    }
}
