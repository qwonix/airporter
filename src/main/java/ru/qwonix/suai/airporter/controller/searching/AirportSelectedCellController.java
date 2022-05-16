package ru.qwonix.suai.airporter.controller.searching;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.entity.Airport;

/**
 * Контроллер текущей выбранной ячейки Аэропорта SearchableComboBox
 */
@Component
public class AirportSelectedCellController {
    @FXML
    private Label airportCity, airportId;

    public void cellSetup(Airport airport) {
        airportCity.setText(airport.getCity());
        airportId.setText(airport.getId());
    }
}