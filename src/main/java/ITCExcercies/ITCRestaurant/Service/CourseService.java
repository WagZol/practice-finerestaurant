package ITCExcercies.ITCRestaurant.Service;

import ITCExcercies.ITCRestaurant.Dao.Course;
import ITCExcercies.ITCRestaurant.Dto.CourseModificationDto;
import ITCExcercies.ITCRestaurant.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService implements ICourseService{

    @Autowired
    CourseRepository courseRepository;

    final String CURRENCY="$";
    List<String[]> shoppingCart=new ArrayList<>();
    double total=0;

    @Override
    public Course getCourseById(int id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findByName(String name) {
        return courseRepository.findCoursesByName(name);
    }

    @Override
    public void deleteById(long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public void update(CourseModificationDto course) {
        courseRepository.update(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getPrice()
        );
    }

    @Override
    public void create(CourseModificationDto course) {
        courseRepository.create(
                course.getName(),
                course.getDescription(),
                course.getPrice()
        );
    }

    @Override
    public Course findById(long id) {
        return courseRepository.findById(id);
    }

    public List<String[]> addElementToShoppingCart(String[] item){
        shoppingCart.add(item);
        return shoppingCart;
    }

    public List<String[]> removeElementFromShoppingCart(int index){
        shoppingCart.remove(index);
        return shoppingCart;
    }

    public double incrementTotal(double itemPrice) throws IllegalArgumentException{
        if (total+itemPrice<0)
            throw new IllegalArgumentException("Total must be 0 or positive!");
        return Math.round((total+=itemPrice) * 100.0) / 100.0;
    }

    public double decrementTotal(double itemPrice) throws IllegalArgumentException{
        if (total-itemPrice<0)
            throw new IllegalArgumentException("Total must be 0 or positive!");
        return Math.round((total-=itemPrice) * 100.0) / 100.0;
    }

    public void resetCalculator(){
        shoppingCart=new ArrayList<>();
        total=0;
    }

    public String convertPrice(double price){
        String priceString=Double.toString(price);
        if(price<0) {
            priceString = priceString.replace("-", "");
            return "-" + CURRENCY + priceString;
        }
        return " "+CURRENCY+priceString;
    }
}
