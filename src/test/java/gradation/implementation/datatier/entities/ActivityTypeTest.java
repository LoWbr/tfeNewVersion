package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.ActivityTypeForm;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityTypeTest {

    @Test
    public void update() {
        ActivityType activityType = new ActivityType();
        activityType.setMet(1.85f);
        activityType.setName("Biking");
        ActivityTypeForm activityTypeForm = new ActivityTypeForm();
        activityTypeForm.setMet(2.05f);
        activityTypeForm.setName("Cycling");
        activityType.update(activityTypeForm);
        assertEquals(activityTypeForm.getMet(), activityType.getMet());
        assertEquals(activityTypeForm.getName(), activityType.getName());

    }
}