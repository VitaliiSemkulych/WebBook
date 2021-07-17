package com.example.demo.controllers;

import com.example.demo.dto.SearchByPhraseModel;
import com.example.demo.dto.UserRegisterForm;
import com.example.demo.dao.GenreDAO;
import com.example.demo.dao.UserRegistrFormDAO;
import com.example.demo.letter.Letter;
import com.example.demo.validator.UserRegistrValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

//controller which works with user log in ang registration functionality
@Controller
public class LoginPageController {

    @Autowired
    private UserRegistrFormDAO userRegistrFormDAO;

    @Autowired
    private UserRegistrValidator userRegistrValidator;

    @Autowired
    private GenreDAO genreDAO;

//initialization binder where will be result of validation user data before registration or log in
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == UserRegisterForm.class) {
            dataBinder.setValidator(userRegistrValidator);
        }

    }


    //method will be called after start application
    @RequestMapping(value = {"/","/LoginPage"} , method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("user",new UserRegisterForm());
        modelAndView.setViewName("LoginPage.html");

        return modelAndView;
    }

    // method called after log in user to the account
    @RequestMapping(value = "/loginSuccess" , method = RequestMethod.GET)
    public String login(Principal principal,HttpSession session,Model model,RedirectAttributes redirectAttributes) {
        String userEmail = principal.getName();
        session.setAttribute("lodinUser",userRegistrFormDAO.findUserAccount(userEmail));
        session.setAttribute("genreList",genreDAO.getGenreList());
        session.setAttribute("letterList", Letter.getLetterList());
        model.addAttribute("searchByPhrace",new SearchByPhraseModel());
        redirectAttributes.addAttribute("id", 17);
        return "redirect:/searchByGenre";
    }

    //method will be called after user log out from their account
    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    // method process registration new users to the system
    @RequestMapping(value = "/registrate" ,  method = RequestMethod.POST)
    public String registrate(Model model, @Valid @ModelAttribute("user") UserRegisterForm rUser, BindingResult bindingResult) {

        userRegistrValidator.validate(rUser,bindingResult);
        if (!bindingResult.hasErrors()) {
            userRegistrFormDAO.addUserAccount(rUser);
            String message = "Registration successful."
                    + "<br> Please login for enter.";
            model.addAttribute("registMessage",message);
        }

        return "LoginPage";
    }

    /* redirect to 403 error page in case if user don't have authority permission
     * for using some resource or moving to some page
     */
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }

}
