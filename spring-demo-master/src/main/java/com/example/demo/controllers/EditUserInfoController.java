package com.example.demo.controllers;

import com.example.demo.dto.SearchByPhraseModel;
import com.example.demo.dto.UserRegisterForm;
import com.example.demo.dao.EditPersonalAccountDAO;
import com.example.demo.validator.UserRegistrValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class EditUserInfoController {


    @Autowired
    private EditPersonalAccountDAO editPersonalAccountDAO;

    @Autowired
    private UserRegistrValidator userRegistrValidator;
    //initialization binder where will be result of validation user data before actualization information about user
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
    //method involve before load user upgrade settings page
    @GetMapping(value = "/userSettings")
    public String openBook(Model model,HttpSession session){
        model.addAttribute("user",session.getAttribute("lodinUser"));
        model.addAttribute("searchByPhrace",new SearchByPhraseModel());
        session.setAttribute("settingsLink",true);
        session.setAttribute("userPageLink",false);
        session.setAttribute("letterId",(char)' ');
        session.setAttribute("genreId",(long)-1);
        return "settings";
    }
    // process update information about user
    @PostMapping(value = "/changeInformation")
    public String updateInformation( Model model, @Valid @ModelAttribute("user") UserRegisterForm user,
                                     BindingResult bindingResult, HttpSession session) throws IOException {
        model.addAttribute("searchByPhrace",new SearchByPhraseModel());
        userRegistrValidator.validate(user,bindingResult);
        if(!bindingResult.hasErrors()){
            editPersonalAccountDAO.inputChanges(user);
            session.setAttribute("lodinUser",user);
            model.addAttribute("editMessage","Information udated!");
        }else{
            if(user.getImage().getBytes().length==0 && !bindingResult.hasFieldErrors("password")) {
                user.setImage(((UserRegisterForm)session.getAttribute("lodinUser")).getImage());
                editPersonalAccountDAO.inputChanges(user);
                session.setAttribute("lodinUser",user);
                model.addAttribute("editMessage","Information udated!");
            }
        }

        return "settings";
    }
}
