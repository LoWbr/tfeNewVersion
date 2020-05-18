package gradation.implementation.datatier.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {

    @Test
    public void isSeen() {
        News news = new News();
        news.setSeen(true);
        assertTrue(news.isSeen());
        news.setSeen(false);
        assertFalse(news.isSeen());
    }
}