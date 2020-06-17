package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @Query("Select event from Activity event where event.open = true and event.over = false and " +
            "event.plannedTo > :nowDate and " /*+ "(:activity is null or event.typeActivity = :activity) and"*/ +
            "(:activity is null or event.typeActivity = :activity)" +
            "and (:minlevel is null or event.minimumLevel = :minlevel) and (:maxlevel is null or event.maximumLevel = :maxlevel)" +
            "and (:city is null or event.address.city = :city)" +
            "and (:duration is null or event.duration >= :duration) and" +
            " (:plannedTo is null or event.plannedTo >= :plannedTo) order by event.plannedTo ASC, event.hour ASC")
    List<Activity> filter(
/*
            @Param("name") String name,
*/
            @Param("activity") ActivityType activityType,
            @Param("minlevel") Level minlevel,
            @Param("maxlevel") Level maxlevel,
            @Param("city") String city,
/*
            @Param("country") String country,
*/
            @Param("duration") Short duration,
            @Param("plannedTo") LocalDate plannedTo,
            @Param("nowDate") LocalDate nowDate);

    @Query("Select event from Activity event where event.open = true and event.over = false and" +
            " event.plannedTo > :date order by event.plannedTo ASC ")
    List<Activity> findOnTimeActivities(
            @Param("date") LocalDate date);

}
