package jProject.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserData user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RoomType chosenRoom;

    private Date reservationStart;

    private Date reservationEnd;

    private boolean stat; //true=approved false=pending

    private  boolean paid;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public RoomType getChosenRoom() {
        return chosenRoom;
    }

    public void setChosenRoom(RoomType chosenRoom) {
        this.chosenRoom = chosenRoom;
    }

    public Date getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(Date reservationStart) {
        this.reservationStart = reservationStart;
    }

    public Date getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(Date reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    public boolean getStatus() {
        return stat;
    }

    public void setStatus(boolean status) {
        this.stat = status;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
