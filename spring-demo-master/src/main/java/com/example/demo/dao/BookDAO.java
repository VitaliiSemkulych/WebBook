package com.example.demo.dao;

import com.example.demo.model.Book;
import com.example.demo.model.Mark;
import com.example.demo.dto.SearchByPhraseModel;
import com.example.demo.enums.SearchType;
import com.example.demo.extractor.BookListResultSetExtractor;
import com.example.demo.extractor.MarkExtractor;
import com.example.demo.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// data access object, which bring data for functionality related with book
@Repository
@Transactional
public class BookDAO {


    @Autowired
    private BookListResultSetExtractor ble;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private MarkExtractor markExtractor;
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> selectByGenreID(String id) {
        String selectByGenreID = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id) \n"
                + "Where B.genre_id=? ORDER BY B.name";
        if (id.equals("17")) {
            return this.selectAll();
        } else {
            return this.getBookList(selectByGenreID,new Object[]{id});
        }

    }
    public List<Book> selectByLetter(String letter) {
        String selectByLetter = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id) \n"
                + "Where B.name Like '" + letter + "%' ORDER BY B.name";
        return this.getBookList(selectByLetter);
    }
    public List<Book> selectByFrace(SearchByPhraseModel searchFrace) {

        String selectByLetter = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id) \n";

        if(searchFrace.getSearchType()==SearchType.AUTHOR){
            selectByLetter+="Where A.fio Like '%" + searchFrace.getSearchPhrase().toLowerCase() + "%' ORDER BY B.name";
        }else{
            selectByLetter+="Where B.name Like '%" +searchFrace.getSearchPhrase().toLowerCase() + "%' ORDER BY B.name";
        }
        return this.getBookList(selectByLetter);
    }


    public Book selectBookByID(String id) {
        String selectBookByID = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id) \n"
                + "Where B.id=?";
        return this.getBook(selectBookByID,new Object[]{id});
    }

    public List<Book> selectReadingBookByUserID(String userId) {
        String selectBookByID = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id)"
                + " inner join reading_book R on(R.book_id=B.id) \n"
                + "Where R.user_id=?";
        return this.getBookList(selectBookByID,new Object[]{userId});
    }
    public List<Book> selectReadedBookByUserID(String userId) {
        String selectBookByID = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id)"
                + " inner join readed_book R on(R.book_id=B.id) \n"
                + "Where R.user_id=?";
        return this.getBookList(selectBookByID,new Object[]{userId});
    }
    public List<Book> selectInterestingBookByUserID(String userId) {
        String selectBookByID = "SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n"
                + "inner join genre G on (B.genre_id=G.id) \n"
                + "inner join publisher P on(B.publisher_id=P.id)"
                + " inner join interesting_book R on(R.book_id=B.id) \n"
                + "Where R.user_id=?";
        return this.getBookList(selectBookByID,new Object[]{userId});
    }
    public List<Book> selectAll() {
        String SELECT_BOOK_ALL="SELECT B.*,A.fio as author_name, G.name as genre_name, P.name as publisher_name  FROM book B inner join author A on(B.author_id=A.id) \n" +
                "inner join genre G on (B.genre_id=G.id) \n" +
                "inner join publisher P on(B.publisher_id=P.id) \n" +
                "ORDER BY B.name";
        return this.getBookList(SELECT_BOOK_ALL);
    }
    private List<Book> getBookList(String SQL){
        return jdbcTemplate.query(SQL,ble);

    }
    private List<Book> getBookList(String SQL,Object[] args){
        return jdbcTemplate.query(SQL,args,ble);

    }
    private Book getBook(String SQL, Object[] args){
        return jdbcTemplate.queryForObject(SQL, args, bookMapper);

    }

    public int getInterestingModeNumber(long bookId,String userEmail) {
        String SQL="select count(*) from interesting_book where book_id=? and user_id=?";
        return this.getTableId(SQL,new Object[]{bookId,userEmail});

    }

    public int getReadingModeNumber(long bookId,String userEmail) {
        String SQL="select count(*) from reading_book where book_id=? and user_id=?";
        return this.getTableId(SQL,new Object[]{bookId,userEmail});

    }

    public int getReadModeNumber(long bookId,String userEmail) {
        String SQL="select count(*) from readed_book where book_id=? and user_id=?";
        return this.getTableId(SQL,new Object[]{bookId,userEmail});

    }

    private int getTableId(String SQL, Object[] args){
        return jdbcTemplate.queryForObject(SQL, args, Integer.class);
    }

    public void addBookInInterestingMode(long bookId,String userEmail){
        String sql = "INSERT INTO interesting_book (book_id,user_id) VALUES(?,?)";
        jdbcTemplate.update(sql,bookId,userEmail);
    }

    public void delateBookInInterestingMode(long bookId,String userEmail){
        String sql = "DELETE FROM interesting_book WHERE book_id=? and user_id=?";
        jdbcTemplate.update(sql,bookId,userEmail);
    }
    public void addBookInReadingMode(long bookId,String userEmail){
        String sql = "INSERT INTO reading_book (book_id,user_id) VALUES(?,?)";
        jdbcTemplate.update(sql,bookId,userEmail);
    }
    public void delateBookInReadingMode(long bookId,String userEmail){
        String sql = "DELETE FROM reading_book WHERE book_id=? and user_id=?";
        jdbcTemplate.update(sql,bookId,userEmail);
    }
    public void addBookInReadMode(long bookId,String userEmail){
        String sql = "INSERT INTO readed_book (book_id,user_id) VALUES(?,?)";
        jdbcTemplate.update(sql,bookId,userEmail);
    }
    public void delateBookInReadMode(long bookId,String userEmail){
        String sql = "DELETE FROM readed_book WHERE book_id=? and user_id=?";
        jdbcTemplate.update(sql,bookId,userEmail);
    }
    public int getMarkNumber(long bookId,String userEmail) {
        String SQL="select count(*) from evaluation where book_id=? and user_id=?";
        return this.getTableId(SQL,new Object[]{bookId,userEmail});

    }

    public List<Mark> getMarckList(long bookId){
        String SQL="select mark from evaluation where book_id=?";
        return jdbcTemplate.query(SQL,new Object[]{bookId}, markExtractor);
    }
    public void updateMark(long bookId,String userEmail,int marck) {
        String sql = "INSERT INTO evaluation (user_id,book_id,mark) VALUES(?,?,?)";
        jdbcTemplate.update(sql, userEmail,bookId, marck);
    }


    public byte[] getContent(long bookId){
        String SQL="select content from book where id=?";
        return jdbcTemplate.queryForObject(SQL,new Object[]{bookId},(resultSet, i) -> resultSet.getBytes("content"));
    }

    public byte[] getImage(long bookId){
        String SQL="select image from book where id=?";
        return jdbcTemplate.queryForObject(SQL,new Object[]{bookId},(resultSet, i) -> resultSet.getBytes("image"));
    }

}
