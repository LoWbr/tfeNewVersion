package gradation.implementation.datatier.entities;

import javax.persistence.*;

@Entity
public class News{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private SportsMan target;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private SportsMan source;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Activity activity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NewsType type;

    @Column(length = 120, nullable = false)
    private String content;

    private boolean seen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SportsMan getTarget() {
        return target;
    }

    public void setTarget(SportsMan target) {
        this.target = target;
    }

    public SportsMan getSource() {
        return source;
    }

    public void setSource(SportsMan source) {
        this.source = source;
    }

    public NewsType getType() {
        return type;
    }

    public void setType(NewsType type) {
        this.type = type;
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

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public News(){}

    public News(SportsMan target, SportsMan source, Activity activity, NewsType type, String content, boolean seen) {
        this.target = target;
        this.source = source;
        this.activity = activity;
        this.content = content;
        this.type = type;
        this.seen = seen;
    }
}
