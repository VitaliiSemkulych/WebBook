package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.Mark;
import com.example.demo.model.security.User;
import com.example.demo.repository.MarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class MarkServiceImp implements MarkService{

    private final MarkRepository markRepository;

    @Override
    public void insertMark(Book book, User user, int mark) {
       markRepository.save(Mark.builder()
               .user(user)
               .book(book)
               .mark(mark)
               .build());
    }

    @Override
    public boolean isBookEvaluated(String bookName, String userEmail) {
        return markRepository.existsByUserEmailAndBookName(userEmail,bookName);
    }

    @Override
    public List<Integer> getMarkOptionList() {
        return IntStream.rangeClosed(1, 5)
                .boxed().collect(Collectors.toList());
    }

    @Override
    public String getAverageBookMark(String bookName) {
        List<Mark> markList = StreamSupport
                .stream(markRepository.findByBookName(bookName).spliterator(), false)
                .collect(Collectors.toList());
        return markList.isEmpty()? "Not evaluated yet":
                new DecimalFormat("#.##")
                        .format(markList.stream().mapToInt(o->o.getMark()).sum()/markList.size());

    }
}
