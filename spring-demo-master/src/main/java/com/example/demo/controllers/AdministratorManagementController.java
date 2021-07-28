package com.example.demo.controllers;

import com.example.demo.dto.BookInfoDTO;
import com.example.demo.dto.BookRequestDTO;
import com.example.demo.dto.FrontendPropertiesDTO;
import com.example.demo.dto.SearchByPhraseDTO;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.example.demo.service.GenreService;
import com.example.demo.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RolesAllowed("ROLE_ADMIN")
public class AdministratorManagementController {

    private final BookService bookService;
    private final GenreService genreService;
    private final PublisherService publisherService;
    private final AuthorService authorService;

    @GetMapping(value = "/switchToUpdate/{bookId}")
    public String switchToUpdateMode(HttpSession session, @PathVariable("bookId") long bookId){
        session.setAttribute("updateBookStatus",true);
        return "redirect:/book/"+bookId;
    }
    @GetMapping(value = "/cancelUpdate/{bookId}")
    public String cancelUpdateMode(HttpSession session, @PathVariable("bookId") long bookId){
        session.setAttribute("updateBookStatus",false);
        return "redirect:/book/"+bookId;
    }

    @GetMapping(value = "/addBook")
    public String selectAddBookPage(HttpSession session){
        session.setAttribute("frontendProperties",
                FrontendPropertiesDTO.getFrontendProperties("",' ',
                        new SearchByPhraseDTO(),false,false,
                        true,false,
                        false,false));
        return "addBook";
    }

    @PostMapping(value = "/addBook")
    @ResponseBody
    public ResponseEntity<?> addBook(@Valid @RequestBody BookRequestDTO book, BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            return bookService.addBook(book) ? ResponseEntity.ok("Book added") :
                    ResponseEntity.badRequest()
                            .body("Happens some problems with book adding." +
                                    "<br> Please contact with administrator fo handle it.");
        }else{
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

    }

    @DeleteMapping(value = "/deleteBook/{bookId}")
    @ResponseBody
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") long bookId){
        return bookService.deleteBook(bookId)?ResponseEntity.ok("Book deleted"):
                ResponseEntity.badRequest()
                        .body("Happens some problems with book removing." +
                                "<br> Please contact with administrator fo handle it.");

    }


    @PutMapping(value = "/updateBookInfo/{bookId}")
    @ResponseBody
    public ResponseEntity<?> updateBookInfo(HttpSession session, @PathVariable("bookId") long bookId,
                                            @Valid @RequestBody BookInfoDTO book, BindingResult bindingResult){
        session.setAttribute("updateBookStatus",false);
        if (!bindingResult.hasErrors()) {
        return bookService.updateBookInfo(bookId, book)?ResponseEntity.ok("Book info updated"):
                ResponseEntity.badRequest()
                        .body("Happens some problems with book info updating." +
                                "<br> Please contact with administrator fo handle it.");
        }else{
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

    }

    @PutMapping(value = "/updateBookFile/{bookId}")
    @ResponseBody
    public ResponseEntity<?> updateBookFile(HttpSession session, @PathVariable("bookId") long bookId,
                                            @RequestBody MultipartFile file){
        session.setAttribute("updateBookStatus",false);
        return bookService.updateBookFile(bookId, file)?ResponseEntity.ok("File updated"):
                ResponseEntity.badRequest()
                        .body("Happens some problems with file updating." +
                                "<br> Please contact with administrator fo handle it.");
    }

    @GetMapping(value = "/addGenre")
    public String selectAddGenrePage(HttpSession session, Model model){
        model.addAttribute("genres",genreService.getGenres());
        session.setAttribute("frontendProperties",
                FrontendPropertiesDTO.getFrontendProperties("",' ',
                        new SearchByPhraseDTO(),false,
                        false,false,
                        false,true,false));
        return "addGenre";
    }

    @PostMapping(value = "/addGenre")
    public String addGenre(@ModelAttribute String genreName){
        genreService.addGenre(genreName);
        return  "redirect:/addGenre";
    }

    @GetMapping(value = "/addPublisher")
    public String selectAddPublisherPage(HttpSession session, Model model){
        model.addAttribute("publishers",publisherService.getPublisher());
        session.setAttribute("frontendProperties",
                FrontendPropertiesDTO.getFrontendProperties("",' ',
                        new SearchByPhraseDTO(),false,
                        false,false,
                        false,false,true));
        return "addPublisher";
    }

    @PostMapping(value = "/addPublisher")
    public String addPublisher(@ModelAttribute String publisherName){
        publisherService.addPublisher(publisherName);
        return  "redirect:/addPublisher";
    }

    @GetMapping(value = "/addAuthor")
    public String selectAddAuthorPage(HttpSession session, Model model){
        model.addAttribute("authors",authorService.getAuthor());
        session.setAttribute("frontendProperties",
                FrontendPropertiesDTO.getFrontendProperties("",' ',
                        new SearchByPhraseDTO(),false,
                        false,false,
                        true,false,false));
        return "addAuthor";
    }

    @PostMapping(value = "/addAuthor")
    public String addAuthor(@ModelAttribute String authorName){
        authorService.addAuthor(authorName);
        return  "redirect:/addAuthor";
    }

    @PutMapping(value = "/updateGenre/{genreId}")
    @ResponseBody
    public ResponseEntity<?> updateGenre( @PathVariable("genreId") long genreId,@RequestBody String genreName) {
        return genreService.updateGenre(genreId,genreName)?ResponseEntity.ok("Genre updated"):
                ResponseEntity.badRequest()
                        .body("Happens some problems with genre updating." +
                                "<br> Please contact with administrator fo handle it.");
    }
    @DeleteMapping(value = "/deleteGenre/{genreId}")
    @ResponseBody
    public ResponseEntity<?> deleteGenre( @PathVariable("genreId") long genreId) {
            return genreService.deleteGenre(genreId)?ResponseEntity.ok("Genre deleted"):
                    ResponseEntity.badRequest()
                            .body("Happens some problems with genre deleting." +
                                    "<br> Please contact with administrator fo handle it.");
    }



    @PutMapping(value = "/updatePublisher/{publisherId}")
    @ResponseBody
    public ResponseEntity<?> updatePublisher( @PathVariable("publisherId") long publisherId,@RequestBody String publisherName) {
        return publisherService.updatePublisher(publisherId,publisherName)?ResponseEntity.ok("Publisher updated"):
                ResponseEntity.badRequest()
                        .body("Happens some problems with publisher updating." +
                                "<br> Please contact with administrator fo handle it.");
    }
    @DeleteMapping(value = "/deletePublisher/{publisherId}")
    @ResponseBody
    public ResponseEntity<?> deletePublisher( @PathVariable("publisherId") long publisherId) {
        return publisherService.deletePublisher(publisherId)?ResponseEntity.ok("Publisher deleted"):
                ResponseEntity.badRequest()
                        .body("Happens some problems with publisher deleting." +
                                "<br> Please contact with administrator fo handle it.");
    }



    @PutMapping(value = "/updateAuthor/{authorId}")
    @ResponseBody
    public ResponseEntity<?> updateAuthor( @PathVariable("authorId") long authorId,@RequestBody String authorName) {
        return authorService.updateAuthor(authorId,authorName)?ResponseEntity.ok("Author updated"):
                ResponseEntity.badRequest()
                        .body("Happens some problems with author updating." +
                                "<br> Please contact with administrator fo handle it.");
    }
    @DeleteMapping(value = "/deleteAuthor/{authorId}")
    @ResponseBody
    public ResponseEntity<?> deleteAuthor( @PathVariable("authorId") long authorId) {
        return authorService.deleteAuthor(authorId)?ResponseEntity.ok("author deleted"):
                ResponseEntity.badRequest()
                        .body("Happens some problems with author deleting." +
                                "<br> Please contact with administrator fo handle it.");
    }

}
