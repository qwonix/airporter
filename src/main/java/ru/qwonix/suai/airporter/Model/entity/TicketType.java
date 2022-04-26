package ru.qwonix.suai.airporter.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Data
@ToString
@Entity
@Table(name = "ticket_type")
public class TicketType {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "condition_id")
    private Condition condition;

    @Column(name = "price")
    private int price;

    @OneToMany(mappedBy = "ticketType", fetch = FetchType.EAGER)
    private List<Ticket> tickets;
}
