package com.example.demo.controllers;


import com.example.demo.dto.BookDTO;
import com.example.demo.dto.FrontendPropertiesDTO;
import com.example.demo.dto.SearchByPhraseDTO;
import com.example.demo.dto.SelectBookResponseDTO;
import com.example.demo.enums.BookmarkType;
import com.example.demo.enums.SelectBookType;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.BookmarkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/*
* Controller which work with whole search functionality in application
* */
@Controller
@AllArgsConstructor
public class SearchController {

    private final BookService bookService;
    private final BookmarkService bookmarkService;


    //method will be called after using search be author name or search by book name functionality.
    @RequestMapping(value = "/searchByPhrase/{page}" , method = RequestMethod.POST)
    public String searchByPhrase(Model model, HttpSession session, @PathVariable("page") int page,
                                 @ModelAttribute("searchPhrase") SearchByPhraseDTO searchPhrase){
        Page<BookDTO> bookPage = bookService.selectByPhrase(searchPhrase,page);

        model.addAttribute("bookSelectionResponse", SelectBookResponseDTO.getBookResponse(bookPage.getContent(),
                bookPage.getTotalElements(),bookPage.getTotalPages(),page));
        session.setAttribute("frontendProperties",FrontendPropertiesDTO.getFrontendProperties("",
                ' ', searchPhrase,false,false));
        return "BookMain";
    }

    //method will be called when user start search by genre.
    @GetMapping(value = "/searchByGenre/{genreName}/{page}" )
    public String searchByGenre(Model model, HttpSession session, @PathVariable("genreName") String genreName,
                                @PathVariable("page") int page){
        Page<BookDTO> bookPage = bookService.selectByGenre(genreName,page);

        model.addAttribute("bookSelectionResponse", SelectBookResponseDTO.getBookResponse(bookPage.getContent(),
                bookPage.getTotalElements(),bookPage.getTotalPages(),page));
        session.setAttribute("frontendProperties",FrontendPropertiesDTO.getFrontendProperties(genreName,' ',
                new SearchByPhraseDTO(),false,false));
        return "BookMain";
    }

    //method will be called when user start search by first letter of book name.
    @GetMapping(value = "/searchByCharacter/{character}/{page}" )
    public String searchByCharacter(Model model, @PathVariable("character") Character character,@PathVariable("page") int page,
                                    HttpSession session){
        Page<BookDTO> bookPage = bookService.selectByCharacter(character,page);

        model.addAttribute("bookSelectionResponse", SelectBookResponseDTO.getBookResponse(bookPage.getContent(),
                bookPage.getTotalElements(),bookPage.getTotalPages(),page));
        session.setAttribute("frontendProperties",FrontendPropertiesDTO.getFrontendProperties("",character,
                new SearchByPhraseDTO(),false,false));
        return "BookMain";
    }



    //method search book which marked by user as read
    @GetMapping(value = "/bookmark/{bookmarkType}/{page}" )
    public String bookmark(Model model, HttpSession session, Principal principal, @PathVariable("page") int page,
                           @PathVariable("bookmarkType") String bookmarkType){
        Page<BookDTO> bookPage = bookmarkService.selectBookmarkPage(principal.getName(), page,
                BookmarkType.fromType(bookmarkType));

        model.addAttribute("bookSelectionResponse", SelectBookResponseDTO.getBookResponse(bookPage.getContent(),
                bookPage.getTotalElements(),bookPage.getTotalPages(),page));
        session.setAttribute("frontendProperties",FrontendPropertiesDTO.getFrontendProperties("",
                ' ', new SearchByPhraseDTO(),false,true));
        session.setAttribute("dashboard",bookmarkType);
        return "userPage";
    }

    @GetMapping(value = "/selectBooks/{type}/{page}")
    public String selectBooks(Model model, HttpSession session, Principal principal,
                              @PathVariable("page") int page, @PathVariable("type") String selectType){
        FrontendPropertiesDTO frontendPropertiesDTO = (FrontendPropertiesDTO)session
                .getAttribute("frontendProperties");
        switch (SelectBookType.fromType(selectType)){
            case GENRE:
                return searchByGenre(model,session,frontendPropertiesDTO.getGenreName(),page);
            case CHARACTER:
                return searchByCharacter(model,frontendPropertiesDTO.getLetter(),page,session);
            case PHRASE:
                return searchByPhrase(model,session,page,frontendPropertiesDTO.getSearchByPhrase());
            case BOOKMARK:
                return  bookmark(model,session,principal,page,(String) session.getAttribute("dashboard"));
            default:
                return "403Page";
        }

    }


}
