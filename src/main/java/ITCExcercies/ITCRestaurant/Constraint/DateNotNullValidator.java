package ITCExcercies.ITCRestaurant.Constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.Date;

public class DateNotNullValidator implements
        ConstraintValidator<DateNotNull, Date> {
    @Override
    public void initialize(DateNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext context) {
        return date!=null;
    }
}
