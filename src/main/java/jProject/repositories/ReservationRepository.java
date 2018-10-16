package jProject.repositories;

import jProject.models.Reservation;
import jProject.models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

//import org.springframework.data.repository.CrudRepository;

@Repository
public interface ReservationRepository extends
        JpaRepository<Reservation, Integer> {


    @Query(value="SELECT Count(id) FROM spdb.reservation where stat=1 and (chosen_room_id = ?1 and  (?2 <= reservation_start and reservation_start <=?3) or (?2 <=  reservation_end  and reservation_end <= ?3)); ", nativeQuery = true)
    int reservedSameRoomCount(int room_id, Date arrive, Date depart); //Number of reserved rooms per type for the requested period
    @Query(value="SELECT DATEDIFF(reservation_end,reservation_start) from spdb.reservation where id=?1 ; ", nativeQuery = true)
     int getDays(int id) ;
    @Query(value="SELECT * FROM spdb.reservation where user_id = ?1 ; ", nativeQuery = true)
    List<Reservation> reservationsById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="UPDATE spdb.reservation SET paid=true, stat=true where id = ?1 ; ", nativeQuery = true)
    void paymentConfirm(int reservationId);


}



