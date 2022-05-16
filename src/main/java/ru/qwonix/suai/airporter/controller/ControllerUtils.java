package ru.qwonix.suai.airporter.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.entity.Passenger;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Component
public class ControllerUtils {

    @Setter
    @Getter
    private Optional<Passenger> passenger = Optional.empty();

    private final ApplicationContext applicationContext;

    @Value("classpath:/views/main-view.fxml")
    private Resource mainView;

    @Value("classpath:/views/searching/ticket-search-view.fxml")
    private Resource ticketSearchView;

    @Value("classpath:/views/user/authorization-view.fxml")
    private Resource authorizationView;

    @Value("classpath:/views/user/registration-view.fxml")
    private Resource registrationView;

    @Setter
    @Getter
    private Stage mainStage;

    public ControllerUtils(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static String encodePassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("sha-1");
        } catch (NoSuchAlgorithmException ignore) {
        }
        final byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        final StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            final String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void changeScene(View view) {
        changeScene(mainStage, view);
    }

    public void changeScene(Stage stage, View view) {
        try {
            URL url;

            switch (view) {
                case MAIN:
                    url = mainView.getURL();
                    break;
                case TICKET_SEARCH:
                    url = ticketSearchView.getURL();
                    break;
                case REGISTRATION:
                    url = registrationView.getURL();
                    break;
                case AUTHORIZATION:
                    url = authorizationView.getURL();
                    break;
                default:
                    url = mainView.getURL();
            }

            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            Parent parent = fxmlLoader.load();

            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAuthorization() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(authorizationView.getURL());
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            Parent load = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Authorization");
            newStage.setScene(new Scene(load));
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(this.mainStage);

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
