package ru.qwonix.suai.airporter.views.ticket.searching;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.dao.TicketTypeDao;
import ru.qwonix.suai.airporter.model.entity.TicketType;
import ru.qwonix.suai.airporter.views.ticket.type.TicketTypesListCell;

import java.net.URL;
import java.util.ResourceBundle;

@Component

public class TicketSearchController implements Initializable {
    private final TicketTypeDao ticketTypeDao;
    private final ObservableList<TicketType> ticketObservableList;
    @FXML
    public ListView<TicketType> listView;

    public TicketSearchController(TicketTypeDao ticketTypeDao) {
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
