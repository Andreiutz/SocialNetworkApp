package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.HelloApplication;
import com.example.socialnetworkgui.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

    private Service service = Service.getInstance();

    @FXML
    private Label nameLabel;

    @FXML
    public void initialize() {
        nameLabel.setText(Service.getCurrentLoggedUser().getFirstName());
    }

    @FXML
    public void mainAppStartUp(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainapp-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Settings!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void logInStartUp(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Settings!");
        stage.setScene(scene);
        stage.show();
    }
}
