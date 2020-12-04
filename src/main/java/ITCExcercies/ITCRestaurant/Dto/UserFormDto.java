package ITCExcercies.ITCRestaurant.Dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserFormDto {

    @NotEmpty(message = "The \"email\" field requierd!")
    @Email(message = "The given email address is not valid!")
    private String username;

    @NotEmpty(message = "The \"password\" field required!")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
