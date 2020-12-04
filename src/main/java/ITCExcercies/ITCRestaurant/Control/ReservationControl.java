package ITCExcercies.ITCRestaurant.Control;

import ITCExcercies.ITCRestaurant.Dao.Course;
import ITCExcercies.ITCRestaurant.Dao.Reservation;
import ITCExcercies.ITCRestaurant.Dto.CourseModificationDto;
import ITCExcercies.ITCRestaurant.Dto.ReservationDto;
import ITCExcercies.ITCRestaurant.Service.ReservationService;
import ITCExcercies.ITCRestaurant.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationControl {

    @Autowired
    ReservationService reservationService;

    @Autowired
    UserService userService;

    @ModelAttribute("reservation")
    public ReservationDto getReservationDto(){
        return new ReservationDto();
    }

    @ModelAttribute("editedReservation")
    public ReservationDto getEditedReservationDto(){
        return new ReservationDto();
    }


    @GetMapping()
    public String getReservationPage(Model model) {
        model.addAttribute("message", "");
        if(userService.authenticatedUserIsAdmin()) {
            List<Reservation> reservations = reservationService.findAll();
            model.addAttribute("reservations", reservations);
            return "reservation_admin";
        }
        return "reservation";
    }

    @PostMapping()
    public String setReservation(@ModelAttribute("reservation") @Valid ReservationDto reservation,
                                 BindingResult result, HttpServletResponse response, Model model){

        Date reservationDate=reservation.getDate();

        if (!reservationService.haveEnoughSeats(reservation.getSeats())) {
            result.rejectValue("seats", null, "We dont have enough seats(max 60) for that time! Please" +
                    " select another one");
        }

        if (reservationDate!=null && reservationService.isDateInvalid(reservationDate)){
            response.setStatus(400);
            result.rejectValue("date", null, "Choose a reservation time at least 1 hour later " +
                    "than the current one");
        }

        if (!result.hasErrors()){
            model.addAttribute("message", "Your reservation time registered! We look forward to " +
                    "seeing you!");
            reservationService.create(reservation);
        }

        return "reservation :: #reservation-form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin")
    public String deleteCourse(@RequestParam(name = "id") long id, Model model, HttpServletResponse response){
        if (reservationService.findById(id)==null){
            response.setStatus(404);
            model.addAttribute("message", "Reservation with this id not found!");
            return "reservation_admin :: reservation";
        }
        reservationService.deleteById(id);
        return null;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin")
    public String updateCourse(@ModelAttribute("editedCourse") @Valid ReservationDto updatedReservation,
                               BindingResult result, Model model, HttpServletResponse response){
        if (result.hasErrors()) {
            response.setStatus(400);
            return "reservation_admin :: #reservation-modification-modal-body";
        }

        if (reservationService.findById(updatedReservation.getId())==null){
            response.setStatus(404);
            return "Reservation with this id not found!";
        }
        reservationService.update(updatedReservation);
        return null;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin")
    public String create(@ModelAttribute("editedCourse") @Valid ReservationDto newReservation,
                            BindingResult result, Model model, HttpServletResponse response){
        if (result.hasErrors()) {
            for (ObjectError error:
                    result.getAllErrors()) {
                System.out.println(error);
            }
            response.setStatus(400);
            return "reservation_admin :: #reservation-modification-modal-body";
        }
        reservationService.create(newReservation);
        return null;
    }

    @GetMapping("/reservationItems")
    public String getCourse(@RequestParam(name = "id", required = false)Long id, Model model){
        if (id==null){
            List<Reservation> reservations = reservationService.findAll();
            model.addAttribute("reservations", reservations);
            return "reservation_admin :: .reservation-list";
        }

        Reservation editedReservation=reservationService.findById(id);
        model.addAttribute("editedReservation", editedReservation);

        return "reservation_admin :: #reservation-modification-modal-body";
    }



}
