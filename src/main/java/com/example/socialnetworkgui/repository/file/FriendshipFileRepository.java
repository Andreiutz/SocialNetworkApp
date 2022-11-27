package com.example.socialnetworkgui.repository.file;


import com.example.socialnetworkgui.domain.OrderedStringPair;
import com.example.socialnetworkgui.domain.friendship.Friendship;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.utils.constants.TimeFormatConstants;

import java.time.LocalDateTime;
import java.util.List;

public class FriendshipFileRepository extends AbstractFileRepository<OrderedStringPair, Friendship> {

    public FriendshipFileRepository(String fileName, Validator<Friendship> validator) {
        super(fileName, validator);
    }

    /**
     * Creates a Friendship object from a list of strings
     * Format:
     * 0 -> first id
     * 1 -> second id
     * 2 -> fromDate
     * @param attributes list of Strings
     * @return Friendship
     */
    @Override
    public Friendship extractEntity(List<String> attributes) {
        return new Friendship(
                attributes.get(0),
                attributes.get(1),
                LocalDateTime.parse(attributes.get(2), TimeFormatConstants.DATE_TIME_FORMAT)
        );
    }

    /**
     * Parses a Friendship object into a String
     * @param entity Friendship
     * @return String
     */
    @Override
    protected String createEntityAsString(Friendship entity) {
        return entity.getId().getFirst() + ";"
                +entity.getId().getSecond() + ";"
                +entity.getFriendsFrom().format(TimeFormatConstants.DATE_TIME_FORMAT);
    }
}
