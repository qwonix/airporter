package ru.qwonix.suai.airporter.controller.ticket.searching;

import javafx.collections.FXCollections;
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
import ru.qwonix.suai.airporter.controller.ticket.AirportCellController;
import ru.qwonix.suai.airporter.controller.ticket.AirportSelectedCellController;
import ru.qwonix.suai.airporter.controller.ticket.TicketTypeCellController;
import ru.qwonix.suai.airporter.model.dao.AirportDao;
import ru.qwonix.suai.airporter.model.dao.TicketTypeDao;
import ru.qwonix.suai.airporter.model.entity.Airport;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
        ticketTypeSearchableComboBoxInit();
    }

    private void ticketTypeSearchableComboBoxInit() {
        departureSearchCB.getSelectionModel()
                .selectedItemProperty()
                .addListener(observable -> {
                    Airport selectedAirport = departureSearchCB.getSelectionModel().getSelectedItem();
                    List<TicketType> allByFlight_departureAirport = ticketTypeDao.findAllByFlight_DepartureAirport(selectedAirport);
                    ticketTypeListView.getItems().clear();
                    ticketTypeListView.getItems().addAll(allByFlight_departureAirport);
                    allByFlight_departureAirport.forEach(System.out::println);
                    System.out.println(ticketTypeListView.getItems().size() + "-------------");
                });

        departureSearchCB.setButtonCell(getAirportsListCell());
        arrivalSearchCB.setButtonCell(getAirportsListCell());

        departureSearchCB.setCellFactory(this::call);
        arrivalSearchCB.setCellFactory(this::call);

        departureSearchCB.setItems(FXCollections.observableArrayList(airportDao.findAll()));
        arrivalSearchCB.setItems(FXCollections.observableArrayList(airportDao.findAll()));


        departureSearchCB.getSelectionModel()
                .selectNext();
        arrivalSearchCB.getSelectionModel()
                .selectFirst();

    }

    private ListCell<Airport> getAirportsListCell() {
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
                    setGraphic(null);
                }
            }
        };
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
                    setGraphic(null);
                }
            }
        });
//        ticketTypeListView.setItems(FXCollections.observableArrayList(ticketTypeDao.findAll()));
    }

    private ListCell<Airport> call(ListView<Airport> param) {
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
                    setGraphic(null);
                }
            }
        };
    }
}
