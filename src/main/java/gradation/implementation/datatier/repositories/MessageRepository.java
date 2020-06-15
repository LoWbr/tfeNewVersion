package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("Select message from Message message where message.author = :user order by message.timeOfDispatch DESC")
    List<Message> findByCreator(
            @Param("user") SportsMan sportsman);

    @Query("Select message from Message message where :user member of message.addressees order by message.timeOfDispatch DESC")
    List<Message> findByReceptor(
            @Param("user") SportsMan sportsman);

    @Query("Select message from Message message where message.id = :id")
    Message findSpecific(
            @Param("id") Long id);
}
