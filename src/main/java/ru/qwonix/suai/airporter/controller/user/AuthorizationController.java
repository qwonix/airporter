package ru.qwonix.suai.airporter.controller.user;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.controller.Controller;
import ru.qwonix.suai.airporter.controller.ControllerUtils;

@Component
public class AuthorizationController implements Controller {

    private final ApplicationContext applicationContext;

    @Value("classpath:/views/user/registration-view.fxml")
    private Resource registrationView;

    @Setter
    private Stage stage;

    public AuthorizationController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @FXML
    public void onForgetPasswordButton_Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void onRegistrationButton_Clicked(MouseEvent mouseEvent) {
        ControllerUtils.changeScene(registrationView, applicationContext, this.stage);
    }
}
