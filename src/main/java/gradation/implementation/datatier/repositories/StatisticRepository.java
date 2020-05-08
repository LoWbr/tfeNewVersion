package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticRepository extends CrudRepository<Statistic, Long> {

    @Query("Select statistic from Statistic statistic where statistic.sportsMan = :id")
    List<Statistic> findBySportsMan(
            @Param("id") SportsMan id);

    @Query("Select statistic from Statistic statistic where statistic.activity = :activity")
    List<Statistic> findByActivity(
            @Param("activity") Activity activity);

    @Query("Select statistic from Statistic statistic where statistic.activity = :activity and " +
            "statistic.sportsMan = :sportsMan")
    List<Statistic> findForActivityAndSportsMan(
            @Param("activity") Activity activity,
            @Param("sportsMan") SportsMan sportsMan);

}
