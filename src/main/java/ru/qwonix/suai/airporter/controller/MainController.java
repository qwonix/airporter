package ru.qwonix.suai.airporter.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.dao.TicketTypeDao;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    private TicketTypeDao ticketTypeDao;

    @FXML
    private Parent mainPane;
    @FXML
    private Button startButton, authButton;

    @Value("classpath:/views/ticket/ticket-search-view.fxml")
    private Resource ticketSearchView;

    @Value("classpath:/views/user/authorization-view.fxml")
    private Resource authorizationView;

    private final ControllerUtils controllerUtils;

    public MainController(TicketTypeDao ticketTypeDao, ControllerUtils controllerUtils) {
        this.ticketTypeDao = ticketTypeDao;
        this.controllerUtils = controllerUtils;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.setOnMouseClicked(event -> {
            controllerUtils.changeScene(ticketSearchView);

        });

        authButton.setOnMouseClicked(event -> {
            controllerUtils.changeScene(authorizationView);
        });
    }
}
