package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.ActivityForm;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddressTest {

    @Test
    public void update() {
        Address address = new Address();
        address.setNumber((short) 10);
        address.setCity("Namur");
        address.setStreet("Rue du Fou");
        address.setCountry("Belgium");
        address.setPostalCode(5000);
        ActivityForm activityForm= new ActivityForm();
        activityForm.setNumber((short) 15);
        activityForm.setCity("Liège");
        activityForm.setStreet("Rue test");
        activityForm.setCountry("Belgique");
        activityForm.setPostalCode(6000);
        address.update(activityForm);
        assertEquals(new Short((short) 15), address.getNumber());
        assertEquals(new Integer(6000), address.getPostalCode());
        assertEquals("Liège", address.getCity());
        assertEquals("Rue test", address.getStreet());
        assertEquals("Belgique", address.getCountry());
    }

}