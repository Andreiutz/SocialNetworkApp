package com.example.socialnetworkgui.repository.database;

import com.example.socialnetworkgui.domain.conversation.Message;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.utils.constants.TimeFormatConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MessageDbRepository extends AbstractDbRepository<Integer, Message> {
    /**
     * Constructor
     *
     * @param url       address of the database
     * @param userName  username for database login
     * @param password  password for database login
     * @param validator entity validato
     */
    public MessageDbRepository(String url, String userName, String password, Validator<Message> validator) {
        super(url, userName, password, validator);
    }

    @Override
    protected Message extractEntity(ResultSet resultSet) throws SQLException {
        int idMessage = resultSet.getInt("id_message");
        int idConversation = resultSet.getInt("id_conversation");
        String sentFrom = resultSet.getString("sent_from");
        String sentTo = resultSet.getString("sent_to");
        String text = resultSet.getString("text");
        LocalDateTime timeSent = resultSet.getTimestamp("time_sent").toLocalDateTime();
        return new Message(
                idMessage,
                idConversation,
                sentFrom,
                sentTo,
                text,
                timeSent
        );
    }

    @Override
    protected String getByIdQuery(Integer integer) {
        return String.format("select * from messages where id_message = %d", integer);
    }

    @Override
    protected String getAllQuery() {
        return "select * from messages";
    }

    @Override
    protected String saveEntityQuery(Message entity) {
        return String.format("insert into messages(id_conversation, sent_from, sent_to, text, time_sent) " +
                "values(%d, '%s', '%s', '%s', '%s')",
                entity.getId_conversation(),
                entity.getSentFrom(),
                entity.getSentTo(),
                entity.getText(),
                entity.getTimeSent().format(TimeFormatConstants.SQL_DATE_TIME_FORMAT));
    }

    @Override
    protected String deleteEntityQuery(Integer integer) {
        return String.format("delete from messages where id_message = %d", integer);
    }

    @Override
    protected String updateEntityQuery(Message entity) {
        return String.format("update messages set text = '%s' where id_message = %d", entity.getText(), entity.getId());
    }
}
