package ITCExcercies.ITCRestaurant.Service;

import ITCExcercies.ITCRestaurant.Dao.Role;
import ITCExcercies.ITCRestaurant.Dao.UserData;
import ITCExcercies.ITCRestaurant.Dto.UserFormDto;
import ITCExcercies.ITCRestaurant.Repository.RoleRepository;
import ITCExcercies.ITCRestaurant.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserData getUserDataByEmail(String email) {
        UserData userData = userRepository.findByEmail(email);
        if (userData == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return userData;
    }

    @Override
    public UserData save(UserFormDto userToRegister){
        UserData user = new UserData();
        Role userRole = roleRepository.findByName("ROLE_USER");

        user.setUsername(userToRegister.getUsername());
        user.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
        user.setRoles(Arrays.asList(userRole));
        return userRepository.save(user);
    }

    @Override
    public boolean isUserExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User loadUserByUsername(String email) {
        UserData userData = getUserDataByEmail(email);
        return new User(userData.getEmail(),
                userData.getPassword(),
                mapRolesToAuthorities(userData.getRoles()));
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }


    public boolean checkCredentials(String email, String password){
        UserData registeredUserData =userRepository.findByEmail(email);
        System.out.println(registeredUserData);
        if (registeredUserData ==null)
            return false;
        boolean isPasswordsMatch=passwordEncoder.matches(password, registeredUserData.getPassword());
        System.out.println(isPasswordsMatch);
        return isPasswordsMatch;
    }

    public boolean authenticatedUserIsAdmin(){
        Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities();
        return roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }


}
