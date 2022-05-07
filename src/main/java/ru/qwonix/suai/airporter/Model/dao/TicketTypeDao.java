package ru.qwonix.suai.airporter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.qwonix.suai.airporter.model.entity.Airport;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.util.List;

public interface TicketTypeDao extends JpaRepository<TicketType, Integer> {

    List<TicketType> findAllByFlight_DepartureAirport(Airport airport);

    List<TicketType> findAllByFlight_ArrivalAirport(Airport airport);

    List<TicketType> findAllByFlight_DepartureAirportAndFlight_ArrivalAirport(Airport departureAirport, Airport arrivalAirport);
}
