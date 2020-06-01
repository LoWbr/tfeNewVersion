package gradation.implementation.datatier.entities;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class CommentTest {

    @Test
    public void getId() {
        Comment comment = new Comment();
        comment.setId(1L);
        Long id = comment.getId();
        Long value = 1L;
        assertEquals(value, id);
    }

    @Test
    public void setId() {
        Comment comment = new Comment();
        comment.setId(1L);
        assertNotNull(comment.getId());
    }

    @Test
    public void getDate() {
        Comment comment = new Comment();
        Date date = new Date();
        comment.setDate(new Timestamp(date.getTime()));
        Timestamp time = comment.getDate();
        Timestamp value = new Timestamp(date.getTime());
        assertEquals(value, time);
    }

    @Test
    public void setDate() {
        Comment comment = new Comment();
        Date date = new Date();
        comment.setDate(new Timestamp(date.getTime()));
        assertNotNull(comment.getDate());
    }

    @Test
    public void getAuthor() {
        Comment comment = new Comment();
        SportsMan sportsMan = new SportsMan();
        comment.setAuthor(sportsMan);
        assertEquals(sportsMan,comment.getAuthor());
    }

    @Test
    public void setAuthor() {
        Comment comment = new Comment();
        SportsMan sportsMan = new SportsMan();
        comment.setAuthor(sportsMan);
        assertNotNull(comment.getAuthor());
    }

    @Test
    public void getActivity() {
        Comment comment = new Comment();
        Activity activity = new Activity();
        comment.setActivity(activity);
        assertEquals(activity,comment.getActivity());
    }

    @Test
    public void setActivity() {
        Comment comment = new Comment();
        Activity activity = new Activity();
        comment.setActivity(activity);
        assertNotNull(comment.getActivity());
    }

    @Test
    public void getContent() {
        Comment comment = new Comment();
        String test = "test";
        comment.setContent(test);
        assertEquals(test, comment.getContent());
    }

    @Test
    public void setContent() {
        Comment comment = new Comment();
        String test = "test";
        comment.setContent(test);
        assertNotNull(comment.getContent());
    }

}