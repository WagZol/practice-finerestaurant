package ITCExcercies.ITCRestaurant.Repository;

import ITCExcercies.ITCRestaurant.Dao.UserData;
import ITCExcercies.ITCRestaurant.Dto.UserFormDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {
	UserData findByEmail(String email);
	UserData save(UserFormDto user);
	boolean existsByEmail(String email);
}
