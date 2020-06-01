package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.SportsManForm;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
public class SportsMan  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = true, nullable = false)
    private Long id;

    @Column(length = 30)
    private String firstName;

    @Column(length = 30)
    private String lastName;

    @Column(columnDefinition="TEXT")
    private String description;

    @Column(columnDefinition = "DATE")
    private LocalDate dateOfBirth;

    @Column(length = 40)
    private String email;

    @Column(length = 80)
    private String password;

    @OneToOne
    @JoinColumn(name="fk_level", referencedColumnName = "id", nullable = false)
    private Level level;

    private Double weight;

    private Integer points;

    @Column(nullable = false)
    private Boolean blocked;

    @OneToMany(mappedBy = "creator")
    private List<Activity> createdActivities;

    @ManyToMany(mappedBy = "registered")
    private List<Activity> registeredActivities;

    @OneToMany(mappedBy = "originator")
    private List<Message> SenddMessages;

    @ManyToMany(mappedBy = "addressee")
    private List<Message> ReceivedMessages;

    @ManyToMany
    private List<SportsMan> contacts = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role")
    private List<Role> roles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points){
        this.points = points;
    }

    public void addPoints(Integer earnedPoints) {
        this.points += earnedPoints;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public List<Activity> getCreatedActivities() {
        return createdActivities;
    }

    public void setCreatedActivities(List<Activity> createdActivities) {
        this.createdActivities = createdActivities;
    }

    public List<Activity> getRegisteredActivities() {
        return registeredActivities;
    }

    public void setRegisteredActivities(List<Activity> registeredActivities) {
        this.registeredActivities = registeredActivities;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<SportsMan> getContacts() {
        return contacts;
    }

    public void setContacts(List<SportsMan> contacts) {
        this.contacts = contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRoles(Role role) {
        roles.add(role);
    }


    public void addContact(SportsMan sportsMan) {
        this.contacts.add(sportsMan);
    }

    public void removeContact(SportsMan sportsMan) {
        this.contacts.remove(sportsMan);
    }

    public SportsMan() {
    }

    public SportsMan(SportsManForm sportsManForm) {
        this.firstName = sportsManForm.getFirstname();
        this.lastName = sportsManForm.getLastname();
        this.email = sportsManForm.getMail();
        this.description = sportsManForm.getDescription();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dateOfBirth = LocalDate.parse(sportsManForm.getDateofBirth(),formatter);
        this.weight = sportsManForm.getWeight();
        this.points = 0;
        this.blocked = false;
        this.roles = new ArrayList<>();
    }

    public void updateSportsMan(SportsManForm sportsManForm){
        this.firstName = sportsManForm.getFirstname();
        this.lastName = sportsManForm.getLastname();
        this.email = sportsManForm.getMail();
        this.description = sportsManForm.getDescription();
        this.dateOfBirth = LocalDate.parse(sportsManForm.getDateofBirth());
        this.weight = sportsManForm.getWeight();
    }

    public boolean checkLevelStatus() {
        if(this.level.getPlace() != 5 && this.getPoints()>this.getLevel().getMaximumThreshold()){
            return true;
        }
        return false;
    }

    public Byte setLevelUp(){
        Byte new_place = this.getLevel().getPlace();
        new_place++;
        return new_place;
    }

    public int getAge(){
        LocalDate current = LocalDate.now();
        return Period.between(dateOfBirth,current).getYears();
    }

    public Statistic generateStatistic(Activity activity, Integer earnedPoints, Integer energeticExpenditure) {
        Statistic statistic = new Statistic(this, activity, earnedPoints, energeticExpenditure);
        return statistic;
    }
}
