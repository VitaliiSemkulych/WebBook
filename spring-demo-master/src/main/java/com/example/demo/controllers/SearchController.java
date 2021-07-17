package com.example.demo.controllers;


import com.example.demo.model.Book;
import com.example.demo.dto.SearchByPhraseModel;
import com.example.demo.dto.UserRegisterForm;
import com.example.demo.dao.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/*
* Controller which work with whole search functionality in application
* */
@Controller

public class SearchController {

    @Autowired
    private BookDAO bookDAO;



   // number book which will be show on page
    private static final int numberBookOnPage = 3;
    //number of searched books
    private long searchedBooksNumber;
    private List<Integer> pageNumbers = new ArrayList<Integer>();
    //array with page nambers which wiil be showed on the page
    private List<Integer> showedPageNumbers = new ArrayList<Integer>();
    // list contains books which wiil be showedon the page
    private List<Book> currentSearchList_Page = new ArrayList<Book>();
    // List will contains all books after searching books by somevalue
    private List<Book> currentSearchList_Full=new ArrayList<>();

    //method will be called after using search be author name or search by book name functionality.
    @RequestMapping(value = "/searchByPhrace" , method = RequestMethod.POST)
    public String searchByPhrace(HttpSession session, @ModelAttribute("searchFrace") SearchByPhraseModel searchFrace,RedirectAttributes redirectAttributes){
        currentSearchList_Full=bookDAO.selectByFrace(searchFrace);
        searchedBooksNumber=currentSearchList_Full.size();
        session.setAttribute("searchByPhrace",searchFrace);
        session.setAttribute("letterId",(char)' ');
        session.setAttribute("genreId",(long)-1);
        redirectAttributes.addAttribute("page", 1);
        redirectAttributes.addAttribute("pageName", "BookMain.html");
        return  "redirect:/selectPage";
    }

    //method will be called when user start search by genre.
    @GetMapping(value = "/searchByGenre" )
    public String searchByGenre(HttpSession session, @RequestParam("id") long id,RedirectAttributes redirectAttributes){
        currentSearchList_Full=bookDAO.selectByGenreID(String.valueOf(id));
        searchedBooksNumber=currentSearchList_Full.size();
        session.setAttribute("genreId",id);
        session.setAttribute("letterId",(char)' ');
        session.setAttribute("searchByPhrace",new SearchByPhraseModel());
        redirectAttributes.addAttribute("page", 1);
        redirectAttributes.addAttribute("pageName", "BookMain.html");
        return "redirect:/selectPage";
    }
    //method will be called when user start search by first letterof book name.
    @GetMapping(value = "/searchByLetter" )
    public String searchByLetter( @RequestParam("id") Character id,HttpSession session,RedirectAttributes redirectAttributes){
        currentSearchList_Full=bookDAO.selectByLetter(String.valueOf(id));
        searchedBooksNumber=currentSearchList_Full.size();
        session.setAttribute("genreId",(long)-1);
        session.setAttribute("searchByPhrace",new SearchByPhraseModel());
        session.setAttribute("letterId",id);
        redirectAttributes.addAttribute("page", 1);
        redirectAttributes.addAttribute("pageName", "BookMain.html");

        return  "redirect:/selectPage";
    }


    //method will be called when user change selected page.
    @GetMapping(value = "/selectPage" )
    public String selectPage(HttpSession session,@RequestParam("page") int currentPage,Model model,@RequestParam("pageName") String pageName) {
        fillPageNumberArray(currentSearchList_Full.size(),numberBookOnPage,currentPage);
        preparePageList(currentPage);
        model.addAttribute("searchByPhrace",session.getAttribute("searchByPhrace"));
        model.addAttribute("searchBookNumber",searchedBooksNumber);
        model.addAttribute("selectedPage",currentPage);
        model.addAttribute("pageNumbrs",showedPageNumbers);
        currentSearchList_Page.forEach(book -> book.setImage(bookDAO.getImage(book.getId())));
        model.addAttribute("bookList",currentSearchList_Page);
        session.setAttribute("settingsLink",false);
        if(!pageName.equals("userPage")){
            session.setAttribute("userPageLink",false);
        }

        return pageName;
    }

    //method will be called when user select previousPage button.
    @GetMapping(value = "/previousPage" )
    public String previousPage(@RequestParam("page") int currentPage,@RequestParam("pageName") String pageName,RedirectAttributes redirectAttributes) {
        if (currentPage > 1) {
            currentPage--;
        } else {
            currentPage = pageNumbers.size();

        }
        redirectAttributes.addAttribute("page", currentPage);
        redirectAttributes.addAttribute("pageName", pageName);
        return "redirect:/selectPage";

    }

    //method will be called when user select nextPage button.
    @GetMapping(value = "/nextPage" )
    public String nextPage(@RequestParam("page") int currentPage,@RequestParam("pageName") String pageName,RedirectAttributes redirectAttributes) {
        if (currentPage < pageNumbers.size()) {
            currentPage++;
        } else {
            currentPage = 1;
        }
        redirectAttributes.addAttribute("page", currentPage);
        redirectAttributes.addAttribute("pageName", pageName);
        return "redirect:/selectPage";
    }

    //method search book which marked by user as reading
    @GetMapping(value = "/readingPage" )
    public String readingMode(HttpSession session,RedirectAttributes redirectAttributes){
        session.setAttribute("tab","reading");
        currentSearchList_Full=bookDAO.selectReadingBookByUserID(((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail());
        searchedBooksNumber=currentSearchList_Full.size();
        session.setAttribute("genreId",(long)-1);
        session.setAttribute("letterId",(char)' ');
        redirectAttributes.addAttribute("page", 1);
        redirectAttributes.addAttribute("pageName", "userPage");
        session.setAttribute("settingsLink",false);
        session.setAttribute("userPageLink",true);
        session.setAttribute("searchByPhrace",new SearchByPhraseModel());
     return "redirect:/selectPage";
    }

    //method search book which marked by user as interesting
    @GetMapping(value = "/interestingPage" )
    public String interestingMode(HttpSession session,RedirectAttributes redirectAttributes){
        session.setAttribute("tab","interesting");
        currentSearchList_Full=bookDAO.selectInterestingBookByUserID(((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail());
        searchedBooksNumber=currentSearchList_Full.size();
        redirectAttributes.addAttribute("page", 1);
        redirectAttributes.addAttribute("pageName", "userPage");
        session.setAttribute("settingsLink",false);
        session.setAttribute("userPageLink",true);

        return "redirect:/selectPage";
    }

    //method search book which marked by user as read
    @GetMapping(value = "/readPage" )
    public String readMode(HttpSession session,RedirectAttributes redirectAttributes){
        session.setAttribute("tab","read");
        currentSearchList_Full=bookDAO.selectReadedBookByUserID(((UserRegisterForm)session.getAttribute("lodinUser")).getUserEmail());
        searchedBooksNumber=currentSearchList_Full.size();
        redirectAttributes.addAttribute("page", 1);
        redirectAttributes.addAttribute("pageName", "userPage");
        session.setAttribute("settingsLink",false);
        session.setAttribute("userPageLink",true);

        return "redirect:/selectPage";
    }


    //filling array with page numbers after user execute some searching functionality
    public void fillPageNumberArray(long totalBooksCount, int booksCountOnPage,int currentPage) {
        int pageCount = 0;
        if (totalBooksCount > 0) {
            if (totalBooksCount % (float) booksCountOnPage == 0) {
                pageCount = (int) (totalBooksCount / booksCountOnPage);
            } else {
                pageCount = (int) (totalBooksCount / booksCountOnPage) + 1;
            }
        }
        pageNumbers.clear();
        for (int i = 1; i <= pageCount; i++) {
            pageNumbers.add(i);
        }
        fillShowedPageNumberArray(currentPage);
    }

    // filling array with page numbers which will be showed on page after user execute some searching functionality
    public void fillShowedPageNumberArray(int currentPage) {
        showedPageNumbers.clear();
        if (pageNumbers.size() > 4) {
            if (currentPage == 1 || currentPage == 2) {
                for (int i = 0; i < 5; i++) {
                    showedPageNumbers.add(pageNumbers.get(i));
                }
            } else if (currentPage == pageNumbers.size() || currentPage == pageNumbers.size() - 1) {
                for (int i = pageNumbers.size() - 5; i < pageNumbers.size(); i++) {
                    showedPageNumbers.add(pageNumbers.get(i));
                }
            } else {
                for (int i = currentPage - 3; i < currentPage + 2; i++) {
                    showedPageNumbers.add(pageNumbers.get(i));
                }
            }
        } else {
            for(int i = 0; i < pageNumbers.size(); i++) {
                showedPageNumbers.add(pageNumbers.get(i));
            }

        }

    }
    //creating list of book which will be show on selected page
    public void preparePageList(int currentPage) {
        currentSearchList_Page.clear();
        int firstBookNumber = numberBookOnPage * currentPage - numberBookOnPage;
        long lastBookNumber = 0;
        if (firstBookNumber + numberBookOnPage > searchedBooksNumber) {
            lastBookNumber = searchedBooksNumber;
        } else {
            lastBookNumber = firstBookNumber + numberBookOnPage;
        }
        for (int i = firstBookNumber; i < lastBookNumber; i++) {
            currentSearchList_Page.add(currentSearchList_Full.get(i));
        }

    }



}
