package ru.qwonix.suai.airporter;

import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.JavaFxApplication.StageReadyEvent;
import ru.qwonix.suai.airporter.controller.ControllerUtils;
import ru.qwonix.suai.airporter.controller.View;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final ControllerUtils controllerUtils;

    public StageInitializer(ControllerUtils controllerUtils) {
        this.controllerUtils = controllerUtils;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();

        controllerUtils.setStage(stage);
        controllerUtils.changeScene(View.MAIN);

        stage.show();
    }
}
