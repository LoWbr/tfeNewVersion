package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic, Long> {

    @Query("Select topic from Topic topic where topic.id < 6 order by topic.date DESC")
    List<Topic> findRecents();
}
