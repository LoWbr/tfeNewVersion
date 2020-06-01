package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @JoinColumn(referencedColumnName = "id")
    private SportsMan author;

    private String about;

    @Lob
    private String content;

    private LocalDateTime timeOfDispatch;

    public Message(){}

    public Message(MessageForm messageForm) {
        this.author = messageForm.getOriginator();
        this.addressees = messageForm.getAddressee();
        this.about = messageForm.getAbout();
        this.content = messageForm.getContent();
        this.timeOfDispatch = LocalDateTime.now();

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
