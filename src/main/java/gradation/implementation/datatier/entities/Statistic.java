package gradation.implementation.datatier.entities;

import javax.persistence.*;

@Entity
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private SportsMan sportsMan;

    private Integer earnedPoints;

    @OneToOne
    private Activity activity;

    @Column(precision = 4, scale = 2)
    private Integer energyExpenditure;

    public SportsMan getSportsMan() {
        return sportsMan;
    }

    public void setSportsMan(SportsMan sportsMan) {
        this.sportsMan = sportsMan;
    }

    public Integer getEarnedPoints() {
        return earnedPoints;
    }

    public void setEarnedPoints(Integer earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Integer getEnergyExpenditure() {
        return energyExpenditure;
    }

    public void setEnergyExpenditure(Integer energyExpenditure) {
        this.energyExpenditure = energyExpenditure;
    }

    public Statistic() {
    }

    public Statistic(SportsMan sportsMan, Activity activity, Integer earnedPoints, Integer energyExpenditure) {
        this.sportsMan = sportsMan;
        this.activity = activity;
        this.earnedPoints = earnedPoints;
        this.energyExpenditure = energyExpenditure;
    }
}
