package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.ActivityTypeForm;

import javax.persistence.*;

@Entity
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(nullable = false , precision = 3, scale = 1)
    private Float met;

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

    public ActivityType(){}

    public void update(ActivityTypeForm activityTypeForm) {
        this.name = activityTypeForm.getName();
        this.met = activityTypeForm.getMet();
    }
}
