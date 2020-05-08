package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PromotionRequestRepository extends CrudRepository<PromotionRequest, Long> {

    @Query("Select promotionrequest from PromotionRequest promotionrequest where promotionrequest.candidate = :id")
    PromotionRequest findByCandidate(
            @Param("id") SportsMan sportsMan);

}
