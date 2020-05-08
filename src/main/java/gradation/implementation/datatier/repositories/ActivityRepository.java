package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Long> {

    @Query("Select event from Activity event where event.id = :id")
    Activity findSpecific(
            @Param("id") Long id);

    @Query("Select event from Activity event where event.creator = :id")
    List<Activity> findByCreator(
            @Param("id") SportsMan sportsMan);

    @Query("Select sportsman from SportsMan sportsman where sportsman not in (:list)")
    List<SportsMan> findNotRegistered(
            @Param("list") List<SportsMan> listNonInscrits);

    @Query("Select event from Activity event where (:activity is null or event.activity = :activity)" +
            "and (:level is null or event.minimumLevel = :level)")
    List<Activity> filter(
            @Param("activity") ActivityType activityType,
            @Param("level") Level level);

    @Query("Select event from Activity event where event.name = :name")
    Activity findByName(
            @Param("name") String name);
}
