package ru.qwonix.suai.airporter.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.dao.TicketTypeDao;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainController implements Controller, Initializable {

    private TicketTypeDao ticketTypeDao;
    private final ApplicationContext applicationContext;

    @FXML
    private Parent mainPane;
    @FXML
    private Button startButton, authButton;

    @Value("classpath:/views/ticket/ticket-search-view.fxml")
    private Resource ticketSearchView;

    @Value("classpath:/views/user/authorization-view.fxml")
    private Resource authorizationView;

    @Setter
    private Stage stage;

    public MainController(TicketTypeDao ticketTypeDao, ApplicationContext applicationContext) {
        this.ticketTypeDao = ticketTypeDao;
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.setOnMouseClicked(event -> {
            ControllerUtils.changeScene(ticketSearchView, applicationContext, this.stage);

        });

        authButton.setOnMouseClicked(event -> {
            ControllerUtils.changeScene(authorizationView, applicationContext, this.stage);
        });
    }
}
