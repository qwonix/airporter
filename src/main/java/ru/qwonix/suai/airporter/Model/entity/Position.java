package ru.qwonix.suai.airporter.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
@Table(name = "position")
public class Position {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "position_name")
    private String name;

    @Column(name = "responsibilities")
    private String responsibilities;

    @Column(name = "requirement")
    private String requirement;

}
