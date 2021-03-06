package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repositories.ReservationDetailsRepository;
import com.example.demo.repositories.ReservationRepository;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.repositories.UnavailableTermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class ReservationController {
    private RoomRepository roomRepository;
    private ReservationRepository reservationRepository;
    private UnavailableTermRepository unavailableTermRepository;
    private RoomToReservation roomToReservation;

    @Autowired
    public ReservationController(RoomRepository roomRepository, ReservationRepository reservationRepository, UnavailableTermRepository unavailableTermRepository, RoomToReservation roomToReservation) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.unavailableTermRepository = unavailableTermRepository;
        this.roomToReservation = roomToReservation;
    }

    @GetMapping("/summary")
       public String basicInfo(@RequestParam(name = "roomId") Long id, Model model){
        Reservation reservation = new Reservation();
        ReservationDetails reservationDetails = new ReservationDetails();
        Room room = roomRepository.getOne(id);
        roomToReservation.setRoom(room);
        model.addAttribute("room", room);
        model.addAttribute("reservation", reservation);
        model.addAttribute("reservationDetails", reservationDetails);
        return "summary";
    }

    @PostMapping("/summary/realize")
    public String realize(@ModelAttribute Reservation reservation, @ModelAttribute ReservationDetails reservationDetails){
        CheckAndSetDate checkAndSetDate = new CheckAndSetDate();
        Room room = roomToReservation.getRoom();
        Room roomFromDataBase = roomRepository.getOne(room.getId());
        boolean isTermAvailable = checkAndSetDate.ifRoomsAvailable(room, roomRepository, reservation, unavailableTermRepository);
        if (!isTermAvailable) {
            return "redirect:roomIsUnavailable";
        } else {
            reservation.setRoom(roomFromDataBase);
            reservationDetails.setDateOfAddingReservation(LocalDate.now());
            reservation.setDetails(reservationDetails);
            roomFromDataBase.addReservation(reservation);
            reservationDetails.setReservation(reservation);
            reservationRepository.save(reservation);
        }
        return "reservationDone";
    }

}
