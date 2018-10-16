package jProject.forms;

import java.util.HashSet;

public class AllRoomTypes {


    private HashSet<Integer> personCounts;
    private HashSet<String> views;
    private HashSet<String> bedTypes;

    public AllRoomTypes(){
        personCounts = new HashSet<>();
        views = new HashSet<>();
        bedTypes = new HashSet<>();
    }

    public HashSet<Integer> getPersonCount() {
        return personCounts;
    }

    public void AddPersonCount(Integer personCount) {
        this.personCounts.add(personCount);
    }

    public HashSet<String> getViews() {
        return views;
    }

    public void AddView(String view) {
        this.views.add(view);
    }

    public HashSet<String> getBedTypes() {
        return bedTypes;
    }

    public void AddBedTypes(String bedType) {
        this.bedTypes.add(bedType);
    }

}
