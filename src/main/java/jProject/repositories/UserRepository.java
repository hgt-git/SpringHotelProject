package jProject.repositories;

import jProject.models.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends
        JpaRepository<UserData, Integer> {
    @Query(value="SELECT COUNT(email) FROM user_data WHERE email=?1", nativeQuery = true)
    short emailCount(String email);

    @Query(value="SELECT * FROM user_data WHERE email=?1", nativeQuery = true)
    UserData findByName(String email);


}



