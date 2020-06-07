package gradation.implementation.datatier.entities;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class TopicTest {

    @Test
    public void getId() {
        Topic topic = new Topic();
        topic.setId(1L);
        Long id = topic.getId();
        Long value = 1L;
        assertEquals(value, id);
    }

    @Test
    public void setId() {
        Topic topic = new Topic();
        topic.setId(1L);
        assertNotNull(topic.getId());
    }

    @Test
    public void getDate() {
        /*Topic topic = new Topic();
        Date date = new Date();
        topic.setDate(new Timestamp(date.getTime()));
        Timestamp time = topic.getDate();
        Timestamp value = new Timestamp(date.getTime());
        assertEquals(value, time);*/
    }

    @Test
    public void setDate() {
        /*Topic topic = new Topic();
        Date date = new Date();
        topic.setDate(new Timestamp(date.getTime()));
        assertNotNull(topic.getDate());*/
    }

    @Test
    public void getAuthor() {
        Topic topic = new Topic();
        SportsMan sportsMan = new SportsMan();
        topic.setAuthor(sportsMan);
        assertEquals(sportsMan,topic.getAuthor());
    }

    @Test
    public void setAuthor() {
        Topic topic = new Topic();
        SportsMan sportsMan = new SportsMan();
        topic.setAuthor(sportsMan);
        assertNotNull(topic.getAuthor());
    }

    @Test
    public void getContent() {
        Topic topic = new Topic();
        String test = "test";
        topic.setContent(test);
        assertEquals(test,topic.getContent());
    }

    @Test
    public void setContent() {
        Topic topic = new Topic();
        String test = "test";
        topic.setContent(test);
        assertNotNull(topic.getContent());
    }
}