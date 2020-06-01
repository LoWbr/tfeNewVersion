package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.ActivityForm;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class ActivityTest {

    @Test
    public void addParticipant() {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        assertEquals(0, activity.getParticipants().size());
        activity.addParticipant(sportsMan);
        assertEquals(1, activity.getParticipants().size());
    }

    @Test
    public void removeParticipant() {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        activity.addParticipant(sportsMan);
        assertEquals(1, activity.getParticipants().size());
        activity.removeParticipant(sportsMan);
        assertEquals(0, activity.getParticipants().size());
    }

    @Test
    public void update() {
        Address address = new Address();
        Activity activity = new Activity();
        activity.setName("Initial");
        activity.setDescription("initial");
        activity.setPlannedTo(LocalDate.of(2020,8,7));
        activity.setHour(LocalTime.of(12,00));
        activity.setDuration((short) 45);
        ActivityForm activityForm = new ActivityForm();
        activityForm.setName("Test");
        activityForm.setDescription("test");
        activityForm.setPlannedTo("2020-08-08");
        activityForm.setHour("14:15");
        activityForm.setDuration((short) 60);
        activity.update(activityForm, address);
        assertEquals("Test", activity.getName());
        assertEquals("test", activity.getDescription());
        assertEquals(LocalTime.of(14,15), activity.getHour());
        assertEquals(LocalDate.of(2020,8,8),activity.getPlannedTo());
        assertEquals(new Short((short) 60), activity.getDuration());

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
        Level level = new Level(), level2 = new Level(), level3 = new Level();
        level.setPlace((byte) 1);
        level2.setPlace((byte) 2);
        level3.setPlace((byte) 3);
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        sportsMan.setLevel(level);
        activity.setMinimumLevel(level2);
        activity.setMaximumLevel(level2);
        assertFalse(activity.checkLevel(sportsMan));
        sportsMan.setLevel(level3);
        assertFalse(activity.checkLevel(sportsMan));
        sportsMan.setLevel(level2);
        assertTrue(activity.checkLevel(sportsMan));
    }

    @Test
    public void checkApplication() {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        assertFalse(activity.checkApplication(sportsMan));
        activity.getCandidates().add(sportsMan);
        assertTrue(activity.checkApplication(sportsMan));
    }

    @Test
    public void checkPresence() {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        assertFalse(activity.checkPresence(sportsMan));
        activity.getParticipants().add(sportsMan);
        assertTrue(activity.checkPresence(sportsMan));
    }
}