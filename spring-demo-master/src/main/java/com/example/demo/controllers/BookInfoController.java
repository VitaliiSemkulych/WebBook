package com.example.demo.controllers;

import com.example.demo.dto.FrontendPropertiesDTO;
import com.example.demo.dto.SearchByPhraseDTO;
import com.example.demo.enums.BookmarkType;
import com.example.demo.model.FileInfo;
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
import java.security.Principal;

// controller which work with single book page
@Controller
@AllArgsConstructor
public class BookInfoController {

    private final MarkService markService;
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final BookService bookService;
    private final CommentService commentService;




    @GetMapping(value = "/book/{bookId}")
    public String getBookInfo(HttpSession session,Model model,
                              @PathVariable("bookId") long bookId, Principal principal){
        session.setAttribute("frontendProperties",
                FrontendPropertiesDTO.getFrontendProperties("",' ',
                        new SearchByPhraseDTO(),false,false,
                        false,false,
                        false,false));
        session.setAttribute("modifyRecensionId",-1);
        model.addAttribute("book",bookService.getBookById(bookId));
        model.addAttribute("inReadingMode",
                bookmarkService.isBookBookmark(bookId,principal.getName(),BookmarkType.READING));
        model.addAttribute("inInterestingMode",
                bookmarkService.isBookBookmark(bookId,principal.getName(),BookmarkType.INTERESTING));
        model.addAttribute("inReadMode",
                bookmarkService.isBookBookmark(bookId,principal.getName(),BookmarkType.READ));
        model.addAttribute("isEvaluated",markService.isBookEvaluated(bookId,principal.getName()));
        model.addAttribute("markOptionsList", markService.getMarkOptionList());
        model.addAttribute("bookMark",markService.getAverageBookMark(bookId));
        model.addAttribute("recensionList",commentService.getBookComments(bookId));
        return "singleBookPage";
    }

    @PostMapping(value = "/evaluate/{bookId}")
    public String evaluate(@PathVariable("bookId") long bookId,@RequestParam("markValue") int mark, Principal principal){
        markService.insertMark(bookService.getBookById(bookId),
                userService.getUserByEmail(principal.getName()),mark);
        return "redirect:/book/"+bookId;
    }


    @GetMapping(value = "/changeBookmarkType/{bookId}/{bookmarkType}")
    public String changeBookmarkType(@PathVariable("bookId") long bookId,Principal principal,
            @PathVariable("bookmarkType") String bookmarkType){
        if (bookmarkService.isBookBookmark(bookId, principal.getName(), BookmarkType.fromType(bookmarkType))) {
            bookmarkService.deleteBookFromBookmark(bookId, principal.getName(), BookmarkType.fromType(bookmarkType));
        } else {
            bookmarkService.addBookToBookmark(bookService.getBookById(bookId),
                    userService.getUserByEmail(principal.getName()),
                    BookmarkType.fromType(bookmarkType));
        }

        return "redirect:/book/"+bookId;
    }


    //function execute download book functionality
    @GetMapping("/getContent/{bookId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("disposition") String disposition,
                                                          @PathVariable("bookId") long bookId) {
        FileInfo content = bookService.getBookById(bookId).getContent();
        byte[] data = bookService.getContent(content);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition+";filename=" + content.getName()+".pdf")
                // Content-Type
                .contentType(MediaType.APPLICATION_PDF) //
                // Content-Length
                .contentLength(data.length) //
                .body(resource);
    }

    //set recension in modify mode
    @GetMapping("/modifyMode/{bookId}")
    public String moveToModifyMode(HttpSession session,
                                   @PathVariable("bookId") long bookId,
                                   @RequestParam("recensionId") long recensionId){
        session.setAttribute("modifyRecensionId",recensionId);
        return "redirect:/book/"+bookId;
    }

    //reset recension from modify mode without modification
    @GetMapping("/cancelModifyMode")
    public String cancelModifyMode(HttpSession session){
        session.setAttribute("modifyRecensionId",-1);
        return "redirect:/book";
    }

    //execute recension modification
    @PostMapping("/acceptModification/{bookId}")
    public String acceptModification(HttpSession session,@PathVariable("bookId") long bookId,
                                     @RequestParam("recensionId") long recensionId,
                                     @RequestParam("modifyRecensionText") String recensionText){
        commentService.modifyComment(recensionId,recensionText);
        session.setAttribute("modifyRecensionId",-1);
        return "redirect:/book/"+bookId;
    }

    //delete selected user recension
    @GetMapping("/deleteRecension/{bookId}")
    public String deleteComment(@PathVariable("bookId") long bookId,
                                  @RequestParam("recensionId") long recensionId){

        commentService.deleteComment(recensionId);
        return "redirect:/book/"+bookId;
    }

    //add recension written by user
    @PostMapping("/sendRecension/{bookId}")
    public String sendComment(Principal principal,@PathVariable("bookId") long bookId,
                                @RequestParam("recensionText") String recensionText){
        commentService.sendComment(recensionText,bookId,principal.getName());
        return "redirect:/book/"+bookId;
    }

}
