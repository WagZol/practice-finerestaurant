package ITCExcercies.ITCRestaurant.Service;

import ITCExcercies.ITCRestaurant.Dao.Course;
import ITCExcercies.ITCRestaurant.Dto.CourseModificationDto;

import java.util.List;

public interface ICourseService {
     Course getCourseById(int id);
     List<Course> findAll();
     List<Course> findByName(String name);
     void deleteById(long id);
     void update(CourseModificationDto course);
     void create(CourseModificationDto course);
     Course findById(long id);
}
