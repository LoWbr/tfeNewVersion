package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LevelRepository extends CrudRepository<Level,Long> {

    @Query("Select level from Level level where level.id = :id")
    Level findSpecific(
            @Param("id") Long id);

}
