package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.utils.constants.TimeFormatConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDateTime;

public class FriendRequestObjectController {

    private String to;
    private String from;
    private LocalDateTime localDateTime;

    @FXML
    private Label fromLabel;
    @FXML
    private Label toLabel;
    @FXML
    private Label dateLabel;

    public FriendRequestObjectController(String to, String from, LocalDateTime localDateTime) {
        this.to = to;
        this.from = from;
        this.localDateTime = localDateTime;
    }

    public void initialize() {
        fromLabel.setText(from);
        toLabel.setText(to);
        dateLabel.setText(localDateTime.format(TimeFormatConstants.DATE_TIME_FORMAT));
    }
}
