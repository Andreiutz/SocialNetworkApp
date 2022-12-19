package com.example.socialnetworkgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * User-ul trimite un friend request, in db apare ca pending
 * In contul celuilalt user apare friendship-ul ca pending,
 */

/**
 * TODO: Implement friend request validator
 *
 */

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMaxHeight(700);
        stage.setMaxWidth(1000);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        launch();
    }
}