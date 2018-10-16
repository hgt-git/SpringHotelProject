package jProject.repositories;


import jProject.models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomTypeRepository extends
        JpaRepository<RoomType, Integer> {


    @Query(value="SELECT * FROM room_type WHERE bed_types = COALESCE(?1 , bed_types) AND ( (person_count >=?2) OR ?2 = 0) AND view=COALESCE(?3,view) AND (( price >=?4 OR ?4 = 0) AND (price <?5 OR ?5 = 0))  ", nativeQuery = true)
    List<RoomType> searchRoom(String bed_types, int person_count, String view, int minPrice, int maxPrice );


}



