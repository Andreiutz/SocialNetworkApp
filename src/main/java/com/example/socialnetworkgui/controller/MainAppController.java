package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.HelloApplication;
import com.example.socialnetworkgui.domain.friendship.Friendship;
import com.example.socialnetworkgui.domain.friendship.Status;
import com.example.socialnetworkgui.domain.user.User;
import com.example.socialnetworkgui.domain.validators.ValidationException;
import com.example.socialnetworkgui.service.Service;
import com.example.socialnetworkgui.service.ServiceException;
import com.example.socialnetworkgui.utils.events.FriendShipEntityChangedEvent;
import com.example.socialnetworkgui.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

public class MainAppController implements Observer<FriendShipEntityChangedEvent> {

    private final ObservableList<User> usersModel = FXCollections.observableArrayList();
    private final ObservableList<Status> statuses = FXCollections.observableArrayList(Status.ACCEPTED, Status.PENDING, Status.REJECTED);
    private final Service service = Service.getInstance();

    @FXML
    private TableView<User> usersTableView;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private Label welcomeLabel;
    @FXML
    private ComboBox<Status> friendshipsComboBox;
    @FXML
    private ListView<Friendship> statusFriendshipsList;
    @FXML
    private TextField searchUserId;

    @FXML
    public void initialize() {
        welcomeLabel.setText(String.format("Welcome back, %s!", Service.getCurrentLoggedUser().getFirstName()));
        initFriends();
        initFriendRequests();
        service.addObserver(this);
    }

    private void initFriends() {
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        usersTableView.setItems(usersModel);
        initModel();
    }

    private void initFriendRequests() {
        friendshipsComboBox.setItems(statuses);
    }

    private void initModel() {
        Iterable<User> friendsOfCurrentUser = service.getFriendsOfCurrentUser();
        List<User> users1 = StreamSupport.stream(friendsOfCurrentUser.spliterator(), false).toList();
        usersModel.setAll(users1);
    }

    @FXML
    public void updateFriendshipList(ActionEvent actionEvent) {
        Status status = friendshipsComboBox.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void addFriendRequest(ActionEvent actionEvent) {
        String userName = searchUserId.getText();
        try {
            service.addFriendRequest(userName);
        } catch (ServiceException | ValidationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    public void deleteFriend(ActionEvent actionEvent) {
        Alert alert;
        if (usersTableView.getSelectionModel().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No friend selected");
        } else {
            String userName = usersTableView.getSelectionModel().getSelectedItem().getId();
            try {
                service.removeFriendship(Service.getCurrentLoggedUser().getId(), userName);
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Friend successfully removed!");
            } catch (ServiceException exception) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Something went wrong!");
            }
        }
        alert.show();
    }

    @FXML
    public void settingsStartUp(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("settings-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Settings!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(FriendShipEntityChangedEvent friendShipEntityChangedEvent) {
        initFriends();
        initFriendRequests();
    }

}