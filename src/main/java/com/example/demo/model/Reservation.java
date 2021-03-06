package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;
    @Column(name = "how_many_people", nullable = false)
    private Integer howManyPeople;
    @Column(name = "phone_number", length = 12, nullable = false)
    private String phoneNumber;
    @Column(name = "resevation_start", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDayStart;
    @Column(name = "resevation_end", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDayEnd;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_details")
    private ReservationDetails details;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Reservation() {
    }

    public Reservation(String firstName, String lastName, Integer howManyPeople, String phoneNumber, LocalDate reservationDayStart, LocalDate reservationDayEnd) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.howManyPeople = howManyPeople;
        this.phoneNumber = phoneNumber;
        this.reservationDayStart = reservationDayStart;
        this.reservationDayEnd = reservationDayEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getHowManyPeople() {
        return howManyPeople;
    }

    public void setHowManyPeople(Integer howManyPeople) {
        this.howManyPeople = howManyPeople;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getReservationDayStart() {
        return reservationDayStart;
    }

    public void setReservationDayStart(LocalDate reservationDayStart) {
        this.reservationDayStart = reservationDayStart;
    }

    public LocalDate getReservationDayEnd() {
        return reservationDayEnd;
    }

    public void setReservationDayEnd(LocalDate reservationDayEnd) {
        this.reservationDayEnd = reservationDayEnd;
    }

    public ReservationDetails getDetails() {
        return details;
    }

    public void setDetails(ReservationDetails details) {
        this.details = details;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


}