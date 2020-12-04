package ITCExcercies.ITCRestaurant.Service;

import ITCExcercies.ITCRestaurant.Dao.Reservation;
import ITCExcercies.ITCRestaurant.Dto.ReservationDto;
import ITCExcercies.ITCRestaurant.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class ReservationService implements IReservationService {

    final int MAX_AVAILABLE_SEATS = 60;
    final int SEATS_PER_TABLE = 4;

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public Reservation getReservationById(int id) {
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findByName(String name) {
        return null;
    }

    @Override
    public void deleteById(long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public void update(ReservationDto reservation) {
        reservationRepository.update(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getSeats()
        );
    }

    @Override
    public void create(ReservationDto reservation) {
        reservationRepository.create(
                reservation.getName(),
                reservation.getDate(),
                reservation.getSeats()
        );
    }

    @Override
    public Reservation findById(long id) {
        return reservationRepository.findById(id);
    }

    public boolean isDateInvalid(Date date) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR, 1);
        System.out.println(calendar.getTime());
        System.out.println(date);

        return date.before(calendar.getTime());
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    //this method needed because we dont want to sit together peoples who dont know each other
    private int getAvailableSeats() {
        List<Reservation> reservations = getAllReservation();
        int availableSeats = MAX_AVAILABLE_SEATS;

        if (reservations != null) {
            for (Reservation reservation :
                    reservations) {
                availableSeats = ((availableSeats - reservation.getSeats()) / SEATS_PER_TABLE) * SEATS_PER_TABLE;
            }
        }

        return availableSeats;
    }

    public boolean haveEnoughSeats(int seats) {
        return seats <= getAvailableSeats();
    }
}
