package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = true, nullable = false)
    private Long id;

    @CreationTimestamp
    private Timestamp date;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private SportsMan author;

    @Lob
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public SportsMan getAuthor() {
        return author;
    }

    public void setAuthor(SportsMan author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Topic() {
    }

    public Topic(SportsMan sportsMan, TopicForm topicForm) {
        this.content = topicForm.getContent();
        this.author = sportsMan;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.date = timestamp;
    }



}
