package gradation.implementation.presentationtier.form;


import javax.validation.constraints.*;

public class LevelForm {

    @NotBlank(message = "This field cannot be empty!!")
    @Size(min= 10, max=30, message="Between 10 & 30 characters")
    private String name;
    private Byte place;
    @Max(50000)// à changer pour être plus petit que le suivant
    @Min(30)// à changer pour être plus grand que le précédent
    @NotNull
    private Integer maximumThreshold;
    @DecimalMax(value ="1.0", inclusive = true , message = "must be lower than 1.0")
    @DecimalMin(value ="0.0", inclusive = false, message = "must be highter than 0.0")
    @Positive(message = "You have to put a valid value (positive)")
    @Digits(integer=1, fraction=1, message = "only one decimal")
    @NotNull
    private Double ratioPoints;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getPlace() {
        return place;
    }

    public void setPlace(Byte place) {
        this.place = place;
    }

    public Integer getMaximumThreshold() {
        return maximumThreshold;
    }

    public void setMaximumThreshold(Integer maximumThreshold) {
        this.maximumThreshold = maximumThreshold;
    }

    public Double getRatioPoints() {
        return ratioPoints;
    }

    public void setRatioPoints(Double ratioPoints) {
        this.ratioPoints = ratioPoints;
    }

}
