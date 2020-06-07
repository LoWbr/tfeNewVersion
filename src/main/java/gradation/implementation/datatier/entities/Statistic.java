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

    private Short earnedPoints;

    @OneToOne
    private Activity activity;

    @Column
    private Short energyExpenditure;

    public SportsMan getSportsMan() {
        return sportsMan;
    }

    public void setSportsMan(SportsMan sportsMan) {
        this.sportsMan = sportsMan;
    }

    public Short getEarnedPoints() {
        return earnedPoints;
    }

    public void setEarnedPoints(Short earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Short getEnergyExpenditure() {
        return energyExpenditure;
    }

    public void setEnergyExpenditure(Short energyExpenditure) {
        this.energyExpenditure = energyExpenditure;
    }

    public Statistic() {
    }

    public Statistic(SportsMan sportsMan, Activity activity, Short earnedPoints, Short energyExpenditure) {
        this.sportsMan = sportsMan;
        this.activity = activity;
        this.earnedPoints = earnedPoints;
        this.energyExpenditure = energyExpenditure;
    }
}
