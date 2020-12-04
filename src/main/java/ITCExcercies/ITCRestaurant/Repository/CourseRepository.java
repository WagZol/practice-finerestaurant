package ITCExcercies.ITCRestaurant.Repository;

import ITCExcercies.ITCRestaurant.Dao.Course;
import ITCExcercies.ITCRestaurant.Dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findById(long id);

    List<Course> findAll();

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

    @Query(value = "UPDATE course SET name = :name, description = :description, price = :price WHERE id = :id",
            nativeQuery = true)
    @Modifying
    @Transactional
    void update(@Param("id") long id, @Param("name") String name, @Param("description") String description,
                    @Param("price") double price);


    @Query(value = "SELECT * FROM course WHERE name LIKE :courseName% LIMIT 10",
            nativeQuery = true)
    List<Course> findCoursesByName(@Param("courseName") String courseName);


    @Query(value = "INSERT INTO course (name, description, price)" +
                    "VALUES (:name, :description, :price);",
            nativeQuery = true)
    @Modifying
    @Transactional
    void create(@Param("name") String name, @Param("description") String description,
                @Param("price") double price);
}
