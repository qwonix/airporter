package ru.qwonix.suai.airporter.controller.ticket;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import ru.qwonix.suai.airporter.model.entity.Ticket;

@Component
public class SeatCellController {
    @FXML
    public Label seatId;

    public void cellSetup(Ticket ticket) {
        seatId.setText(ticket.getSeatId());
    }
}