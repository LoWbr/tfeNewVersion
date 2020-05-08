package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.*;

import javax.persistence.*;

@Entity
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = true, nullable = false)
    private Long id;

    private String name;

    private Byte place;

    private Short maximumThreshold;

    @Column(name = "ratio_points")
    private Double ratioPoints;

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

    public Level(){}

    public void update(LevelForm levelForm){
        this.name = levelForm.getName();
        this.maximumThreshold = levelForm.getMaximumThreshold();
        this.ratioPoints = levelForm.getRatioPoints();
    }

}
