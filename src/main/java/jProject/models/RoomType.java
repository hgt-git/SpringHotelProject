package jProject.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String roomName;

    @NotNull
    private int roomCount;

    @NotNull
    private int personCount;

    @NotNull
    private String view;

    @NotNull
    private String bedTypes;

    @NotNull
    private String description;

    @NotNull
    private int size;

    @NotNull
    private int price;

    @OneToMany(mappedBy = "chosenRoom")
    private Set<Reservation> reservationId = new HashSet<Reservation>();

    @OneToMany(mappedBy = "room")
    private Set<Image> images = new HashSet<Image>();





    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getBedTypes() {
        return bedTypes;
    }

    public void setBedTypes(String bedTypes) {
        this.bedTypes = bedTypes;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public Set<Reservation> getReservationId() {
        return reservationId;
    }

    public void setReservationId(Set<Reservation> reservationId) {
        this.reservationId = reservationId;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}