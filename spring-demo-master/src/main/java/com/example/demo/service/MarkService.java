package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.security.User;

import java.util.List;

public interface MarkService {
    void insertMark(Book bookByName, User userByEmail, int mark);
    boolean isBookEvaluated(long bookId, String userEmail);
    List<Integer> getMarkOptionList();
    String getAverageBookMark(long bookId);
}
