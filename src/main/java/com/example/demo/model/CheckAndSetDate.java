package com.example.demo.model;


import com.example.demo.repositories.RoomRepository;
import com.example.demo.repositories.UnavailableTermRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckAndSetDate {


    public boolean ifRoomsAvailable(Room room, RoomRepository roomRepository, Reservation reservation, UnavailableTermRepository unavailableTermRepository) {
        LocalDate reservationDayStart = reservation.getReservationDayStart();
        LocalDate reservationDayEnd = reservation.getReservationDayEnd();
        List<UnavailableTerm> unavailableTerms = roomRepository.getOne(room.getId()).getUnavailableTerms();
        for (UnavailableTerm unavailableTerm : unavailableTerms) {
            if (unavailableTerm.getStartOfUnavailableTerm().isAfter(reservationDayStart)) {
                if (!reservationDayEnd.isBefore(unavailableTerm.getStartOfUnavailableTerm())) {
                    return false;
                }
            } else if (unavailableTerm.getStartOfUnavailableTerm().isBefore(reservationDayStart)) {
                if (!reservationDayStart.isAfter(unavailableTerm.getEndOfUnavailableTerm())) {
                    return false;
                }
            } else if (unavailableTerm.getStartOfUnavailableTerm().isEqual(reservationDayStart)) {
                return false;
            }
        }
        setDatesOnRoom(roomRepository, reservationDayStart, reservationDayEnd, room, unavailableTermRepository);
        return true;
    }

    public void setDatesOnRoom(RoomRepository roomRepository, LocalDate reservationDayStart, LocalDate reservationDayEnd, Room room, UnavailableTermRepository unavailableTermRepository) {
        UnavailableTerm unavailableTerm = new UnavailableTerm(reservationDayStart, reservationDayEnd);
        Room roomFromDataBase = roomRepository.getOne(room.getId());
        roomFromDataBase.addUnavailableTerm(unavailableTerm);
        unavailableTerm.setRoom(roomFromDataBase);
        unavailableTermRepository.save(unavailableTerm);
    }
}



