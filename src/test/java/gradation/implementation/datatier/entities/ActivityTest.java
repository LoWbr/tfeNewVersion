package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.ActivityForm;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityTest {

    @Test
    public void addParticipant() {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        assertEquals(0, activity.getRegistered().size());
        activity.addParticipant(sportsMan);
        assertEquals(1, activity.getRegistered().size());
    }

    @Test
    public void removeParticipant() {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        activity.addParticipant(sportsMan);
        assertEquals(1, activity.getRegistered().size());
        activity.removeParticipant(sportsMan);
        assertEquals(0, activity.getRegistered().size());
    }

    @Test
    public void update() {
        Address address = new Address();
        Activity activity = new Activity();
        activity.setName("Initial");
        ActivityForm activityForm = new ActivityForm();
        activityForm.setName("Test");
        activityForm.setPlannedTo("2020-08-08");
        activityForm.setHour("14:15");
        activity.update(activityForm, address);
        assertEquals("Test", activity.getName());
    }

    @Test
    public void closeEvent() {
        Activity activity = new Activity();
        activity.setOver(false);
        assertFalse(activity.getOver());
        activity.closeEvent();
        assertTrue(activity.getOver());
    }

    @Test
    public void checkLevel() {
        Level level = new Level(), level2 = new Level();
        level.setPlace((byte) 1);
        level2.setPlace((byte) 2);
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        sportsMan.setLevel(level);
        activity.setMinimumLevel(level2);
        assertFalse(activity.checkLevel(sportsMan));
        sportsMan.setLevel(level2);
        assertTrue(activity.checkLevel(sportsMan));
    }

    @Test
    public void checkApplication() {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        assertFalse(activity.checkApplication(sportsMan));
        activity.getCandidate().add(sportsMan);
        assertTrue(activity.checkApplication(sportsMan));
    }

    @Test
    public void checkPresence() {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        assertFalse(activity.checkPresence(sportsMan));
        activity.getRegistered().add(sportsMan);
        assertTrue(activity.checkPresence(sportsMan));
    }
}