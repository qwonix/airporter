package ru.qwonix.suai.airporter.views.ticket;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class TicketsListCell extends ListCell<Ticket> {
    @Override
    protected void updateItem(Ticket ticket, boolean b) {
        super.updateItem(ticket, b);

        if (!b) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ticket-cell-layout.fxml"));
                Parent cellLayout = loader.load();
                TicketCellController cellLayoutController = loader.getController();
                cellLayoutController.CellSetup(ticket);

                setGraphic(cellLayout);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
