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
    @JoinColumn(name="fk_target", referencedColumnName = "id")
    private List<SportsMan> addressee;

    @ManyToOne
    @JoinColumn(name="fk_source", referencedColumnName = "id")
    private SportsMan originator;

    private String about;

    @Lob
    private String content;

    private LocalDateTime timeOfDispatch;

    public Message(){}

    public Message(MessageForm messageForm) {
        this.originator = messageForm.getOriginator();
        this.addressee = messageForm.getAddressee();
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

    public List<SportsMan> getAddressee() {
        return addressee;
    }

    public void setAddressee(List<SportsMan> addressee) {
        this.addressee = addressee;
    }

    public SportsMan getOriginator() {
        return originator;
    }

    public void setOriginator(SportsMan originator) {
        this.originator = originator;
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
