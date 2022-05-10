package ru.qwonix.suai.airporter.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ControllerUtils {

    private final ApplicationContext applicationContext;

    @Setter
    private Stage stage;

    public ControllerUtils(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void changeScene(Resource authorizationView) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(authorizationView.getURL());
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            Parent parent = fxmlLoader.load();

            stage.setScene(new Scene(parent));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
