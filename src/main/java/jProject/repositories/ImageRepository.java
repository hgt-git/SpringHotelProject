package jProject.repositories;

import jProject.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends
        JpaRepository<Image, Integer> {

    @Query(value="SELECT max(id) FROM image", nativeQuery = true)
    Integer imgLastId();

}



