package ru.qwonix.suai.airporter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.qwonix.suai.airporter.model.entity.Airport;
import ru.qwonix.suai.airporter.model.entity.TicketType;

import java.time.LocalDate;
import java.util.List;

public interface TicketTypeDao extends JpaRepository<TicketType, Integer> {

    @Query("select distinct tt from Ticket t join t.ticketType tt")
    List<TicketType> findAllByTicket_PassengerIsNull();

    List<TicketType> findAllByFlight_DepartureAirport(Airport airport);

    List<TicketType> findAllByFlight_ArrivalAirport(Airport airport);

    List<TicketType> findAllByFlight_DepartureAirportAndFlight_ArrivalAirport(Airport departureAirport, Airport arrivalAirport);

    @Query("from TicketType where CAST(flight.scheduledDeparture as LocalDate) = :scheduledDeparture and flight.departureAirport =:departureAirport and flight.arrivalAirport =:arrivalAirport")
    List<TicketType> findAllByDepartureAirportAndArrivalAirportAndDepartureDate(Airport departureAirport, Airport arrivalAirport, LocalDate scheduledDeparture);

}
