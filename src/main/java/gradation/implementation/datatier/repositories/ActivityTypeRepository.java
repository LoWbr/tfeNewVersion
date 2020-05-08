package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActivityTypeRepository extends CrudRepository<ActivityType, Long> {
    @Query("Select activitytype from ActivityType activitytype where activitytype.id = :id")
    ActivityType findSpecific(
            @Param("id") Long id);
}
