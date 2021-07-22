package com.example.demo.controllers;

import com.example.demo.dto.SearchByPhraseDTO;
import com.example.demo.dto.UserRegisterFormDTO;
import com.example.demo.exception.enrollExeption.BadActivationCodeException;
import com.example.demo.character.Letter;
import com.example.demo.service.GenreService;
import com.example.demo.service.UserService;
import com.example.demo.service.security.UserEnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

//controller which works with user log in ang registration functionality
@Controller
@AllArgsConstructor
public class LoginPageController {

    private final UserEnrollmentService userEnrollmentService;
    private final UserService userService;
    private final GenreService genreService;




    //rewrite
    //method will be called after start application
    @GetMapping(value = {"/","/LoginPage"})
    public String index(Model model) {
        ModelAndView modelAndView=new ModelAndView();
        model.addAttribute("user",new UserRegisterFormDTO());
        return "LoginPage";
    }

    // method called after log in user to the account
    @GetMapping("/loginSuccess")
    public String login(Principal principal,HttpSession session,Model model,RedirectAttributes redirectAttributes) {
        String userEmail = principal.getName();
        session.setAttribute("loginUser",userService.getUserInfo(userEmail));
        session.setAttribute("genreList",genreService.getGenres());
        session.setAttribute("letterList", Letter.getLetterList());
        session.setAttribute("searchByPhrase",new SearchByPhraseDTO());
        redirectAttributes.addAttribute("genreName", "ALL GENRES");
//        redirectAttributes.addAttribute("id", 17);
        return "redirect:/searchByGenre";
    }

    //method will be called after user log out from their account
    @GetMapping("/logoutSuccessful")
    public String logoutSuccessfulPage(HttpSession session) {
        SecurityContextHolder.clearContext();
        session.invalidate();
        return "redirect:/";
    }


    @PostMapping("/enroll")
    public String enroll (Model model, @Valid @ModelAttribute("user") UserRegisterFormDTO enrolledUser, BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            final String message = "Registration successful."
                    + "<br> Please login for enter.";
            userEnrollmentService.enroll(enrolledUser);
            model.addAttribute("enrollMassage", message);
        }
        return "LoginPage";
    }

    @GetMapping("/activate")
    public String activate(Model model,@RequestParam String activationCode) {
        try {
            userEnrollmentService.activateUser(activationCode);
            model.addAttribute("accountActivationMessage","User activated");
        } catch (BadActivationCodeException e) {
            model.addAttribute("accountActivationMessage","Incorrect activation code");
        }
        return "User activated";
    }


    /* redirect to 403 error page in case if user don't have authority permission
     * for using some resource or moving to some page
     */
    @GetMapping("/403")
    public String accessDenied(Model model, Optional<Principal> principal) {
        final String principleName=principal.isPresent()?principal.get().getName():"unsigned user";
        final String message = "Hi " +  principleName
                + "<br> You do not have permission to access this page!";
        model.addAttribute("message", message);
        return "403Page";
    }

}
