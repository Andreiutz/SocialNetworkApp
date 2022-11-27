package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.HelloApplication;
import com.example.socialnetworkgui.domain.user.User;
import com.example.socialnetworkgui.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    private Service service = Service.getInstance();

    @FXML
    private TextField userNameLogInField;
    @FXML
    private TextField passwordLogInField;

    @FXML
    private void logIn(ActionEvent actionEvent) throws IOException {
        String userName = userNameLogInField.getText();
        String password = passwordLogInField.getText();
        User user = service.findUser(userName);
        if (user != null && user.getPassword().equals(password)) {
            mainAppStartUp(actionEvent, user);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username or password invalid!");
            alert.show();
        }
    }

    private void mainAppStartUp(ActionEvent actionEvent, User user) throws IOException {
        Service.setCurrentLoggedUser(user);
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainapp-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void signUpStartUp(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Sign up!");
        stage.setScene(scene);
        stage.show();
    }

}
