package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.*;

import javax.persistence.*;

@Entity
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = true, nullable = false)
    private Long id;
    @Column(length = 30)
    private String name;
    @Column(nullable = false)
    private Byte place;
    @Column(nullable = false)
    private Integer maximumThreshold;
    @Column(nullable = false, precision = 2, scale = 1)
    private Float ratioPoints;

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

    public Integer getMaximumThreshold() {
        return maximumThreshold;
    }

    public void setMaximumThreshold(Integer maximumThreshold) {
        this.maximumThreshold = maximumThreshold;
    }

    public Float getRatioPoints() {
        return ratioPoints;
    }

    public void setRatioPoints(Float ratioPoints) {
        this.ratioPoints = ratioPoints;
    }

    public Level(){}

    public void update(LevelForm levelForm){
        this.name = levelForm.getName();
        this.maximumThreshold = levelForm.getMaximumThreshold();
        this.ratioPoints = levelForm.getRatioPoints();
    }

}
