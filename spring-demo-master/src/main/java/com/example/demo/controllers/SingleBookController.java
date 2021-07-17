package com.example.demo.controllers;

import com.example.demo.model.Mark;
import com.example.demo.dto.SearchByPhraseModel;
import com.example.demo.dto.UserRegisterForm;
import com.example.demo.dao.BookDAO;
import com.example.demo.dao.RecensionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// controller which work with single book page
@Controller
public class SingleBookController {

    @Autowired
    private RecensionDAO recencionDAO;

    @Autowired
    private BookDAO bookDAO;

    //load single book page
    @GetMapping(value = "/singleBook")
    public String openBook(RedirectAttributes redirectAttributes, @RequestParam("id") long bookId, HttpSession session){
        session.setAttribute("settingsLink",false);
        session.setAttribute("userPageLink",false);
        session.setAttribute("letterId",(char)' ');
        session.setAttribute("genreId",(long)-1);
        session.setAttribute("modifyRecensionId",-1);
        redirectAttributes.addAttribute("id",bookId);
        return "redirect:/book";
    }

    // set correspond attribute before log in selected book page
    @GetMapping(value = "/book")
    public String insertBookInfo(Model model, @RequestParam("id") long bookId,HttpSession session){
        model.addAttribute("searchByPhrace",new SearchByPhraseModel());
        model.addAttribute("book",bookDAO.selectBookByID(String.valueOf(bookId)));

        if(bookDAO.getReadingModeNumber(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail())!=0){
        model.addAttribute("inReadingMode",true);
        }else{
            model.addAttribute("inReadingMode",false);
        }
        if(bookDAO.getInterestingModeNumber(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail())!=0){
            model.addAttribute("inInterestingMode",true);
        }else{
            model.addAttribute("inInterestingMode",false);
        }
        if(bookDAO.getReadModeNumber(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail())!=0){
            model.addAttribute("inReadMode",true);
        }else{
            model.addAttribute("inReadMode",false);
        }
        if(bookDAO.getMarkNumber(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail())!=0){

            model.addAttribute("isEvaluated",true);
        }else{

            model.addAttribute("isEvaluated",false);

        }
        model.addAttribute("marckOptionsList", IntStream.rangeClosed(1, 5)
                .boxed().collect(Collectors.toList()));
        List<Mark> markList =bookDAO.getMarckList(bookId);
        if (markList.isEmpty()){
            model.addAttribute("bookMarck","Not evaluated yet");
        }else {
            int summ= markList.stream().mapToInt(o->o.getMark()).sum();
            model.addAttribute("bookMarck",String.valueOf(new DecimalFormat("#.##")
                    .format(summ/ markList.size())));

        }
        model.addAttribute("recensionList",recencionDAO.getRecensionList(bookId));

        return "singleBookPage";
    }
    // Add or delete book in interesting bookmarks
    @GetMapping(value = "/changeInterestingMode")
    public String changeInterestingMode(RedirectAttributes redirectAttributes, @RequestParam("id") long bookId, HttpSession session){

        if(bookDAO.getInterestingModeNumber(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail())!=0){
            bookDAO.delateBookInInterestingMode(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail());
        }else{
            bookDAO.addBookInInterestingMode(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail());
        }
        redirectAttributes.addAttribute("id",bookId);
        return "redirect:/book";
    }
    // Add or delete book in reading bookmarks
    @GetMapping(value = "/changeReadingMode")
    public String changeReadingMode(RedirectAttributes redirectAttributes, @RequestParam("id") long bookId, HttpSession session){

        if(bookDAO.getReadingModeNumber(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail())!=0){
            bookDAO.delateBookInReadingMode(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail());
        }else{
            bookDAO.addBookInReadingMode(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail());
        }
        redirectAttributes.addAttribute("id",bookId);
        return "redirect:/book";
    }
    // Add or delete book in read bookmarks
    @GetMapping(value = "/changeReadMode")
    public String changeReadMode(RedirectAttributes redirectAttributes, @RequestParam("id") long bookId, HttpSession session){

        if(bookDAO.getReadModeNumber(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail())!=0){
            bookDAO.delateBookInReadMode(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail());
        }else{
            bookDAO.addBookInReadMode(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail());
        }
        redirectAttributes.addAttribute("id",bookId);
        return "redirect:/book";
    }
    // book evaluation
    @PostMapping(value = "/evaluate")
    public String evaluate(RedirectAttributes redirectAttributes, @RequestParam("id") long bookId,@RequestParam("marckValue") int marck, HttpSession session){
            bookDAO.updateMark(bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail(),marck);
        redirectAttributes.addAttribute("id",bookId);
        return "redirect:/book";
    }

    //function execute download book functionality
    @GetMapping("/getContent")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("disposition") String disposition,@RequestParam("id") long bookId) throws IOException {
        byte[] data = bookDAO.getContent(bookId);
        String bookName = bookDAO.selectBookByID(String.valueOf(bookId)).getBookName();
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
    @GetMapping("/modifyMode")
    public String moveToModufyMode(HttpSession session,RedirectAttributes redirectAttributes,@RequestParam("id") long bookId,@RequestParam("recensionId") long recensionId){

        session.setAttribute("modifyRecensionId",recensionId);
        redirectAttributes.addAttribute("id",bookId);
        return "redirect:/book";
    }

    //reset recension from modify mode without modification
    @GetMapping("/cancelModifyMode")
    public String cancelModufyMode(HttpSession session,RedirectAttributes redirectAttributes,@RequestParam("id") long bookId){

        session.setAttribute("modifyRecensionId",-1);
        redirectAttributes.addAttribute("id",bookId);
        return "redirect:/book";
    }

    //execute recension modification
    @PostMapping("/acceptModification")
    public String acceptModification(HttpSession session,RedirectAttributes redirectAttributes,@RequestParam("id") long bookId,
                                     @RequestParam("recensionId") long recensionId,
                                     @RequestParam("modifyRecensionText") String recensionText){

        recencionDAO.modifyRecension(recensionId,recensionText);
        session.setAttribute("modifyRecensionId",-1);
        redirectAttributes.addAttribute("id",bookId);
        return "redirect:/book";
    }

    //delete selected user recension
    @GetMapping("/delateRecension")
    public String delateRecension(RedirectAttributes redirectAttributes,@RequestParam("id") long bookId,
                                  @RequestParam("recensionId") long recensionId){
        recencionDAO.delateRecension(recensionId);
        redirectAttributes.addAttribute("id",bookId);
        return "redirect:/book";
    }

    //add recension written by user
    @PostMapping("/sendRecension")
    public String sendRecension(HttpSession session,RedirectAttributes redirectAttributes,
                                @RequestParam("id") long bookId,
                                @RequestParam("recensionText") String recensionText){
        recencionDAO.sendRecension(recensionText,bookId,((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail());
        redirectAttributes.addAttribute("id",bookId);
        return "redirect:/book";
    }

}
