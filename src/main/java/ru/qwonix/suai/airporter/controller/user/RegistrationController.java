package ru.qwonix.suai.airporter.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.controller.ControllerUtils;
import ru.qwonix.suai.airporter.controller.View;
import ru.qwonix.suai.airporter.model.dao.PassengerDao;
import ru.qwonix.suai.airporter.model.entity.Gender;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegistrationController implements Initializable {

    private final ControllerUtils controllerUtils;
    private final PassengerDao passengerDao;

    @FXML
    private TextField usernameField, phoneField, emailField,
            passportField, firstnameField, lastnameField, patronymicField;
    @FXML
    private DatePicker birthdayDatePicker;
    @FXML
    private PasswordField passwordField, passwordRepeatField;

    @FXML
    private Label usernameCaption, passwordCaption, passwordRepeatCaption,
            phoneCaption, emailCaption, passportCaption, nameCaption, birthdayCaption;

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
        String firstnameFieldText = firstnameField.getText();
        String lastname = lastnameField.getText();
        String patronymic = patronymicField.getText();
        LocalDate birthday = birthdayDatePicker.getValue();

        boolean isValid = isValidUsername(username) &
                isValidPassword(password) &
                isValidPasswordRepeat(passwordRepeatField.getText()) &
                isValidPhone(phone) &
                isValidEmail(email) &
                isValidPassport(passport) &
                isValidName(firstnameFieldText) &
                isValidName(lastname) &
                isValidName(patronymic) &
                isValidBirthday(birthday);

        if (isValid) {
            System.out.println("харош добавил в базу");
        } else {
            usernameCaption.setVisible(true);
            passwordCaption.setVisible(true);
            passwordRepeatCaption.setVisible(true);
            phoneCaption.setVisible(true);
            emailCaption.setVisible(true);
            passportCaption.setVisible(true);
            nameCaption.setVisible(true);
            birthdayCaption.setVisible(true);
        }
    }

    @FXML
    private void onSelectedGenderChange(ActionEvent event) {
        String genderMark = (String)
                ((RadioButton) (event.getSource()))
                        .getUserData();
        Gender gender = Gender.fromCode(genderMark.charAt(0));
    }

    private boolean isValidUsername(String username) {
        int minLength = 5;
        int maxLength = 30;
        if (username.length() < minLength) {
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

        usernameCaption.setVisible(false);
        usernameCaption.setText(null);
        return true;
    }

    @FXML
    private void onUsernameChanged(KeyEvent inputMethodEvent) {
        String username = ((TextField) (inputMethodEvent.getSource())).getText();
        isValidUsername(username);
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

        passwordCaption.setVisible(false);
        passwordCaption.setText(null);
        return true;
    }

    @FXML
    private void onPasswordChanged(KeyEvent inputMethodEvent) {
        String password = ((TextField) (inputMethodEvent.getSource())).getText();
        isValidPassword(password);
    }


    private boolean isValidPasswordRepeat(String passwordRepeat) {
        if (!passwordRepeat.equals(passwordField.getText())) {
            passwordRepeatCaption.setText("Пароли не совпадают");
            return false;
        }

        passwordRepeatCaption.setVisible(false);
        passwordRepeatCaption.setText(null);
        return true;
    }

    @FXML
    private void onPasswordRepeatChanged(KeyEvent inputMethodEvent) {
        String passwordRepeat = ((TextField) (inputMethodEvent.getSource())).getText();
        isValidPasswordRepeat(passwordRepeat);
    }

    private boolean isValidPhone(String phone) {
        Matcher phoneMatcher = Pattern.compile("^\\+7 \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$")
                .matcher(phone);

        if (!phoneMatcher.find()) {
            phoneCaption.setText("Формат номера: +7 (ххх) ххх-хх-хх");
            return false;
        }
        phoneCaption.setVisible(false);
        phoneCaption.setText(null);

        return true;
    }

    @FXML
    private void onPhoneChanged(KeyEvent inputMethodEvent) {
        String phone = ((TextField) (inputMethodEvent.getSource())).getText();
        isValidPhone(phone);
    }

    private boolean isValidEmail(String email) {
        Matcher phoneMatcher = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                .matcher(email);
        if (!phoneMatcher.find()) {
            emailCaption.setText("Введите корректный email");
            return false;
        }
        emailCaption.setVisible(false);
        emailCaption.setText(null);

        return true;
    }

    @FXML
    private void onEmailChanged(KeyEvent inputMethodEvent) {
        String email = ((TextField) (inputMethodEvent.getSource())).getText();
        isValidEmail(email);
    }

    private boolean isValidPassport(String passport) {
        Matcher passportMatcher = Pattern.compile("^[0-9]{6} [0-9]{4}$")
                .matcher(passport);
        if (!passportMatcher.find()) {
            passportCaption.setText("Введите корректный номер паспорта");
            return false;
        }
        passportCaption.setVisible(false);
        passportCaption.setText(null);

        return true;
    }

    @FXML
    private void onPassportChanged(KeyEvent inputMethodEvent) {
        String passport = ((TextField) (inputMethodEvent.getSource())).getText();
        isValidPassport(passport);
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

    private boolean isValidBirthday(LocalDate birthday) {
        if (birthday == null) {
            birthdayCaption.setText("Укажите ваш день рождения");
            return false;
        }

        int years = Period.between(birthday, LocalDate.now()).getYears();
        if (years < 18 || years > 150) {
            birthdayCaption.setText("years < 18 || years > 150");
            return false;
        }

        birthdayCaption.setVisible(false);
        birthdayCaption.setText(null);

        return true;
    }

    @FXML
    private void onBirthdayChanged(ActionEvent inputMethodEvent) {
        LocalDate birthday = ((DatePicker) (inputMethodEvent.getSource())).getValue();
        isValidBirthday(birthday);
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
        controllerUtils.changeScene(View.AUTHORIZATION);
    }
}