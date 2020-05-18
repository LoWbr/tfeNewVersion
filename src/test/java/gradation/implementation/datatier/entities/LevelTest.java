package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.LevelForm;
import org.junit.Test;

import static org.junit.Assert.*;

public class LevelTest {

    @Test
    public void update() {
        Level level = new Level();
        level.setName("Hero");
        level.setMaximumThreshold(15000);
        level.setRatioPoints(0.5);
        LevelForm levelForm = new LevelForm();
        levelForm.setName("Test");
        levelForm.setMaximumThreshold(1000);
        levelForm.setRatioPoints(0.4);
        level.update(levelForm);
        assertEquals(levelForm.getMaximumThreshold(),level.getMaximumThreshold());
        assertEquals(levelForm.getName(),level.getName());
        assertEquals(levelForm.getRatioPoints(),level.getRatioPoints());
    }
}