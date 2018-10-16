package jProject.services;


import jProject.forms.RoomForm;
import jProject.forms.SearchRoomForm;
import jProject.models.RoomType;

import java.util.List;


public interface RoomTypeService {

    RoomType Update(RoomType roomEdit);
    List<RoomType> getAllRoomTypes();
    RoomType addNewRoomType(RoomForm roomForm);
    List<RoomType> findByUserCriteria(SearchRoomForm searchedRoom);
    RoomType findById(Integer id);

}
