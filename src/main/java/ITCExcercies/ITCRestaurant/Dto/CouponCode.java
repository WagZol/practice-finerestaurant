package ITCExcercies.ITCRestaurant.Dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CouponCode {

    @NotNull(message = "The \"coupon code\" field requierd!")
    @Pattern(regexp = "^\\d{9}$", message = "The \"coupon code\" field must be 9 digits long!")
    String code;

    public CouponCode(String code) {
        this.code = code;
    }

    public CouponCode() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
