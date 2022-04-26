package ru.qwonix.suai.airporter.views.ticket;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.io.IOException;

public class TicketTypesListCell extends ListCell<TicketType> {
    @Override
    protected void updateItem(TicketType ticketType, boolean b) {
        super.updateItem(ticketType, b);

        if (!b) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ticket-type-cell-layout.fxml"));
                Parent cellLayout = loader.load();
                TicketTypeCellController cellLayoutController = loader.getController();
                cellLayoutController.CellSetup(ticketType);

                setGraphic(cellLayout);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
