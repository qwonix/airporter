package ru.qwonix.suai.airporter.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@ToString
@Entity
@Table(name = "airport")
public class Airport {

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

}