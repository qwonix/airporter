package ru.qwonix.suai.airporter.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.dao.TicketTypeDao;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    private TicketTypeDao ticketTypeDao;
    @FXML public FlowPane mainFlowPane;
    @FXML public Button startButton;

    @Setter
    private Stage stage;

    public MainController(TicketTypeDao ticketTypeDao) {
        this.ticketTypeDao = ticketTypeDao;
            }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.setOnMouseClicked(event -> {
            System.out.println("++++");
            ticketTypeDao.findAll().forEach(System.out::println);
        });
    }
}
