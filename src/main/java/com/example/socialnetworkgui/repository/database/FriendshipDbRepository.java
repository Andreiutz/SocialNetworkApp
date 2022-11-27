package com.example.socialnetworkgui.repository.database;


import com.example.socialnetworkgui.domain.OrderedStringPair;
import com.example.socialnetworkgui.domain.friendship.Friendship;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.utils.constants.TimeFormatConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class FriendshipDbRepository extends AbstractDbRepository<OrderedStringPair, Friendship> {

    public FriendshipDbRepository(String url, String userName, String password, Validator<Friendship> validator) {
        super(url, userName, password, validator);
    }

    @Override
    protected Friendship extractEntity(ResultSet resultSet) throws SQLException {
        String firstId = resultSet.getString("first_id");
        String secondId = resultSet.getString("second_id");
        LocalDateTime friendsFrom = resultSet.getTimestamp("friends_from").toLocalDateTime();
        return new Friendship(
                firstId, secondId, friendsFrom
        );
    }

    @Override
    protected String getByIdQuery(OrderedStringPair orderedStringPair) {
        return String.format("SELECT * FROM friendships WHERE first_id LIKE '%s' AND second_id LIKE '%s'", orderedStringPair.getFirst(), orderedStringPair.getSecond());
    }

    @Override
    protected String getAllQuery() {
        return "SELECT * FROM friendships";
    }

    @Override
    protected String saveEntityQuery(Friendship entity) {
        return "INSERT INTO friendships(first_id, second_id, friends_from) VALUES " +
                String.format("('%s', '%s', '%s')",
                        entity.getId().getFirst(),
                        entity.getId().getSecond(),
                        entity.getFriendsFrom().format(TimeFormatConstants.SQL_DATE_TIME_FORMAT));
    }

    @Override
    protected String deleteEntityQuery(OrderedStringPair orderedStringPair) {
        return String.format("DELETE FROM friendships WHERE first_id LIKE '%s' AND second_id LIKE '%s'",
                orderedStringPair.getFirst(),
                orderedStringPair.getSecond());
    }

    @Override
    protected String updateEntityQuery(Friendship entity) {
        return String.format("UPDATE friendships SET " +
                "friends_from = '%s' WHERE first_id = '%s' AND second_id = '%s'",
                entity.getFriendsFrom().format(TimeFormatConstants.SQL_DATE_TIME_FORMAT),
                entity.getId().getFirst(),
                entity.getId().getSecond());
    }

}
