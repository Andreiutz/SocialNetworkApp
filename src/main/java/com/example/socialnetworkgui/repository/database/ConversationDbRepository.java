package com.example.socialnetworkgui.repository.database;

import com.example.socialnetworkgui.domain.conversation.Conversation;
import com.example.socialnetworkgui.domain.validators.Validator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConversationDbRepository extends AbstractDbRepository<Integer, Conversation> {

    /**
     * Constructor
     *
     * @param url       address of the database
     * @param userName  username for database login
     * @param password  password for database login
     * @param validator entity validato
     */
    public ConversationDbRepository(String url, String userName, String password, Validator<Conversation> validator) {
        super(url, userName, password, validator);
    }

    @Override
    protected Conversation extractEntity(ResultSet resultSet) throws SQLException {
        int id_conversation = resultSet.getInt("id_conversation");
        String firstUser = resultSet.getString("first_user");
        String secondUser = resultSet.getString("second_user");
        return new Conversation(id_conversation, firstUser, secondUser);
    }

    @Override
    protected String getByIdQuery(Integer integer) {
        return String.format("select * from conversations where id_conversation = %d", integer);
    }

    @Override
    protected String getAllQuery() {
        return "select * from conversations";
    }

    @Override
    protected String saveEntityQuery(Conversation entity) {
        return String.format("insert into conversations(first_user, second_user) " +
                "values('%s', '%s')", entity.getFirstUser(), entity.getSecondUser());
    }

    @Override
    protected String deleteEntityQuery(Integer integer) {
        return String.format("delete from conversations where id_conversation = %d", integer);
    }

    //should not use this yet
    @Override
    protected String updateEntityQuery(Conversation entity) {
        return null;
    }
}
