package ru.qwonix.suai.airporter.model.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "ticket")
public class Ticket {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "seat_id")
    private String seatId;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", seatId='" + seatId + '\'' +
                ", passenger=" + passenger +
//                ", ticketType=" + ticketType +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;
}