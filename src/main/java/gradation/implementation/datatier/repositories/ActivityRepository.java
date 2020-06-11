package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@ConditionalOnProperty(name="app.repository", havingValue = "main")
public interface ActivityRepository extends CrudRepository<Activity, Long>{

    @Query("Select event from Activity event where event.id = :id")
    Activity findSpecific(
            @Param("id") Long id);

    @Query("Select event from Activity event where event.creator = :id")
    List<Activity> findByCreator(
            @Param("id") SportsMan sportsMan);

    @Query("Select sportsman from SportsMan sportsman where sportsman not in (:list)")
    List<SportsMan> findNotRegistered(
            @Param("list") List<SportsMan> listNonInscrits);

    @Query("Select event from Activity event where (:activity is null or event.typeActivity = :activity)" +
            "and (:minlevel is null or event.minimumLevel = :minlevel) and (:maxlevel is null or event.maximumLevel = :maxlevel)" +
            "and (:city is null or event.address.city = :city)" +
            "and (:duration is null or event.duration >= :duration) and" +
            " (:plannedTo is null or event.plannedTo >= :plannedTo)")
    List<Activity> filter(
            @Param("activity") ActivityType activityType,
            @Param("minlevel") Level minlevel,
            @Param("maxlevel") Level maxlevel,
            @Param("city") String city,
            @Param("duration") Short duration,
            @Param("plannedTo") LocalDate plannedTo);

    @Query("Select event from Activity event where event.plannedTo >= :date order by event.plannedTo ASC ")
    List<Activity> findOnTimeActivities(
            @Param("date") LocalDate date);

}
