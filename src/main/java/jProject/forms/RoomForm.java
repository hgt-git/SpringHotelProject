package jProject.forms;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class RoomForm {

    @NotBlank(message = "This field is required!")
    @Pattern(regexp = "^[A-Za-z0-9\\s]*$",message ="Only A-Z a-z and 0-9 are allowed")
    private String roomName;


    @NotNull(message = "This field is required!")
    @Range(min = 1, max = 300,message = "Must be between 1 and 300" )
    private int roomCount;//Number of rooms of this type

    @NotNull(message = "This field is required!")
    @Range(min = 1, max = 20,message = "Must be between 1 and 20" )
    private int personCount;//Max number of people in room

    @NotBlank(message = "This field is required!")
    @Pattern(regexp = "^[A-Za-z0-9\\s]*$",message ="Only A-Z a-z and 0-9 are allowed")
    private String view;

    @NotBlank(message = "This field is required!")
    @Pattern(regexp = "^[A-Za-z0-9\\s]*$",message ="Only A-Z a-z and 0-9 are allowed")
    private String bedTypes;

    @NotBlank(message = "This field is required!")
    @Pattern(regexp = "^[A-Za-z0-9\\s]*$",message ="Only A-Z a-z and 0-9 are allowed")
    private String description;

    @NotNull(message = "This field is required!")
    @Range(min = 1, max = 1000,message = "Must be between 1 and 1000" )
    private int size;//Room size

    @NotNull(message = "This field is required!")
    @Range(min = 1, max = 10000,message = "Must be between 1 and 10000" )
    private int price;//Price per night


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}