package ru.qwonix.suai.airporter.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.dao.TicketTypeDao;
import ru.qwonix.suai.airporter.model.entity.TicketType;
import ru.qwonix.suai.airporter.views.ticket.TicketTypesListCell;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("main-view.fxml")
public class MainController implements Initializable {
    public ListView<TicketType> listView;

    private final TicketTypeDao ticketTypeDao;

    private final ObservableList<TicketType> ticketObservableList;

    public MainController(TicketTypeDao ticketTypeDao) {
        this.ticketTypeDao = ticketTypeDao;
        ticketObservableList = FXCollections.observableArrayList();

        ticketObservableList.addAll(ticketTypeDao.findAll());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(ticketObservableList);
        listView.setCellFactory(ticketListView -> new TicketTypesListCell());
    }
}
