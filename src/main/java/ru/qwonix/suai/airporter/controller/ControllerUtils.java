package ru.qwonix.suai.airporter.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class ControllerUtils {
    public static void changeScene(Resource authorizationView, ApplicationContext applicationContext, Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(authorizationView.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();

            stage.setScene(new Scene(parent));
            ((Controller) fxmlLoader.getController()).setStage(stage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
