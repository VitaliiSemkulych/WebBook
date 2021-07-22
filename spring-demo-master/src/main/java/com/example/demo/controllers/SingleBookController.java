package com.example.demo.controllers;

import com.example.demo.dto.FrontendPropertiesDTO;
import com.example.demo.dto.SearchByPhraseDTO;
import com.example.demo.enums.BookmarkType;
import com.example.demo.service.*;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

// controller which work with single book page
@Controller
@AllArgsConstructor
public class SingleBookController {

    private final MarkService markService;
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final BookService bookService;
    private final CommentService commentService;




    @GetMapping(value = "/book/{bookName}")
    public String insertBookInfo(HttpSession session,Model model, @PathVariable("bookName") String bookName, Principal principal){

        session.setAttribute("frontendProperties",
                FrontendPropertiesDTO.getFrontendProperties("",' ',
                        new SearchByPhraseDTO(),false,false));

        session.setAttribute("modifyRecensionId",-1);
        model.addAttribute("book",bookService.getBookByName(bookName));
        model.addAttribute("inReadingMode",
                bookmarkService.isBookBookmark(bookName,principal.getName(),BookmarkType.READING));
        model.addAttribute("inInterestingMode",
                bookmarkService.isBookBookmark(bookName,principal.getName(),BookmarkType.INTERESTING));
        model.addAttribute("inReadMode",
                bookmarkService.isBookBookmark(bookName,principal.getName(),BookmarkType.READ));

        model.addAttribute("isEvaluated",markService.isBookEvaluated(bookName,principal.getName()));
        model.addAttribute("markOptionsList", markService.getMarkOptionList());
        model.addAttribute("bookMark",markService.getAverageBookMark(bookName));
        model.addAttribute("recensionList",commentService.getBookComments(bookName));


        return "singleBookPage";
    }

    @PostMapping(value = "/evaluate/{bookName}")
    public String evaluate(@PathVariable String bookName,@RequestParam("markValue") int mark, Principal principal){
        markService.insertMark(bookService.getBookByName(bookName),
                userService.getUserByEmail(principal.getName()),mark);
        return "redirect:/book/"+bookName;
    }


    @GetMapping(value = "/changeBookmarkType/{bookName}/{bookmarkType}")
    public String changeBookmarkType(@PathVariable("bookName") String bookName,Principal principal,
            @PathVariable("bookmarkType") String bookmarkType){

        if (bookmarkService.isBookBookmark(bookName, principal.getName(), BookmarkType.fromType(bookmarkType))) {
            bookmarkService.deleteBookFromBookmark(bookName, principal.getName(), BookmarkType.fromType(bookmarkType));
        } else {
            bookmarkService.addBookToBookmark(bookService.getBookByName(bookName),
                    userService.getUserByEmail(principal.getName()),
                    BookmarkType.fromType(bookmarkType));
        }


        return "redirect:/book/"+bookName;
    }


    //Дописати
    //function execute download book functionality
    @GetMapping("/getContent/{bookName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("disposition") String disposition,@PathVariable("bookName") String bookName) throws IOException {
        byte[] data = bookService.getContent(bookName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition+";filename=" + bookName+".pdf")
                // Content-Type
                .contentType(MediaType.APPLICATION_PDF) //
                // Content-Lengh
                .contentLength(data.length) //
                .body(resource);
    }

    //set recension in modify mode
    @GetMapping("/modifyMode/{bookName}")
    public String moveToModifyMode(HttpSession session,
                                   @PathVariable("bookName") String bookName,
                                   @RequestParam("recensionId") long recensionId){
        session.setAttribute("modifyRecensionId",recensionId);
        return "redirect:/book/"+bookName;
    }

    //reset recension from modify mode without modification
    @GetMapping("/cancelModifyMode/{bookName}")
    public String cancelModifyMode(HttpSession session,@PathVariable("bookName") String bookName){
        session.setAttribute("modifyRecensionId",-1);
        return "redirect:/book";
    }

    //execute recension modification
    @PostMapping("/acceptModification/{bookName}")
    public String acceptModification(HttpSession session,@PathVariable("bookName") String bookName,
                                     @RequestParam("recensionId") long recensionId,
                                     @RequestParam("modifyRecensionText") String recensionText){
        commentService.modifyComment(recensionId,recensionText);
        session.setAttribute("modifyRecensionId",-1);
        return "redirect:/book/"+bookName;
    }

    //delete selected user recension
    @GetMapping("/deleteRecension/{bookName}")
    public String deleteComment(@PathVariable("bookName") String bookName,
                                  @RequestParam("recensionId") long recensionId){

        commentService.deleteComment(recensionId);
        return "redirect:/book/"+bookName;
    }

    //add recension written by user
    @PostMapping("/sendRecension/{bookName}")
    public String sendComment(Principal principal,@PathVariable("bookName") String bookName,
                                @RequestParam("recensionText") String recensionText){
        commentService.sendComment(recensionText,bookName,principal.getName());
        return "redirect:/book/"+bookName;
    }

}
