package ITCExcercies.ITCRestaurant.Converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateToStringConverter implements Converter<Date, String> {
    @Override
    public String convert(Date source) {

        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(source);
    }
}
