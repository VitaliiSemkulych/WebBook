package com.example.demo.service;

import com.example.demo.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getBookComments(long bookId);
    void modifyComment(long commentId, String commentText);
    void deleteComment(long commentId);
    void sendComment(String commentText, long bookId, String email);
}
