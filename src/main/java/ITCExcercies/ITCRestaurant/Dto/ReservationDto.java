package ITCExcercies.ITCRestaurant.Dto;

import ITCExcercies.ITCRestaurant.Constraint.DateNotNull;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

public class ReservationDto {
    Long id;

    @NotEmpty(message = "The \"name\" field requierd!")
    String name;

    @DateNotNull(message = "The \"date\" field requierd!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    Date date;

    @Min(value = 1, message = "The \"seats\" field must be greater then 0")
    int seats;

    public ReservationDto(String name, Date date, int seats, Long id) {
        this.name = name;
        this.date = date;
        this.seats = seats;
        this.id = id;
    }

    public ReservationDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
