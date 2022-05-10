package ru.qwonix.suai.airporter.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class ControllerUtils {

    private final ApplicationContext applicationContext;

    @Value("classpath:/views/main-view.fxml")
    private Resource mainView;

    @Value("classpath:/views/ticket/ticket-search-view.fxml")
    private Resource ticketSearchView;

    @Value("classpath:/views/user/authorization-view.fxml")
    private Resource authorizationView;

    @Value("classpath:/views/user/registration-view.fxml")
    private Resource registrationView;

    @Setter
    private Stage stage;

    public ControllerUtils(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void changeScene(View view) {
        try {
            URL url;

            switch (view) {
                case MAIN:
                    url = mainView.getURL();
                    break;
                case TICKET_SEARCH:
                    url = ticketSearchView.getURL();
                    break;
                case REGISTRATION:
                    url = registrationView.getURL();
                    break;
                case AUTHORIZATION:
                    url = authorizationView.getURL();
                    break;
                default:
                    url = mainView.getURL();
            }

            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            Parent parent = fxmlLoader.load();

            stage.setScene(new Scene(parent));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
