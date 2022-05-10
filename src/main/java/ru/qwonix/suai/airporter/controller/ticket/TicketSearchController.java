package ru.qwonix.suai.airporter.controller.ticket;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.SearchableComboBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.dao.AirportDao;
import ru.qwonix.suai.airporter.model.dao.TicketTypeDao;
import ru.qwonix.suai.airporter.model.entity.Airport;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@Component
public class TicketSearchController implements Initializable {

    private final TicketTypeDao ticketTypeDao;
    private final AirportDao airportDao;

    @FXML
    private ListView<TicketType> ticketTypeListView;
    @FXML
    private SearchableComboBox<Airport> departureSearchCB, arrivalSearchCB;


    @Value("classpath:/views/ticket/ticketTypeCell/ticket-type-cell-layout.fxml")
    private Resource ticketTypeCellView;

    @Value("classpath:/views/ticket/airportCell/airport-cell-layout.fxml")
    private Resource airportCellView;

    @Value("classpath:/views/ticket/airportCell/airport-selected-cell-layout.fxml")
    private Resource airportSelectedCellView;


    public TicketSearchController(TicketTypeDao ticketTypeDao, AirportDao airportDao) {
        this.ticketTypeDao = ticketTypeDao;
        this.airportDao = airportDao;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketTypeListViewInit();
        airportSearchableComboBoxInit();
    }

    private void ticketTypeListViewInit() {
        ticketTypeListView.setCellFactory(param -> new ListCell<>() {
            @Override
            public void updateItem(TicketType ticketType, boolean empty) {
                super.updateItem(ticketType, empty);
                if (!empty && ticketType != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(ticketTypeCellView.getURL());
                        Parent cellLayout = loader.load();
                        TicketTypeCellController cellLayoutController = loader.getController();
                        cellLayoutController.cellSetup(ticketType);

                        setGraphic(cellLayout);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    setGraphic(null); // duplication element bug fix
                }
            }
        });
        ticketTypeListView.setItems(FXCollections.observableArrayList(ticketTypeDao.findAll()));
    }

    /**
     * Инициализация блока выбора аэропортов вылета и прилёта
     */
    private void airportSearchableComboBoxInit() {
        departureSearchCB.getSelectionModel()
                .selectedItemProperty()
                .addListener(updateTickets());

        arrivalSearchCB.getSelectionModel()
                .selectedItemProperty()
                .addListener(updateTickets());

        departureSearchCB.setButtonCell(getAirportSelectedCell());
        arrivalSearchCB.setButtonCell(getAirportSelectedCell());

        departureSearchCB.setCellFactory(this::getAirportPreviewCell);
        arrivalSearchCB.setCellFactory(this::getAirportPreviewCell);

        ObservableList<Airport> observableAirports
                = FXCollections.observableArrayList(airportDao.findAll(Sort.by(Sort.Direction.ASC, "city")));
        departureSearchCB.setItems(observableAirports);
        arrivalSearchCB.setItems(observableAirports);

//        departureSearchCB.getSelectionModel()
//                .selectNext();
//        arrivalSearchCB.getSelectionModel()
//                .selectFirst();

    }

    private InvalidationListener updateTickets() {
        return o -> {
            Airport departureAirport = departureSearchCB.getSelectionModel().getSelectedItem();
            Airport arrivalAirport = arrivalSearchCB.getSelectionModel().getSelectedItem();

            List<TicketType> ticketTypes;

            if (departureAirport != null && arrivalAirport != null) {
                ticketTypes = ticketTypeDao.findAllByFlight_DepartureAirportAndFlight_ArrivalAirport
                        (departureAirport, arrivalAirport);
            } else if (departureAirport != null) {
                ticketTypes = ticketTypeDao.findAllByFlight_DepartureAirport(departureAirport);
            } else if (arrivalAirport != null) {
                ticketTypes = ticketTypeDao.findAllByFlight_ArrivalAirport(arrivalAirport);
            } else return;

            ticketTypeListView.getItems().clear();
            ticketTypeListView.getItems().addAll(ticketTypes);
        };
    }

    private ListCell<Airport> getAirportSelectedCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Airport airport, boolean empty) {
                super.updateItem(airport, empty);
                if (!empty && airport != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(airportSelectedCellView.getURL());
                        Parent cellLayout = loader.load();
                        AirportSelectedCellController cellLayoutController = loader.getController();
                        cellLayoutController.cellSetup(airport);

                        setGraphic(cellLayout);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    setGraphic(null); // duplication element bug fix
                }
            }
        };
    }

    private ListCell<Airport> getAirportPreviewCell(ListView<Airport> param) {
        return new ListCell<>() {
            @Override
            public void updateItem(Airport airport, boolean empty) {
                super.updateItem(airport, empty);
                if (!empty && airport != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(airportCellView.getURL());
                        Parent cellLayout = loader.load();
                        AirportCellController cellLayoutController = loader.getController();
                        cellLayoutController.cellSetup(airport);

                        setGraphic(cellLayout);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    setGraphic(null); // duplication element bug fix
                }
            }
        };
    }
}
