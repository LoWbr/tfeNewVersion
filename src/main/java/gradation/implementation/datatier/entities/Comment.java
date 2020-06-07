package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Basic
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private SportsMan author;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Activity activity;

    @Column(length = 150, nullable = false)
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public SportsMan getAuthor() {
        return author;
    }

    public void setAuthor(SportsMan author) {
        this.author = author;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment() {
    }

    public Comment(CommentForm commentForm) {
        this.activity = commentForm.getActivity();
        this.author = commentForm.getAuthor();
        this.content = commentForm.getContent();
        ZoneId zoneId = ZoneId.of("UTC+2");
        this.date = LocalDateTime.now(zoneId);
    }

}
