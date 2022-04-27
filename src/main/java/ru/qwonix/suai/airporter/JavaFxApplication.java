package ru.qwonix.suai.airporter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import ru.qwonix.suai.airporter.views.MainController;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        applicationContext = new SpringApplicationBuilder()
                .sources(AirporterApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
//        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
//        Parent root = fxWeaver.loadView(MainController.class);
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();

        applicationContext.publishEvent(new StageReadyEvent(stage));

    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage)getSource();
        }
    }
}
