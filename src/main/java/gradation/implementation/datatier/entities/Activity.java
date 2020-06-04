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
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private ActivityType typeActivity;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Address address;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Level minimumLevel;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Level maximumLevel;

    @Column(nullable = false)
    private Short duration;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private SportsMan creator;

    @ManyToMany
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private List<SportsMan> candidates = new ArrayList<>();

    @ManyToMany
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private List<SportsMan> participants = new ArrayList<>();

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

    public ActivityType getTypeActivity() {
        return typeActivity;
    }

    public void setTypeActivity(ActivityType activity) {
        this.typeActivity = activity;
    }

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }

    public String getActivityName() {
        return typeActivity.getName();
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

    public List<SportsMan> getParticipants() {
        return participants;
    }

    public void setParticipants(List<SportsMan> registered) {
        this.participants = registered;
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

    public List<SportsMan> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<SportsMan> candidate) {
        this.candidates = candidate;
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
        this.typeActivity = activityForm.getActivity();
        this.creator = sportsMan;
        this.address = address;
        this.open = true;
        this.over = false;
    }

    public void addParticipant(SportsMan sportsMan) {
        this.participants.add(sportsMan);
    }

    public void removeParticipant(SportsMan sportsMan) {
        this.participants.remove(sportsMan);
    }

    public void update(ActivityForm activityForm, Address address) {
        this.name = activityForm.getName();
        this.typeActivity = activityForm.getActivity();
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
        if (this.getCandidates().contains(sportsMan))
            return true;
        else
            return false;
    }

    public boolean checkPresence(SportsMan sportsMan){
        if (this.getParticipants().contains(sportsMan))
            return true;
        else
            return false;
    }

    public boolean checkCreator(SportsMan sportsMan){
        if(this.creator.getId() == sportsMan.getId())
            return true;
        else
            return false;
    }

}
