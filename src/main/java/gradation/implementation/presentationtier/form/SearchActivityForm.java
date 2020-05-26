package gradation.implementation.presentationtier.form;

import gradation.implementation.datatier.entities.*;

public class SearchActivityForm {

    private ActivityType activity;
    private Level minimumLevel;
    private Level maximumLevel;
    private Short duration;
    private String city;
    private String country;
    private String date;

    public ActivityType getActivity() {
        return activity;
    }

    public void setActivity(ActivityType activity) {
        this.activity = activity;
    }

    public Level getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(Level minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    public Level getMaximumLevel() {
        return maximumLevel;
    }

    public void setMaximumLevel(Level maximumLevel) {
        this.maximumLevel = maximumLevel;
    }

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
