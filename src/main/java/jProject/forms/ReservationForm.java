package jProject.forms;



import jProject.models.RoomType;
import jProject.models.UserData;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;


public class ReservationForm {

    private UserData user;

    private RoomType chosenRoom;

    @NotNull(message = "You should pick a start date")
    @NotEmpty(message = "You should pick a start date")
    private Date reservationStart;

    @NotNull(message = "You should pick a end date")
    @NotEmpty(message = "You should pick a end date")
    private Date reservationEnd;


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

    public boolean AreDatesCorrect(){
        if(reservationStart.compareTo(reservationEnd)>=0 && reservationStart.compareTo(new Date(Calendar.getInstance().getTime().getTime()))>=0)
            return true;
        else
            return false;
    }
}