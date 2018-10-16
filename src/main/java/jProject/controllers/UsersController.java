package jProject.controllers;


import jProject.forms.LoginForm;
import jProject.forms.RegisterForm;

import jProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;



@Controller
public class UsersController {


    @Autowired
    private UserService userService;



    @GetMapping("/register")
    public String registerForm(Model model) {
       model.addAttribute("RegForm", new RegisterForm());
        return "register";
    }


    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("RegForm") @Valid RegisterForm RegForm, BindingResult bindingResult, Model model, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "register";
        }
        if(userService.isUnique(RegForm.getEmail())) {
            model.addAttribute("emailUnique", true);
            return "register";
        }
        if(!RegForm.getPassword().equals(RegForm.getConfirmPassword())) {
            model.addAttribute("passwordError", true);
            return "register";
        }

        userService.addNewUser(RegForm);

        return loging_method(model, new LoginForm (RegForm.getEmail(), RegForm.getPassword()), request);
    }


    @GetMapping("/admin")
    public String adminMain (){
        return "/admin/greeting";
    }


    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        redirectAttributes.addAttribute("logout",true);
        return "redirect:/login";
    }



    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("logForm", new RegisterForm());

        return "login";

    }


    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(Model model,HttpServletRequest request,@ModelAttribute("logForm") @Valid LoginForm logForm, BindingResult bindingResult)
    {

        if (bindingResult.hasErrors()) {
            model.addAttribute("logForm",logForm);
            return "login";
        }
        model.addAttribute("logForm",logForm);

        return this.loging_method(model, logForm, request);
        //"index";
    }


    private String loging_method(Model model, LoginForm logForm, HttpServletRequest request ) {

        try {

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(logForm.getEmail(), logForm.getPassword());

            // Authenticate the user

            Authentication authentication = authenticationManager.authenticate(authRequest);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            // Create new session and add security context.
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("UserId",userService.getUserId(logForm.getEmail()).getId());

        } catch (Exception h) {
            model.addAttribute("loginError", true);
            return "login";
        }

        return "redirect:/";
    }
}