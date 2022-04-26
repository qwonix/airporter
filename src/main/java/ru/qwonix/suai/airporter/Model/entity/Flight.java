package ru.qwonix.suai.airporter.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@ToString
@Entity
@Table(name = "flight")
public class Flight {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "scheduled_departure")
    private ZonedDateTime scheduledDeparture;

    @Column(name = "scheduled_arrival")
    private ZonedDateTime scheduledArrival;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "departure_airport")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport")
    private Airport arrivalAirport;

    @ManyToOne
    @JoinColumn(name = "aircraft_code")
    private Aircraft aircraft;

    @OneToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;

    @Column(name = "actual_departure")
    private ZonedDateTime actualDeparture;

    @Column(name = "actual_arrival")
    private ZonedDateTime actualArrival;
}