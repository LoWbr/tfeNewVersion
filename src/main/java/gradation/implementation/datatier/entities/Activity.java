package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.ActivityForm;

import javax.persistence.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    private String name;

    @Column(columnDefinition="TEXT")
    private String description;

    private LocalDate plannedTo;

    private LocalTime hour;

    @OneToOne
    @JoinColumn(name="fk_activity",referencedColumnName = "id", nullable = false)
    private ActivityType activity;

    @OneToOne
    @JoinColumn(name="fk_address", referencedColumnName = "id"/*, nullable = false*/)
    private Address address;

    @OneToOne
    @JoinColumn(name="fk_minimum_level", referencedColumnName = "id", nullable = false)
    private Level minimumLevel;

    @OneToOne
    @JoinColumn(name="fk_maximum_level", referencedColumnName = "id", nullable = false)
    private Level maximumLevel;

    @Column(nullable = false)
    private Short duration;

    @ManyToOne
    @JoinColumn(name="fk_creator", referencedColumnName = "id", nullable = false)
    private SportsMan creator;

    @ManyToMany
    @JoinColumn(name="fk_user_candidate", referencedColumnName = "id", nullable = false)
    private List<SportsMan> candidate = new ArrayList<>();

    @ManyToMany
    @JoinColumn(name="fk_user_registered", referencedColumnName = "id", nullable = false)
    private List<SportsMan> registered = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean open;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean over;

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

    public ActivityType getActivity() {
        return activity;
    }

    public void setActivity(ActivityType activity) {
        this.activity = activity;
    }

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }

    public String getActivityName() {
        return activity.getName();
    }

    public SportsMan getCreator() {
        return creator;
    }

    public void setCreator(SportsMan creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creator.getFirstName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPlannedTo() {
        return plannedTo;
    }

    public void setPlannedTo(LocalDate plannedTo) {
        this.plannedTo = plannedTo;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public List<SportsMan> getRegistered() {
        return registered;
    }

    public void setRegistered(List<SportsMan> registered) {
        this.registered = registered;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public List<SportsMan> getCandidate() {
        return candidate;
    }

    public void setCandidate(List<SportsMan> candidate) {
        this.candidate = candidate;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getOver() {
        return over;
    }

    public void setOver(Boolean over) {
        this.over = over;
    }

    public Activity() {
    }

    public Activity(ActivityForm activityForm, SportsMan sportsMan, Address address) throws ParseException {
        this.name = activityForm.getName();
        this.description = activityForm.getDescription();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.plannedTo = LocalDate.parse(activityForm.getPlannedTo(),formatter);
        String hour = activityForm.getHour().concat(":00");
        this.hour = LocalTime.parse(hour);
        this.minimumLevel = activityForm.getMinimumLevel();
        this.maximumLevel = activityForm.getMaximumLevel();
        this.duration = activityForm.getDuration();
        this.activity = activityForm.getActivity();
        this.creator = sportsMan;
        this.address = address;
        this.open = true;
        this.over = false;
    }

    public void addParticipant(SportsMan sportsMan) {
        this.registered.add(sportsMan);
    }

    public void removeParticipant(SportsMan sportsMan) {
        this.registered.remove(sportsMan);
    }

    public void update(ActivityForm activityForm, Address address) {
        this.name = activityForm.getName();
        this.activity = activityForm.getActivity();
        this.description = activityForm.getDescription();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.plannedTo = LocalDate.parse(activityForm.getPlannedTo(),formatter);
        String hour = activityForm.getHour().concat(":00");
        this.hour = LocalTime.parse(hour);
        this.duration = activityForm.getDuration();
        this.minimumLevel = activityForm.getMinimumLevel();
        this.maximumLevel = activityForm.getMaximumLevel();
        this.address = address;
    }

    public void closeEvent() {
        this.setOver(true);
    }

    public boolean checkLevel(SportsMan sportsMan){
        if(sportsMan.getLevel().getPlace() >= this.minimumLevel.getPlace() &&
                sportsMan.getLevel().getPlace() <= this.maximumLevel.getPlace())
            return true;
        else
            return false;
    }

    public boolean checkApplication(SportsMan sportsMan){
        if (this.getCandidate().contains(sportsMan))
            return true;
        else
            return false;
    }

    public boolean checkPresence(SportsMan sportsMan){
        if (this.getRegistered().contains(sportsMan))
            return true;
        else
            return false;
    }

}
