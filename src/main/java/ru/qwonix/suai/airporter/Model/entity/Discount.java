package ru.qwonix.suai.airporter.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;


@Data
@ToString
@Entity
@Table(name = "discount")
public class Discount {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pct")
    private int name;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

}
