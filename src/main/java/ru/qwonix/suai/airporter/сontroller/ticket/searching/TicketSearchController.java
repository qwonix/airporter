package ru.qwonix.suai.airporter.сontroller.ticket.searching;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.controlsfx.control.SearchableComboBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.dao.TicketTypeDao;
import ru.qwonix.suai.airporter.model.entity.Airport;
import ru.qwonix.suai.airporter.model.entity.TicketType;
import ru.qwonix.suai.airporter.сontroller.ticket.TicketTypeCellController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class TicketSearchController implements Initializable {

    private final TicketTypeDao ticketTypeDao;

    private final ObservableList<TicketType> ticketTypeObservableList;
    private final ObservableList<TicketType> ticketObservableList;
    @FXML
    private ListView<TicketType> ticketTypeListView;
    @FXML
    private SearchableComboBox<Airport> departureSearchCB, arrivalSearchCB;

    @Value("classpath:/views/ticket/ticketTypeCell/ticket-type-cell-layout.fxml")
    private Resource ticketTypeCell;

    public TicketSearchController(TicketTypeDao ticketTypeDao) {
        this.ticketTypeDao = ticketTypeDao;

        ticketObservableList = FXCollections.observableArrayList();
        ticketTypeObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketTypeListViewInit();
        ticketTypeSearchableComboBoxInit();
    }

    private void ticketTypeSearchableComboBoxInit() {
        departureSearchCB.setItems(FXCollections.observableArrayList());
    }

    private void ticketTypeListViewInit() {
        ticketTypeListView.setCellFactory(param -> new ListCell<>() {
            @Override
            public void updateItem(TicketType ticketType, boolean empty) {
                super.updateItem(ticketType, empty);
                if (!empty && ticketType != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(ticketTypeCell.getURL());
                        Parent cellLayout = loader.load();
                        TicketTypeCellController cellLayoutController = loader.getController();
                        cellLayoutController.cellSetup(ticketType);

                        setGraphic(cellLayout);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        ticketTypeObservableList.addAll(ticketTypeDao.findAll());
        ticketTypeListView.setItems(ticketTypeObservableList);
    }

}
