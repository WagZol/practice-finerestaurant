package ITCExcercies.ITCRestaurant.Service;

import ITCExcercies.ITCRestaurant.Dao.UserData;
import ITCExcercies.ITCRestaurant.Dto.UserFormDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    UserData getUserDataByEmail(String userName);
    User loadUserByUsername(String userName);
    boolean checkCredentials(String email, String password);
    boolean authenticatedUserIsAdmin();
    UserData save(UserFormDto userToRegister);
    boolean isUserExist(String email);

}
