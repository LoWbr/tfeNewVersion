package gradation.implementation.presentationtier.form;

import javax.validation.constraints.*;

public class ActivityTypeForm {

    @NotBlank(message = "This field cannot be empty!!")
    @Size(min= 10, max=30, message="Between 10 & 30 characters")
    private String name;

    @DecimalMax(value ="15.0", inclusive = false , message = "must be lower than 15.0")
    @DecimalMin(value ="2.0", inclusive = false, message = "must be highter than 2.0")
    @Positive(message = "You have to put a valid value (positive)")
    @Digits(integer=2, fraction=1, message = "only one decimal")
    @NotNull
    private Double met;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMet() {
        return met;
    }

    public void setMet(Double met) {
        this.met = met;
    }
}
