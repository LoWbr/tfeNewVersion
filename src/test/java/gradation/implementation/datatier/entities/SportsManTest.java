package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.SportsManForm;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class SportsManTest {

    @Test
    public void addRoles() {
        SportsMan sportsMan = new SportsMan();
        Role role = new Role();
        assertEquals(0, sportsMan.getRoles().size());
        sportsMan.addRoles(role);
        assertEquals(1, sportsMan.getRoles().size());
    }

    @Test
    public void addContact() {
        SportsMan sportsMan = new SportsMan(), sportsMan2 = new SportsMan();
        assertEquals(0, sportsMan.getContacts().size());
        sportsMan.addContact(sportsMan2);
        assertEquals(1, sportsMan.getContacts().size());
    }

    @Test
    public void removeContact() {
        SportsMan sportsMan = new SportsMan(), sportsMan2 = new SportsMan();
        sportsMan.addContact(sportsMan2);
        assertEquals(1, sportsMan.getContacts().size());
        sportsMan.removeContact(sportsMan2);
        assertEquals(0, sportsMan.getContacts().size());
    }

    @Test
    public void updateSportsMan() {
        SportsMan sportsMan = new SportsMan();
        sportsMan.setFirstName("Laurent");
        sportsMan.setLastName("Weber");
        sportsMan.setEmail("laurent@gmail.com");
        sportsMan.setDescription("------");
        sportsMan.setWeight(84.5f);
        sportsMan.setDateOfBirth(LocalDate.of(1990,05,15));
        SportsManForm sportsManForm = new SportsManForm();
        sportsManForm.setFirstname("Test");
        sportsManForm.setLastname("testLastName");
        sportsManForm.setMail("test@gmail.com");
        sportsManForm.setDescription("complete");
        sportsManForm.setWeight(70.0f);
        sportsManForm.setDateofBirth("1990-05-22");
        sportsMan.updateSportsMan(sportsManForm);
        assertEquals("Test", sportsMan.getFirstName());
        assertEquals("testLastName", sportsMan.getLastName());
        assertEquals("test@gmail.com", sportsMan.getEmail());
        assertEquals("complete", sportsMan.getDescription());
        assertEquals(new Double(70.0),sportsMan.getWeight());
        assertEquals(LocalDate.of(1990,05,22), sportsMan.getDateOfBirth());
    }

    @Test
    public void addPoints(){
        SportsMan sportsMan = new SportsMan();
        sportsMan.setPoints(80);
        sportsMan.addPoints(40);
        assertEquals(120,sportsMan.getPoints().longValue());
    }

    @Test
    public void checkLevelStatus() {
        SportsMan sportsMan = new SportsMan();
        sportsMan.setPoints(80);
        Level level = new Level();
        level.setPlace((byte) 4);
        level.setMaximumThreshold(60);
        sportsMan.setLevel(level);
        assertTrue(sportsMan.checkLevelStatus());
        sportsMan.setPoints(50);
        assertFalse(sportsMan.checkLevelStatus());
    }

    @Test
    public void setLevelUp() {
        SportsMan sportsMan = new SportsMan();
        Level level = new Level();
        level.setPlace((byte)3);
        sportsMan.setLevel(level);
        Byte actual = Byte.valueOf((byte) 4);
        assertEquals(actual,sportsMan.setLevelUp());
    }

    @Test
    public void getAge() {
        SportsMan sportsMan = new SportsMan();
        LocalDate dateOfBirth = LocalDate.of(1990,06,29);
        sportsMan.setDateOfBirth(dateOfBirth);
        assertEquals(29, sportsMan.getAge());
    }
}