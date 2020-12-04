package ITCExcercies.ITCRestaurant.Service;

import ITCExcercies.ITCRestaurant.Dao.Course;
import ITCExcercies.ITCRestaurant.Dao.Reservation;
import ITCExcercies.ITCRestaurant.Dto.CourseModificationDto;
import ITCExcercies.ITCRestaurant.Dto.ReservationDto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IReservationService {
    Reservation getReservationById(int id);
    List<Reservation> findAll();
    List<Reservation> findByName(String name);
    void deleteById(long id);
    void update(ReservationDto reservation);
    void create(ReservationDto reservation);
    Reservation findById(long id);
    boolean haveEnoughSeats(int seats);
    boolean isDateInvalid(Date date);
}
