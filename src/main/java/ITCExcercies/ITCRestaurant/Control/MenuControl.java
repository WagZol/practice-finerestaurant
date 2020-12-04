package ITCExcercies.ITCRestaurant.Control;

import ITCExcercies.ITCRestaurant.Dao.Coupon;
import ITCExcercies.ITCRestaurant.Dao.Course;
import ITCExcercies.ITCRestaurant.Dto.CouponCode;
import ITCExcercies.ITCRestaurant.Dto.CourseModificationDto;
import ITCExcercies.ITCRestaurant.Service.CouponService;
import ITCExcercies.ITCRestaurant.Service.CourseService;
import ITCExcercies.ITCRestaurant.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuControl {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    CouponService couponService;

    @ModelAttribute("couponCode")
    public CouponCode getCouponCodeDto() {
        return new CouponCode();
    }

    @ModelAttribute("editedCourse")
    public CourseModificationDto getCourseModificationDto(){
        return new CourseModificationDto();
    }

    @GetMapping()
    public String getMenuPage(Model model) {
        if (userService.authenticatedUserIsAdmin()) {
            List<Course> courses = courseService.findAll();
            model.addAttribute("courses", courses);
            return "menu_admin";
        }
        courseService.resetCalculator();
        return "menu";
    }

    @GetMapping("/searchCourse")
    public String getCourses(@RequestParam(name = "coursename") String courseNameToSearch, Model model) {
        if (courseNameToSearch.equals("")) {
            return "menu :: .menu-list";
        }
        List<Course> courses = courseService.findByName(courseNameToSearch);
        model.addAttribute("courses", courses);
        return "menu :: .menu-list";
    }

    @GetMapping("/addToShoppingCart")
    public String addToShoppingCart(@RequestParam(name = "itemname") String itemName,
                                    @RequestParam(name = "itemprice") double itemPrice, Model model) {
        List<String[]> shoppingCart = courseService.addElementToShoppingCart(new String[]{itemName,
                courseService.convertPrice(itemPrice)});
        double total = courseService.incrementTotal(itemPrice);

        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("total", total);

        return "menu :: .shopping-item-context";
    }

    @GetMapping("/removeFromShoppingCart")
    public String removeFromShoppingCart(@RequestParam(name = "index") int index,
                                         @RequestParam(name = "itemprice") float itemPrice, Model model) {
        List<String[]> shoppingCart = courseService.removeElementFromShoppingCart(index);
        double total = courseService.decrementTotal(itemPrice);

        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("total", total);

        return "menu :: .shopping-item-context";
    }

    @PostMapping("/couponCode")
    public String addCouponByCode(@ModelAttribute("couponCode") @Valid CouponCode couponCode,
                                  BindingResult result, Model model, HttpServletResponse response){
        double total=0.0;

//        Generic errors
        if(result.hasErrors()){
            response.setStatus(400);
            return "menu :: #coupon-form";
        }

//        Coupon not found
        Coupon couponToSearchFor=couponService.findCouponByCode(couponCode.getCode());
        if (couponToSearchFor==null) {
            response.setStatus(400);
            result.rejectValue("code", null, "Invalid coupon code!");
            return "menu :: #coupon-form";
        }

//        More coupon then total
        try {
            total = courseService.incrementTotal(couponToSearchFor.getPrice());
        }catch (IllegalArgumentException exception){
            response.setStatus(400);
            result.rejectValue("code", null, "Discount must be less then total!");
            return "menu :: #coupon-form";
        }
        List<String[]> shoppingCart = courseService.addElementToShoppingCart(new String[]{couponToSearchFor.getName(),
                courseService.convertPrice(couponToSearchFor.getPrice())});


        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("total", total);

        return "menu :: .shopping-item-context";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping()
    public String deleteCourse(@RequestParam(name = "id") long id, Model model, HttpServletResponse response){
        if (courseService.findById(id)==null){
            response.setStatus(404);
            return "Course with this id not found!";
        }
        courseService.deleteById(id);
        return null;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping()
    public String updateCourse(@ModelAttribute("editedCourse") @Valid CourseModificationDto updatedCourse,
                               BindingResult result, Model model, HttpServletResponse response){
        if (result.hasErrors()) {
            response.setStatus(400);
            return "menu_admin :: #course-modification-modal-body";
        }

        if (courseService.findById(updatedCourse.getId())==null){
            response.setStatus(404);
            return "Course with this id not found!";
        }
        courseService.update(updatedCourse);
        return null;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public String addCourse(@ModelAttribute("editedCourse") @Valid CourseModificationDto newCourse,
                            BindingResult result, Model model, HttpServletResponse response){
        if (result.hasErrors()) {
            response.setStatus(400);
            return "menu_admin :: #course-modification-modal-body";
        }
        courseService.create(newCourse);
        return null;
    }

    @GetMapping("/courses")
    public String getCourse(@RequestParam(name = "id", required = false)Long id, Model model){
        if (id==null){
            List<Course> courses = courseService.findAll();
            model.addAttribute("courses", courses);
            return "menu_admin :: .menu-list";
        }

        Course editedCourse=courseService.findById(id);
        model.addAttribute("editedCourse", editedCourse);

        return "menu_admin :: #course-modification-modal-body";
    }
}
