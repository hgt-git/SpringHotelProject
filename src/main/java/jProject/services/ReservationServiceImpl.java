package jProject.services;


import jProject.forms.ReservationForm;
import jProject.models.Reservation;
import jProject.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;


@Service
@Primary
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;


    public int checkReservations(int id, Date start, Date end){ return reservationRepository.reservedSameRoomCount(id,start,end);}

    @Override
    public Reservation addNewReservation(ReservationForm reservationForm) {
        Reservation reservation = new Reservation();
        reservation.setChosenRoom(reservationForm.getChosenRoom());
        reservation.setUser(reservationForm.getUser());
        reservation.setReservationStart(reservationForm.getReservationStart());
        reservation.setReservationEnd(reservationForm.getReservationEnd());
        reservation.setStatus(false);
        reservation.setPaid(false);


        return reservationRepository.save(reservation);
    }

    public int getDays(int id){return reservationRepository.getDays(id); }

    @Override
    public List<Reservation> getAllReservations() { return reservationRepository.findAll(); }

    @Override
    public Reservation findById(Integer id){return  reservationRepository.findOne(id);}


    @Override
    public Reservation Update(Reservation reservationEdit) {

        return  reservationRepository.save(reservationEdit);
    }


    public List<Reservation> getReservationsById(int id){
        return reservationRepository.reservationsById(id);
    }

    public void paymentIsComplete(int reservationId){

        reservationRepository.paymentConfirm(reservationId);
    }

}
