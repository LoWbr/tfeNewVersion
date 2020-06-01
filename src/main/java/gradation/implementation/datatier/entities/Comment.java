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

    private Timestamp date;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private SportsMan author;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Activity activity;

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
        ZonedDateTime current = ZonedDateTime.now().plusHours(2);
        Timestamp timestamp = Timestamp.valueOf(current.toLocalDateTime());
        String value = timestamp.toString();
        this.date = Timestamp.valueOf(value);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", date=" + date +
                ", author=" + author +
                ", activity=" + activity +
                ", content='" + content + '\'' +
                '}';
    }
}
