package gradation.implementation.presentationtier.form;


public class LevelForm {

    private String name;
    private Byte place;
    private Short maximumThreshold;
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

    public Short getMaximumThreshold() {
        return maximumThreshold;
    }

    public void setMaximumThreshold(Short maximumThreshold) {
        this.maximumThreshold = maximumThreshold;
    }

    public Double getRatioPoints() {
        return ratioPoints;
    }

    public void setRatioPoints(Double ratioPoints) {
        this.ratioPoints = ratioPoints;
    }

}
