package ru.qwonix.suai.airporter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.JavaFxApplication.StageReadyEvent;

import ru.qwonix.suai.airporter.controller.ControllerUtils;


import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final ApplicationContext applicationContext;
    @Value("classpath:/views/main-view.fxml")
    private Resource mainView;

    private final ControllerUtils controllerUtils;

    public StageInitializer(ApplicationContext applicationContext, ControllerUtils controllerUtils) {
        this.applicationContext = applicationContext;
        this.controllerUtils = controllerUtils;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(mainView.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();

            Stage stage = event.getStage();
            controllerUtils.setStage(stage);

            stage.setScene(new Scene(parent));

            stage.show();

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
