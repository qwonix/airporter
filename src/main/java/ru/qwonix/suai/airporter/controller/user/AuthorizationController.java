package ru.qwonix.suai.airporter.controller.user;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.controller.ControllerUtils;
import ru.qwonix.suai.airporter.controller.View;
import ru.qwonix.suai.airporter.model.dao.PassengerDao;
import ru.qwonix.suai.airporter.model.entity.Passenger;

import java.util.Optional;

@Slf4j
@Component
public class AuthorizationController {

    private final PassengerDao passengerDao;
    private final ControllerUtils controllerUtils;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label usernameCaption, passwordCaption;


    public AuthorizationController(PassengerDao passengerDao, ControllerUtils controllerUtils) {
        this.passengerDao = passengerDao;
        this.controllerUtils = controllerUtils;
    }

    @FXML
    private void onForgetPasswordButton_Clicked(MouseEvent mouseEvent) {
    }


    @FXML
    private void onUsernameChanged(KeyEvent keyEvent) {
        // todo: запрет на ввод некоторых символов
    }

    @FXML
    private void onLoginButton_Clicked(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!passengerDao.existsByUsername(username)) {
            usernameCaption.setText("Пользователя с таким логином нет");
            return;
        } else {
            usernameCaption.setText(null);
        }

        if (passengerDao.isCorrectPasswordByUsername(username, password)) {
            Passenger passenger = passengerDao.findByUsername(username);
            log.info("passenger '" + passenger.getUsername() + "' logged on successfully");

            controllerUtils.setPassenger(Optional.of(passenger));

            // close authorization stage
            ((Stage) ((Node) (mouseEvent.getSource())).getScene().getWindow()).close();

        } else {
            passwordCaption.setText("Неверный пароль");
        }
    }

    @FXML
    private void onRegistrationButton_Clicked(MouseEvent mouseEvent) {
        Stage authorizationStage = (Stage) ((Node) (mouseEvent.getSource())).getScene().getWindow();
        controllerUtils.changeScene(authorizationStage, View.REGISTRATION);
    }
}
