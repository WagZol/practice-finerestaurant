package ITCExcercies.ITCRestaurant.Dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int seats;


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    private Date date;
    private String name;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date=date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Reservation() {
    }

    public Reservation(Long id, int seats, String name, Date date) {
        this.id = id;
        this.seats = seats;
        this.name = name;
        this.date=date;
    }
}
