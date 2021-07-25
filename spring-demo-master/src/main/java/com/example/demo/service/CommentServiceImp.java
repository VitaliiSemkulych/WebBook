package com.example.demo.service;

import com.example.demo.exception.NoSuchBookException;
import com.example.demo.model.Book;
import com.example.demo.model.Comment;
import com.example.demo.model.security.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImp implements CommentService{

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public List<Comment> getBookComments(long bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Override
    @Transactional
    public void modifyComment(long commentId, String commentText) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.setCommentText(commentText);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional(rollbackFor = {NoSuchBookException.class})
    public void sendComment(String commentText, long bookId, String email) {
        Book book = bookRepository.findById(bookId).orElseThrow(NoSuchBookException::new);
        User user = userRepository.findByEmail(email).get();
        commentRepository.save(Comment.builder()
                .commentText(commentText)
                .book(book)
                .user(user)
                .build());
    }
}
