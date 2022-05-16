package ru.qwonix.suai.airporter.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    private final ControllerUtils controllerUtils;
    @FXML
    private Button startButton, authButton;

    public MainController(ControllerUtils controllerUtils) {
        this.controllerUtils = controllerUtils;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.setOnMouseClicked(event -> {
            controllerUtils.changeScene(View.TICKET_SEARCH);
        });

        authButton.setOnMouseClicked(event -> {
            controllerUtils.openAuthorization();
        });
    }
}
