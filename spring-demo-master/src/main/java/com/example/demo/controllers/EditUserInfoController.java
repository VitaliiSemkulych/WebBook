package com.example.demo.controllers;

import com.example.demo.dto.*;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@AllArgsConstructor
public class EditUserInfoController {

    private final UserService userService;

    //method involve before load user upgrade settings page
    @GetMapping(value = "/userSettings")
    public String openBook(Model model,HttpSession session){
        model.addAttribute("user",session.getAttribute("loginUser"));
        session.setAttribute("frontendProperties",FrontendPropertiesDTO.getFrontendProperties("",' ',
                new SearchByPhraseDTO(),true,false));
        session.setAttribute("updateUserInfo",new UpdateUserInfoRequestDTO());
        session.setAttribute("updateUserImage",new UpdateUserImageRequestDTO());
        session.setAttribute("updateUserEmail",new UpdateUserEmailRequestDTO());
        return "settings";
    }

    @PostMapping("/updateUserInfo")
    public String updateUserInformation( Model model, @Valid @ModelAttribute("updateUserInfo") UpdateUserInfoRequestDTO user,
                                     BindingResult bindingResult, HttpSession session){
        if(!bindingResult.hasErrors()){
            userService.updateUserInfo(user);
            session.setAttribute("loginUser",user);
            model.addAttribute("editMessage","Information updated!");
        }else{
                model.addAttribute("editMessage","Updating rejected!");
        }
        return "settings";
    }

    @PostMapping("/updateUserEmail")
    public String updateUserEmail( Model model, @Valid @ModelAttribute("updateUserEmail") UpdateUserEmailRequestDTO user,
                                     BindingResult bindingResult, HttpSession session){
        if(!bindingResult.hasErrors()) {
            if (userService.updateUserEmail(user)){
                session.setAttribute("loginUser", user);
                model.addAttribute("editMessage", "Information updated!");
                return "settings";
            }
        }
            model.addAttribute("editMessage","Updating rejected!");
        return "settings";
    }

    @PostMapping("/updateUserImage")
    public String updateUserImage( Model model, @Valid @ModelAttribute("updateUserImage") UpdateUserImageRequestDTO user,
                                     BindingResult bindingResult, HttpSession session){
        if(!bindingResult.hasErrors()){
            if (userService.updateUserImage(user)) {
                session.setAttribute("loginUser", user);
                model.addAttribute("editMessage", "Information updated!");
                return "settings";
            }
        }
        model.addAttribute("editMessage","Updating rejected!");
        return "settings";
    }
}
