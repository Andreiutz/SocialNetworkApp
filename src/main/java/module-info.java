module com.example.socialnetworkgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.socialnetworkgui to javafx.fxml, java.base;
    opens com.example.socialnetworkgui.domain to javafx.fxml, javafx.base;
    opens com.example.socialnetworkgui.domain.user to javafx.fxml, javafx.base;
    opens com.example.socialnetworkgui.controller to javafx.fxml;


    exports com.example.socialnetworkgui;
    exports com.example.socialnetworkgui.service;
    exports com.example.socialnetworkgui.controller;
}