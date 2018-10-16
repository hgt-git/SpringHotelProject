package jProject.controllers;

import jProject.forms.RoomForm;
import jProject.models.RoomType;
import jProject.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping(value = "/admin")
@Controller
public class RoomManageController {

    @Autowired
    RoomTypeService roomTypeService;

    @GetMapping("/rooms/create")
    public String roomCreateGet(Model model) {
        model.addAttribute("roomForm", new RoomForm());
        return "newRoomType";
    }


    @PostMapping("/rooms/create")
    public String roomCreatePost(@ModelAttribute("roomForm") @Valid RoomForm roomForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            return "newRoomType";
        }


        RoomType roomType = roomTypeService.addNewRoomType(roomForm);

        redirectAttributes.addFlashAttribute("roomTypeId",
                roomType.getId());

        return "redirect:/admin/files/";
    }


    @GetMapping("/rooms")
    public String roomAllGet(Model model){

        model.addAttribute("allRoom", roomTypeService.getAllRoomTypes());
        return "rooms";
    }



    @GetMapping("/rooms/edit")
    public String roomEditGet(@RequestParam String idEdit, Model model){

        RoomType roomTypeEdit = roomTypeService.findById(Integer.parseInt(idEdit));
        model.addAttribute("editRoom",roomTypeEdit);
        return "editRoomType";

    }


    @PostMapping("/rooms/edit")
    public String roomEditPost(@ModelAttribute("editRoom") @Valid RoomType roomEdit, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("editRoom", roomEdit);
            return "newRoomType";
        }

        roomTypeService.Update(roomEdit);

        redirectAttributes.addFlashAttribute("roomTypeId",
                "You successfully edited that room !");

        return "redirect:/admin/rooms";
    }




}
