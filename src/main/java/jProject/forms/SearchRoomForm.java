package jProject.forms;


import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.validation.constraints.Pattern;


public class SearchRoomForm {

    private int personCount;

    private String view;

    private String bedTypes;

    @Pattern(regexp = "^[0-9]*$",message ="Only numbers are allowed")
    private int minPrice;//Min price per night

    @Pattern(regexp = "^[0-9]*$",message ="Only numbers are allowed")
    private int maxPrice;//Max price per night


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

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() { return maxPrice; }

    public void setMaxPrice(int maxPrice) { this.maxPrice = maxPrice; }



}