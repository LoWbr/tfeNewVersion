package gradation.implementation.datatier.entities;

import javax.persistence.*;

@Entity
public class PromotionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private SportsMan applier;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Role inDemand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SportsMan getApplier() {
        return applier;
    }

    public void setApplier(SportsMan candidate) {
        this.applier = candidate;
    }

    public Role getInDemand() {
        return inDemand;
    }

    public void setInDemand(Role inDemand) {
        this.inDemand = inDemand;
    }

    public PromotionRequest() {
    }

    public PromotionRequest(SportsMan applier, Role inDemand) {
        this.applier = applier;
        this.inDemand = inDemand;
    }
}
