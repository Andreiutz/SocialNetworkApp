package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.domain.conversation.Conversation;
import com.example.socialnetworkgui.domain.conversation.ConversationDTO;
import com.example.socialnetworkgui.domain.conversation.Message;
import com.example.socialnetworkgui.service.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class ConversationsController {
    private Service service = Service.getInstance();

    @FXML
    private ListView<ConversationDTO> conversationsList;
    @FXML
    private Label friendName;
    @FXML
    private ListView<Message> messagesList;

    private List<ConversationDTO> getConversationDTO(List<Conversation> conversationList) {
        List<ConversationDTO> conversationDTOList = new ArrayList<>();
        for (Conversation conversation : conversationList) {
            //We save in the DTO the other user besides the current logged one
            String otherUser = conversation.getFirstUser().equals(Service.getCurrentLoggedUser().getId())
                    ? conversation.getSecondUser() : conversation.getFirstUser();
            conversationDTOList.add(new ConversationDTO(conversation.getId(), otherUser));
        }
        return conversationDTOList;
    }

    public void initialize() {
        List<Conversation> conversationList = (List<Conversation>) service.getConversationsForCurrentUser();
        conversationsList.getItems().addAll(getConversationDTO(conversationList));

    }

    @FXML
    public void updateMessages(MouseEvent mouseEvent) {
        int currentConversationID = conversationsList.getSelectionModel().getSelectedItem().getId();
        Iterable<Message> messagesWithSelectedUser = service.getMessagesFromConversation(currentConversationID);
        for (Message message : messagesWithSelectedUser) {
            System.out.println(message.toString());
        }
    }
}
