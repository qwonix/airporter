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
import ru.qwonix.suai.airporter.сontroller.MainController;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    @Value("classpath:/views/main-view.fxml")
    private Resource resource;

    private final ApplicationContext applicationContext;

    public StageInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
            fxmlLoader.setControllerFactory(param -> applicationContext.getBean(param));
            Parent parent = fxmlLoader.load();

            Stage stage = event.getStage();
            stage.setScene(new Scene(parent, 800, 600));

            ((MainController) fxmlLoader.getController()).setStage(stage);

            stage.show();

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
