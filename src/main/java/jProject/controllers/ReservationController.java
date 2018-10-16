package jProject.controllers;

import jProject.forms.AllRoomTypes;
import jProject.forms.ReservationForm;
import jProject.forms.SearchRoomForm;
import jProject.models.Reservation;
import jProject.models.RoomType;
import jProject.models.UserData;
import jProject.services.ReservationService;
import jProject.services.RoomTypeService;
import jProject.services.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    RoomTypeService roomTypeService;

    @Autowired
    UserService userService;


    @GetMapping("/reservation/create")
    public String bookRoomGet(Model model) {
        model.addAttribute("reservationForm", new ReservationForm());

        return "reservationCreate";
    }

    @PostMapping("/reservation/create")
    public String bookRoomPost(@ModelAttribute("reservationForm") ReservationForm reservationForm, @RequestParam int chosenRoom,  Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        UserData userData = userService.getUserId(userDetail.getUsername());

        reservationForm.setUser(userData);
        reservationForm.setChosenRoom(roomTypeService.findById(chosenRoom));
        HttpSession session = httpServletRequest.getSession();

        if(!reservationForm.AreDatesCorrect()) {
            model.addAttribute("dateError", true);
            model.addAttribute("result", session.getAttribute("matchingRoomTypes"));
            return "reservationCreate";
        }

        if(reservationService.checkReservations(reservationForm.getChosenRoom().getId(), reservationForm.getReservationStart(), reservationForm.getReservationEnd())<reservationForm.getChosenRoom().getRoomCount()) {
            Reservation reservation = reservationService.addNewReservation(reservationForm);

           //Calculate the amount of money for the stay
            String amount = String.valueOf(reservationService.getDays(reservation.getId())*reservationForm.getChosenRoom().getPrice());

            redirectAttributes.addFlashAttribute("amount",amount );

            session.setAttribute("amount",amount);
            session.setAttribute("reservationId",reservation.getId());
            return "redirect:/checkouts";

        }
        else  redirectAttributes.addFlashAttribute("message",
                "That room has been taken for the chosen period. Please change ether the room or the period");

        return "redirect:/reservation/search";
    }


    @GetMapping("/admin/reservations")
    public String allReservationsGet(Model model) {
        model.addAttribute("allReservations", reservationService.getAllReservations());
        return "reservations";
    }


    @GetMapping("/reservation/my")
    public String userReservationsGet(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();

        model.addAttribute("allReservations", reservationService.getReservationsById((int)session.getAttribute("UserId")));

        return "myReservations";
    }







    @GetMapping("/admin/reservation/edit")
    public String editReservationGet( @RequestParam int reservationId,  Model model) {

    Reservation reservationEdit = reservationService.findById(reservationId);
    model.addAttribute("editReservation",reservationEdit);

        return "editReservation";
    }



    @PostMapping("/admin/reservation/edit")
    public String roomEditPost(@ModelAttribute("editReservation")  Reservation reservationEdit, Model model, RedirectAttributes redirectAttributes) {

        Reservation reservation = reservationService.findById(reservationEdit.getId());
        reservation.setStatus(reservationEdit.getStatus());
        reservation.setReservationStart(reservationEdit.getReservationStart());
        reservation.setReservationEnd(reservationEdit.getReservationEnd());
        Reservation reservationFinal = reservationService.Update(reservation);

        redirectAttributes.addFlashAttribute("message",
                "You successfully edited that reservation!");

        return "redirect:/admin/reservations";
    }



    @GetMapping("/reservation/search")
    public String searchRoomGet( Model model, RedirectAttributes redirectAttributes) {

        List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
        if (roomTypes.isEmpty())
        {
            redirectAttributes.addFlashAttribute("message", "No rooms found!" );

            return "redirect:/";
        }

        AllRoomTypes allRoomTypes = new AllRoomTypes();
        for(RoomType roomType : roomTypes) {
            allRoomTypes.AddBedTypes(roomType.getBedTypes());
            allRoomTypes.AddPersonCount(roomType.getPersonCount());
            allRoomTypes.AddView(roomType.getView());
        }

        model.addAttribute("searchForm", new SearchRoomForm());
        model.addAttribute("roomsData",allRoomTypes);

        return "searchRoom";
    }



    @PostMapping("/reservation/search")
    public String searchRoomPost(@ModelAttribute("searchForm")  SearchRoomForm searchRoomForm, Model model, RedirectAttributes redirectAttributes,HttpServletRequest httpServletRequest) {

        if(searchRoomForm.getBedTypes().contains("-"))
            searchRoomForm.setBedTypes(null);
        if(searchRoomForm.getView().contains("-"))
            searchRoomForm.setView(null);
        List<RoomType> roomType = roomTypeService.findByUserCriteria(searchRoomForm);

        if(roomType.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No results found!" );

            return "redirect:/reservation/search";
        }


        for (int i=0; i<roomType.size();i++)
            Hibernate.initialize(roomType.get(i).getImages());

        redirectAttributes.addFlashAttribute("result", roomType );
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("matchingRoomTypes",roomType);


        return "redirect:/reservation/create";

    }



}
