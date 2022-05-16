package ru.qwonix.suai.airporter.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.controller.ControllerUtils;
import ru.qwonix.suai.airporter.controller.View;
import ru.qwonix.suai.airporter.model.dao.PassengerDao;
import ru.qwonix.suai.airporter.model.entity.Gender;
import ru.qwonix.suai.airporter.model.entity.Passenger;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class RegistrationController implements Initializable {

    private final ControllerUtils controllerUtils;
    private final PassengerDao passengerDao;

    private Gender gender = Gender.MALE;

    @FXML
    private TextField usernameField, phoneField, emailField,
            passportField, firstnameField, lastnameField, patronymicField;
    @FXML
    private DatePicker birthdateDatePicker;
    @FXML
    private PasswordField passwordField, passwordRepeatField;

    @FXML
    private Label usernameCaption, passwordCaption, passwordRepeatCaption,
            phoneCaption, emailCaption, passportCaption, nameCaption, birthdateCaption;

    public RegistrationController(ControllerUtils controllerUtils, PassengerDao passengerDao) {
        this.controllerUtils = controllerUtils;
        this.passengerDao = passengerDao;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onRegistrationButton_Clicked(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String passport = passportField.getText();
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String patronymic = patronymicField.getText();
        LocalDate birthdate = birthdateDatePicker.getValue();

        boolean isValid = isValidUsername(username) &
                isValidPassword(password) &
                isValidPasswordRepeat(passwordRepeatField.getText()) &
                isValidPhone(phone) &
                isValidEmail(email) &
                isValidPassport(passport) &
                isValidName(firstname) &
                isValidName(lastname) &
                isValidName(patronymic) &
                isValidBirthdate(birthdate);


        if (isValid) {
            Passenger passenger = new Passenger();
            passenger.setUsername(username);
            passenger.setPassword(password);
            passenger.setPhone(phone);
            passenger.setEmail(email);
            passenger.setPassport(passport);
            passenger.setFirstName(firstname);
            passenger.setLastName(lastname);
            passenger.setPatronymic(patronymic);
            passenger.setBirthDate(birthdate);
            passenger.setGender(gender);

            passengerDao.save(passenger);
            log.info("new passenger {}, username: {}", passenger.getId(), passenger.getUsername());
        } else {
            usernameCaption.setVisible(true);
            passwordCaption.setVisible(true);
            passwordRepeatCaption.setVisible(true);
            phoneCaption.setVisible(true);
            emailCaption.setVisible(true);
            passportCaption.setVisible(true);
            nameCaption.setVisible(true);
            birthdateCaption.setVisible(true);
        }
    }

    @FXML
    private void onSelectedGenderChange(ActionEvent event) {
        String genderMark = (String)
                ((RadioButton) (event.getSource()))
                        .getUserData();
        gender = Gender.fromCode(genderMark.charAt(0));
    }

    private boolean isValidUsername(String username) {
        int minLength = 5;
        int maxLength = 30;

        if (passengerDao.existsPassengerByUsername(username)) {
            usernameCaption.setText("Логин занят");
            return false;
        } else if (username.length() < minLength) {
            usernameCaption.setText("Длина меньше " + minLength);
            return false;
        } else if (username.length() > maxLength) {
            usernameCaption.setText("Длина больше " + maxLength);
            return false;
        }
        Matcher usernameMatcher = Pattern.compile("^[a-zA-Z0-9_]+$")
                .matcher(username);
        if (!usernameMatcher.find()) {
            usernameCaption.setText("Разрешён только английский алфавит, цифры и символ _ " + minLength);
            return false;
        }
        return true;
    }

    @FXML
    private void onUsernameChanged(KeyEvent inputMethodEvent) {
        String username = ((TextField) (inputMethodEvent.getSource())).getText();
        if (isValidUsername(username)) {
            usernameCaption.setVisible(false);
            usernameCaption.setText(null);
        }
    }

    private boolean isValidPassword(String password) {
        int minLength = 8;
        int maxLength = 50;
        if (password.length() < minLength) {
            passwordCaption.setText("Длина меньше " + minLength);
            return false;
        } else if (password.length() > maxLength) {
            passwordCaption.setText("Длина больше " + maxLength);
            return false;
        }
        Matcher passwordMatcher = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,50}$")
                .matcher(password);
        if (!passwordMatcher.find()) {
            passwordCaption.setText("Минимум восемь символов, по крайней мере, одна буква и одна цифра");
            return false;
        }

        return true;
    }

    @FXML
    private void onPasswordChanged(KeyEvent inputMethodEvent) {
        String password = ((TextField) (inputMethodEvent.getSource())).getText();
        if (isValidPassword(password)) {
            passwordCaption.setVisible(false);
            passwordCaption.setText(null);
        }
    }


    private boolean isValidPasswordRepeat(String passwordRepeat) {
        if (!passwordRepeat.equals(passwordField.getText())) {
            passwordRepeatCaption.setText("Пароли не совпадают");
            return false;
        }

        return true;
    }

    @FXML
    private void onPasswordRepeatChanged(KeyEvent inputMethodEvent) {
        String passwordRepeat = ((TextField) (inputMethodEvent.getSource())).getText();
        if (isValidPasswordRepeat(passwordRepeat)) {
            passwordRepeatCaption.setVisible(false);
            passwordRepeatCaption.setText(null);
        }
    }

    private boolean isValidPhone(String phone) {
        Matcher phoneMatcher = Pattern.compile("^\\+7 \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$")
                .matcher(phone);

        if (!phoneMatcher.find()) {
            phoneCaption.setText("Формат номера: +7 (ххх) ххх-хх-хх");
            return false;
        }
        else if (passengerDao.existsPassengerByPhone(phone)) {
            phoneCaption.setText("Номер телефона занят");
            return false;
        }

        return true;
    }

    @FXML
    private void onPhoneChanged(KeyEvent inputMethodEvent) {
        String phone = ((TextField) (inputMethodEvent.getSource())).getText();
        if (isValidPhone(phone)) {
            phoneCaption.setVisible(false);
            phoneCaption.setText(null);
        }
    }

    private boolean isValidEmail(String email) {
        Matcher phoneMatcher = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                .matcher(email);
        if (!phoneMatcher.find()) {
            emailCaption.setText("Введите корректный email");
            return false;
        } else if (passengerDao.existsPassengerByEmail(email)) {
            emailCaption.setText("Почта занята");
            return false;
        }

        return true;
    }

    @FXML
    private void onEmailChanged(KeyEvent inputMethodEvent) {
        String email = ((TextField) (inputMethodEvent.getSource())).getText();
        if (isValidEmail(email)) {
            emailCaption.setVisible(false);
            emailCaption.setText(null);
        }
    }

    private boolean isValidPassport(String passport) {
        Matcher passportMatcher = Pattern.compile("^[0-9]{6} [0-9]{4}$")
                .matcher(passport);
        if (!passportMatcher.find()) {
            passportCaption.setText("Введите корректный номер паспорта");
            return false;
        }
        else if (passengerDao.existsPassengerByPassport(passport)) {
            passportCaption.setText("Паспорт уже используется");
            return false;
        }

        return true;
    }

    @FXML
    private void onPassportChanged(KeyEvent inputMethodEvent) {
        String passport = ((TextField) (inputMethodEvent.getSource())).getText();
        if (isValidPassport(passport)) {
            passportCaption.setVisible(false);
            passportCaption.setText(null);
        }
    }

    private boolean isValidName(String name) {
        Matcher nameMatcher = Pattern.compile("^[а-яА-ЯёЁ\\-]{2,}$")
                .matcher(name);
        if (!nameMatcher.find()) {
            nameCaption.setText("Введите полное ФИО");
            return false;
        }
        nameCaption.setVisible(false);
        nameCaption.setText(null);

        return true;
    }

    @FXML
    private void onFirstNameChanged(KeyEvent inputMethodEvent) {
        String firstName = ((TextField) (inputMethodEvent.getSource())).getText();
        isValidName(firstName);
    }

    @FXML
    private void onLastNameChanged(KeyEvent inputMethodEvent) {
        String lastName = ((TextField) (inputMethodEvent.getSource())).getText();
        isValidName(lastName);
    }

    @FXML
    private void onPatronymicChanged(KeyEvent inputMethodEvent) {
        String patronymic = ((TextField) (inputMethodEvent.getSource())).getText();
        isValidName(patronymic);
    }

    private boolean isValidBirthdate(LocalDate birthdate) {
        if (birthdate == null) {
            birthdateCaption.setText("Укажите ваш день рождения");
            return false;
        }

        int years = Period.between(birthdate, LocalDate.now()).getYears();
        if (years < 18 || years > 150) {
            birthdateCaption.setText("Вы должны быть совершеннолетним");
            return false;
        }

        return true;
    }

    @FXML
    private void onBirthdayChanged(ActionEvent inputMethodEvent) {
        LocalDate birthdate = ((DatePicker) (inputMethodEvent.getSource())).getValue();
        if (isValidBirthdate(birthdate)) {
            birthdateCaption.setVisible(false);
            birthdateCaption.setText(null);
        }
    }

    @FXML
    private void onPasswordLabelClicked(MouseEvent mouseEvent) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(passwordField.getText());
        clipboard.setContent(content);
    }

    @FXML
    private void onAuthorizationButton_Clicked(MouseEvent mouseEvent) {
        Stage authorizationStage = (Stage) ((Node) (mouseEvent.getSource())).getScene().getWindow();
        controllerUtils.changeScene(authorizationStage, View.AUTHORIZATION);
    }
}