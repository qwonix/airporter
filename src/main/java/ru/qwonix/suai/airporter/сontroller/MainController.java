package ru.qwonix.suai.airporter.сontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.dao.TicketTypeDao;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    private TicketTypeDao ticketTypeDao;
    @FXML
    public FlowPane mainFlowPane;
    @FXML
    public Button startButton;
    private final ApplicationContext applicationContext;
    @Value("classpath:/views/ticket/searching/ticket-search-view.fxml")
    private Resource resource;
    @Setter
    private Stage stage;

    public MainController(TicketTypeDao ticketTypeDao, ApplicationContext applicationContext) {
        this.ticketTypeDao = ticketTypeDao;
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.setOnMouseClicked(event -> {
            System.out.println("++++");
            ticketTypeDao.findAll().forEach(System.out::println);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
                fxmlLoader.setControllerFactory(param -> applicationContext.getBean(param));
                Parent parent = fxmlLoader.load();

                stage.setScene(new Scene(parent, 800, 600));

//                ((MainController) fxmlLoader.getController()).setStage(stage);

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
