package com.example.demo.dao;

import com.example.demo.model.Comment;
import com.example.demo.extractor.RecensionListExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// data access object, which bring data for functionality related with recension
@Repository
@Transactional
public class RecensionDAO {
   @Autowired
   private RecensionListExtractor rle;
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public RecensionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Comment> getRecensionList(long bookId){
        String sql="SELECT  u.userName,r.* FROM recension r INNER JOIN users u ON(r.user_id=u.userid) Where r.book_id=?";
        return jdbcTemplate.query(sql,new Object[]{bookId},rle);
    }

    public void sendRecension(String recensionText,long bookId, String userEmail) {
        String sql =  "INSERT INTO recension (user_id,book_id, recension_text) "
                + "VALUES(?,?,?)";
        jdbcTemplate.update(sql,userEmail,bookId,recensionText);

    }
    public void modifyRecension(long recentionId,String modifyRecensionText){
        String sql =  "update recension set recension_text=? where recension_id=?";
        jdbcTemplate.update(sql,modifyRecensionText,recentionId);
    }
    public void delateRecension(long recentionId) {

        String sql =  "DELETE FROM recension WHERE recension_id=?";
        jdbcTemplate.update(sql,recentionId);
    }


/*    public String getRecensionUserName(String userEmail){
        String sql="select userName from users where userid=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{userEmail},String.class);

    }*/

}
