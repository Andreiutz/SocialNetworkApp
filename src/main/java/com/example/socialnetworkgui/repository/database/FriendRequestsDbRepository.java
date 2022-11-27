package com.example.socialnetworkgui.repository.database;

import com.example.socialnetworkgui.domain.friendship.FriendRequest;
import com.example.socialnetworkgui.domain.friendship.Status;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.utils.constants.TimeFormatConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class FriendRequestsDbRepository extends AbstractDbRepository<Integer, FriendRequest> {

    /**
     * Constructor
     *
     * @param url       address of the database
     * @param userName  username for database login
     * @param password  password for database login
     * @param validator entity validato
     */
    public FriendRequestsDbRepository(String url, String userName, String password, Validator<FriendRequest> validator) {
        super(url, userName, password, validator);
    }

    @Override
    protected FriendRequest extractEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id_fr");
        String fromId = resultSet.getString("sent_from");
        String toId = resultSet.getString("sent_to");
        Status status = Status.fromString(resultSet.getString("status"));
        LocalDateTime timeSent = resultSet.getTimestamp("time_sent").toLocalDateTime();
        return new FriendRequest(
                id, fromId, toId, status, timeSent
        );
    }

    @Override
    protected String getByIdQuery(Integer id) {
        return String.format("SELECT * FROM friendrequests WHERE id_fr = %d", id);
    }

    @Override
    protected String getAllQuery() {
        return "SELECT * FROM friendrequests";
    }

    @Override
    protected String saveEntityQuery(FriendRequest entity) {
        return String.format("INSERT INTO friendrequests(sent_from, sent_to, status, time_sent) VALUES " +
                "('%s', '%s', '%s', '%s')",
                entity.getFromId(),
                entity.getToId(),
                entity.getStatus().toString().toLowerCase(),
                entity.getTimeSent().format(TimeFormatConstants.SQL_DATE_TIME_FORMAT));
    }

    @Override
    protected String deleteEntityQuery(Integer integer) {
        return String.format("DELETE FROM friendrequests WHERE id_fr = %d", integer);
    }

    @Override
    protected String updateEntityQuery(FriendRequest entity) {
        return String.format("UPDATE friendrequests SET " +
                "status = '%s' WHERE id_fr = %d",
                entity.getStatus().toString().toLowerCase(),
                entity.getId());
    }
}
