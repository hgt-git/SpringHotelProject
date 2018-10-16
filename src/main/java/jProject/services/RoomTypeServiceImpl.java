package jProject.services;


import jProject.forms.RoomForm;
import jProject.forms.SearchRoomForm;
import jProject.models.RoomType;
import jProject.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class RoomTypeServiceImpl implements RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public RoomType Update(RoomType roomEdit) {

        return  roomTypeRepository.save(roomEdit);
    }

    @Override
    public List<RoomType> getAllRoomTypes() {
         return roomTypeRepository.findAll();
    }


    @Override
    public RoomType addNewRoomType(RoomForm roomForm) {
        RoomType roomType = new RoomType();
        roomType.setRoomName(roomForm.getRoomName());
        roomType.setRoomCount(roomForm.getRoomCount());
        roomType.setPersonCount(roomForm.getPersonCount());
        roomType.setView(roomForm.getView());
        roomType.setBedTypes(roomForm.getBedTypes());
        roomType.setDescription(roomForm.getDescription());
        roomType.setSize(roomForm.getSize());
        roomType.setPrice(roomForm.getPrice());

        return roomTypeRepository.save(roomType);
    }


    @Override
    public RoomType findById(Integer id) {
        return this.roomTypeRepository.findOne(id);
    }

    @Override
    public List<RoomType> findByUserCriteria(SearchRoomForm searchedRoom){

       return  roomTypeRepository.searchRoom(searchedRoom.getBedTypes(), searchedRoom.getPersonCount(), searchedRoom.getView(), searchedRoom.getMinPrice(), searchedRoom.getMaxPrice());
    }


}
