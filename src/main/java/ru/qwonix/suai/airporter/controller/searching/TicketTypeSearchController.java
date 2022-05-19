package ru.qwonix.suai.airporter.controller.searching;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.SearchableComboBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.dao.AirportDao;
import ru.qwonix.suai.airporter.model.dao.TicketTypeDao;
import ru.qwonix.suai.airporter.model.entity.Airport;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Контроллер окна выбора билета
 */
@Slf4j
@Component
public class TicketTypeSearchController implements Initializable {

    private final ApplicationContext applicationContext;
    private final TicketTypeDao ticketTypeDao;
    private final AirportDao airportDao;

    @FXML
    private ListView<TicketType> ticketTypeListView;
    @FXML
    private SearchableComboBox<Airport> departureSearchCB, arrivalSearchCB;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private Label searchComboBoxCaption;

    @Value("classpath:/views/searching/ticketTypeCell/ticket-type-cell-layout.fxml")
    private Resource ticketTypeCellView;

    @Value("classpath:/views/searching/airportCell/airport-cell-layout.fxml")
    private Resource airportCellView;

    @Value("classpath:/views/searching/airportCell/airport-selected-cell-layout.fxml")
    private Resource airportSelectedCellView;


    public TicketTypeSearchController(ApplicationContext applicationContext, TicketTypeDao ticketTypeDao, AirportDao airportDao) {
        this.applicationContext = applicationContext;
        this.ticketTypeDao = ticketTypeDao;
        this.airportDao = airportDao;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketTypeListViewInit();
        airportSearchableComboBoxInit();
    }

    @FXML
    private void onFindButtonClicked(MouseEvent mouseEvent) {
        Airport departureAirport = departureSearchCB.getSelectionModel().getSelectedItem();
        Airport arrivalAirport = arrivalSearchCB.getSelectionModel().getSelectedItem();

        List<TicketType> ticketTypes;
        if (departureAirport == null || arrivalAirport == null) {
            searchComboBoxCaption.setText("Выберите аэропорты");
            return;
        } else if (departureDatePicker.getValue() == null) {
            searchComboBoxCaption.setText("Выберите дату вылета");
            return;
        }

        ticketTypes = ticketTypeDao.findAllByDepartureAirportAndArrivalAirportAndDepartureDate
                (departureAirport, arrivalAirport, departureDatePicker.getValue());

        ticketTypeListView.setItems(FXCollections.observableArrayList(ticketTypes));
        searchComboBoxCaption.setText(null);
    }


    private void ticketTypeListViewInit() {
        ticketTypeListView.setCellFactory(param -> new ListCell<>() {
            @Override
            public void updateItem(TicketType ticketType, boolean empty) {
                super.updateItem(ticketType, empty);
                if (!empty && ticketType != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(ticketTypeCellView.getURL());
                        loader.setControllerFactory(applicationContext::getBean);
                        Parent cellLayout = loader.load();
                        TicketTypeCellController cellLayoutController = loader.getController();
                        cellLayoutController.cellSetup(ticketType);

                        this.setGraphic(cellLayout);
                    } catch (IOException ex) {
                        log.error("failed to create new ticket type cell: {}", ex.getMessage());
                    }
                } else {
                    this.setGraphic(null); // duplication element bug fix
                }
            }
        });

        List<TicketType> ticketTypes = ticketTypeDao.findAllByTicket_PassengerIsNull();
        ticketTypeListView.setItems(FXCollections.observableArrayList(ticketTypes));
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

        ObservableList<Airport> airports = FXCollections.observableArrayList(
                airportDao.findAll(Sort.by(Sort.Direction.ASC, "city")));


        departureDatePicker.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
//                    if (LocalDate )
//                        this.setDisable();
                } else {
                    this.setGraphic(null); // duplication element bug fix
                }

//                setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
            }
        });

        departureSearchCB.setItems(airports);
        arrivalSearchCB.setItems(airports);

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

            // todo: replace filter with sql query??
            ticketTypes = ticketTypes.stream()
                    .filter(ticketType -> ticketType.getTickets().size() != 0)
                    .collect(Collectors.toList());

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

                        this.setGraphic(cellLayout);
                    } catch (IOException ex) {
                        log.error("failed to create new selected airport cell: {}", ex.getMessage());
                    }
                } else {
                    this.setGraphic(null); // duplication element bug fix
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

                        this.setGraphic(cellLayout);
                    } catch (IOException ex) {
                        log.error("failed to create new airport preview cell: {}", ex.getMessage());
                    }
                } else {
                    this.setGraphic(null); // duplication element bug fix
                }
            }
        };
    }


}