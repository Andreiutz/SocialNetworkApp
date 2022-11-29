package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.HelloApplication;
import com.example.socialnetworkgui.domain.user.Address;
import com.example.socialnetworkgui.domain.validators.ValidationException;
import com.example.socialnetworkgui.service.Service;
import com.example.socialnetworkgui.service.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class SignUpController {

    private final Service service = Service.getInstance();

    @FXML
    private TextField userNameTF;
    @FXML
    private TextField firstNameTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private TextField emailTF;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField countryTF;
    @FXML
    private TextField countyTF;
    @FXML
    private TextField cityTF;
    @FXML
    private TextField streetTF;
    @FXML
    private TextField streetNumberTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private PasswordField confirmPasswordTF;

    @FXML
    private void logInStartUp(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Log in!");
        stage.setScene(scene);
        stage.show();
    }

    private void startMain(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainapp-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void mainAppStartUp(ActionEvent actionEvent) {
        String userName = userNameTF.getText();
        String firstName = firstNameTF.getText();
        String lastName = lastNameTF.getText();
        String email = emailTF.getText();
        LocalDate birthDate = birthDatePicker.getValue();
        String country = countryTF.getText();
        String county = countyTF.getText();
        String city = cityTF.getText();
        String street = streetTF.getText();
        Long streetNumber;
        try {
            streetNumber = Long.parseLong(streetNumberTF.getText());
        } catch (NumberFormatException exception) {
            streetNumber = -1L;
        }
        String password = passwordTF.getText();
        String confirmPassword = confirmPasswordTF.getText();
        if (password.equals(confirmPassword)) {
            try {
                service.addUser(userName, firstName, lastName, email, birthDate,
                        new Address(country, county, city, street, streetNumber), password);
                Service.setCurrentLoggedUser(service.findUser(userName));
                startMain(actionEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ServiceException | ValidationException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(exception.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Passwords do not match!");
            alert.show();
        }
    }
}
