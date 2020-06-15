package gradation.implementation.presentationtier.form;

import gradation.implementation.datatier.entities.ActivityType;

import javax.validation.constraints.*;

public class ActivityTypeForm {

    private Long id;

    @NotBlank(message = "This field cannot be empty!!")
    @Size(min= 8, max=40, message="Between 8 & 40 characters")
    private String name;

    @DecimalMax(value ="15.0", inclusive = false , message = "must be lower than 15.0")
    @DecimalMin(value ="2.0", inclusive = false, message = "must be highter than 2.0")
    @Positive(message = "You have to put a valid value (positive)")
    @Digits(integer=2, fraction=1, message = "only one decimal")
    @NotNull
    private Float met;

    public ActivityTypeForm(){
        this.met = 2.5f;
    }

    public ActivityTypeForm(ActivityType activityType) {
        this.id = activityType.getId();
        this.name = activityType.getName();
        this.met = activityType.getMet();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMet() {
        return met;
    }

    public void setMet(Float met) {
        this.met = met;
    }
}
