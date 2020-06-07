package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = true, nullable = false)
    private Long id;

    @ManyToMany
    @JoinColumn(referencedColumnName = "id")
    private List<SportsMan> addressees;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private SportsMan author;

    @Column(length = 50, nullable = false)
    private String about;

    @Lob
    @Column(nullable = false)
    private String content;

    @Basic
    @Column(nullable = false)
    private LocalDateTime timeOfDispatch;

    public Message(){}

    public Message(MessageForm messageForm) {
        this.author = messageForm.getOriginator();
        this.addressees = messageForm.getAddressee();
        this.about = messageForm.getAbout();
        this.content = messageForm.getContent();
        ZoneId zoneId = ZoneId.of("UTC+2");
        this.timeOfDispatch = LocalDateTime.now(zoneId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SportsMan> getAddressees() {
        return addressees;
    }

    public void setAddressees(List<SportsMan> addressee) {
        this.addressees = addressee;
    }

    public SportsMan getAuthor() {
        return author;
    }

    public void setAuthor(SportsMan originator) {
        this.author = originator;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeOfDispatch() {
        return timeOfDispatch;
    }

    public void setTimeOfDispatch(LocalDateTime timeOfDispatch) {
        this.timeOfDispatch = timeOfDispatch;
    }


}
