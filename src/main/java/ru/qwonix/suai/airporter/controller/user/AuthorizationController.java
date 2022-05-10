package ru.qwonix.suai.airporter.controller.user;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.controller.ControllerUtils;
import ru.qwonix.suai.airporter.controller.View;

@Component
public class AuthorizationController {

    private final ControllerUtils controllerUtils;


    public AuthorizationController(ControllerUtils controllerUtils) {
        this.controllerUtils = controllerUtils;
    }

    @FXML
    public void onForgetPasswordButton_Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void onRegistrationButton_Clicked(MouseEvent mouseEvent) {
        controllerUtils.changeScene(View.REGISTRATION);
    }
}
