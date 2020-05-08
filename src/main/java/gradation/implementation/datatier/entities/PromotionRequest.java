package gradation.implementation.datatier.entities;

import javax.persistence.*;

@Entity
public class PromotionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name="fk_candidate", referencedColumnName = "id", nullable = false)
    private SportsMan candidate;

    @OneToOne
    @JoinColumn(name = "fk_request_role", referencedColumnName = "id", nullable = false)
    private Role request;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SportsMan getCandidate() {
        return candidate;
    }

    public void setCandidate(SportsMan candidate) {
        this.candidate = candidate;
    }

    public Role getInDemand() {
        return request;
    }

    public void setInDemand(Role inDemand) {
        this.request = inDemand;
    }

    public PromotionRequest() {
    }

    public PromotionRequest(SportsMan candidate, Role inDemand) {
        this.candidate = candidate;
        this.request = inDemand;
    }
}
