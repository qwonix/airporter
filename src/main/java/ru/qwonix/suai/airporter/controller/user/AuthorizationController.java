package ru.qwonix.suai.airporter.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.controller.ControllerUtils;
import ru.qwonix.suai.airporter.controller.View;
import ru.qwonix.suai.airporter.model.dao.PassengerDao;

@Slf4j
@Component
public class AuthorizationController {

    private final PassengerDao passengerDao;
    private final ControllerUtils controllerUtils;
    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameCaption;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label passwordCaption;


    public AuthorizationController(PassengerDao passengerDao, ControllerUtils controllerUtils) {
        this.passengerDao = passengerDao;
        this.controllerUtils = controllerUtils;
    }

    @FXML
    public void onForgetPasswordButton_Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void onRegistrationButton_Clicked(MouseEvent mouseEvent) {
        controllerUtils.changeScene(View.REGISTRATION);
    }

    public void onPasswordChanged(KeyEvent keyEvent) {

    }

    public void onUsernameChanged(KeyEvent keyEvent) {
        if (passengerDao.existsByUsername(usernameField.getText())) {
            usernameCaption.setText(null);
            return;
        }
    }

    public void onLoginButton_Clicked(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!passengerDao.existsByUsername(username)) {
            usernameCaption.setText("Пользователя с таким логином нет");
            return;
        }
        if (!passengerDao.isCorrectPasswordByUsername(username, password)) {
            passwordCaption.setText("Неверный пароль");
            return;
        }
        log.info("user '" + username + "' logged on successfully");
        controllerUtils.changeScene(View.TICKET_SEARCH);
    }
}
