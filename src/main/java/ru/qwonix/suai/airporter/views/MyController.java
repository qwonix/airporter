package ru.qwonix.suai.airporter.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.views.ticket.Ticket;
import ru.qwonix.suai.airporter.views.ticket.TicketsListCell;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("main-view.fxml")
public class MyController implements Initializable {
    public ListView<Ticket> listView;

    private final ObservableList<Ticket> ticketObservableList;

    public MyController() {
        ticketObservableList = FXCollections.observableArrayList();

        ticketObservableList.addAll(
                new Ticket(1, "A1", 100),
                new Ticket(2, "A2", 130),
                new Ticket(3, "A3", 140),
                new Ticket(4, "A4", 120),
                new Ticket(5, "A5", 110)
        );

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(ticketObservableList);
        listView.setCellFactory(ticketListView -> new TicketsListCell());
    }
}
