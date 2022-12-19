package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.domain.user.User;
import com.example.socialnetworkgui.domain.validators.ValidationException;
import com.example.socialnetworkgui.service.Service;
import com.example.socialnetworkgui.service.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AddFriendController {
    private Service service = Service.getInstance();

    @FXML
    private TextField userIdTF;
    @FXML
    public ListView<User> usersList;

    public void initialize() {
        Iterable<User> allUsers = service.getAllUsers();
        List<User> users = StreamSupport.stream(allUsers.spliterator(), false)
                .filter(x -> !x.getId().equals(Service.getCurrentLoggedUser().getId())).toList();
        usersList.getItems().setAll(users);
        userIdTF.textProperty().addListener(f -> updateSearchList());
    }

    public void updateSearchList() {
        String userName = userIdTF.getText();
        Predicate<User> userPredicate = x -> x.getId().contains(userName);
        Iterable<User> allUsers = service.getAllUsers();
        List<User> users = StreamSupport.stream(allUsers.spliterator(), false)
                .filter(x -> !x.getId().equals(Service.getCurrentLoggedUser().getId()))
                .filter(userPredicate)
                .collect(Collectors.toList());
        usersList.getItems().setAll(users);
    }

    @FXML
    public void addFriend(ActionEvent actionEvent) {
        Alert alert;
        if (usersList.getSelectionModel().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No user selected!");
        } else {
            User user = usersList.getSelectionModel().getSelectedItem();
            try {
                service.addFriendRequest(user.getId());
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Friend request successfully sent!");
            } catch (ServiceException | ValidationException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
            }
            alert.show();
        }
    }
}
