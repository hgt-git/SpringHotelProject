package jProject.services;


import jProject.forms.ReservationForm;
import jProject.forms.RoomForm;
import jProject.models.Reservation;
import jProject.models.RoomType;

import java.sql.Date;
import java.util.List;


public interface ReservationService {

    Reservation addNewReservation(ReservationForm reservationForm);
    List<Reservation> getAllReservations();
    Reservation findById(Integer id);
    Reservation Update(Reservation reservationEdit);
    int checkReservations (int id, Date start, Date end);
    int getDays(int id);
    List<Reservation> getReservationsById(int id);
    void paymentIsComplete(int reservationId);
}
