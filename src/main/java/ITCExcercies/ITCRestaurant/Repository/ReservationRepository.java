package ITCExcercies.ITCRestaurant.Repository;

import ITCExcercies.ITCRestaurant.Dao.Course;
import ITCExcercies.ITCRestaurant.Dao.Reservation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    Reservation findById(long id);

    List<Reservation> findAll();

    void deleteById(long id);

//    Nyilvánvalóan itt a CrudRepositroy-t kellett volna használnom, már csak a műveletek jellege miatt is (Create, Read,
//    Update, Delete de sajnos a 'save' esetén amikor új entitást kéne elmentenem az adatbázisba akkor az id nem áll
//    át a soron következő id-re amit automatikusan kigenerál, duplikált id jön létre. 3 ötletem lett volna a
//    hibára.:
//    *Valahol nem új entitást hoztam létre, hanem egy régi objektumot akartam felülírni, lehet hogy a modelattribute-nál
//    *A JPA GeneratedId-nál más stratégiát kéne kipróbálni
//    *Hagyni kellett volna hogy a hibernate magától generálja ki a táblákat
//
//    Sajnos idő hijján kényszermegoldást kellett alkalmaznom, mivel inkább mutatok be egy csúnya de működő kódot
//    mint semmilyet

    @Query(value = "UPDATE reservation SET name = :name, date = :date, seats = :seats WHERE id = :id",
            nativeQuery = true)
    @Modifying
    @Transactional
    void update(@Param("id") long id, @Param("name") String name, @Param("date") Date reservationDate,
                    @Param("seats") int seats);


    @Query(value = "INSERT INTO reservation (name, date, seats)" +
                    "VALUES (:name, :date, :seats);",
            nativeQuery = true)
    @Modifying
    @Transactional
    void create(@Param("name") String reservationName, @Param("date") Date date,
                @Param("seats") int seats);
}
