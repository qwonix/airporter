package ru.qwonix.suai.airporter.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.controller.Controller;
import ru.qwonix.suai.airporter.model.entity.Gender;

@Component
public class RegistrationController implements Controller {
    @FXML
    public BorderPane borderPane;

    @Setter
    private Stage stage;

    @FXML
    public void onSelectedGenderChange(ActionEvent event) {
        String genderMark = (String)
                ((RadioButton) (event.getSource()))
                        .getUserData();
        Gender gender = Gender.fromCode(genderMark.charAt(0));
        System.out.println(gender);
    }
}
