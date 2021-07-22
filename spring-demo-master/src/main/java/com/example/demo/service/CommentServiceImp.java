package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.Comment;
import com.example.demo.model.security.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImp implements CommentService{

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public List<Comment> getBookComments(String bookName) {
        return commentRepository.findByBookName(bookName);
    }

    @Override
    public void modifyComment(long commentId, String commentText) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.setCommentText(commentText);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void sendComment(String commentText, String bookName, String email) {
        Book book = bookRepository.findByName(bookName);
        User user = userRepository.findByEmail(email).get();
        commentRepository.save(Comment.builder()
                .commentText(commentText)
                .book(book)
                .user(user)
                .build());
    }
}
