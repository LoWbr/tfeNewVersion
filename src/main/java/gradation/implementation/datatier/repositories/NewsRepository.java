package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends CrudRepository<News, Long> {

    @Query("Select news from News news where (:name is null or news.source.firstName = :name)" +
            "and (:type is null or news.type = :type) ")
    List<News> filter(
            @Param("name") String name,
            @Param("type") NewsType newstype);

    @Query("Select news from News news where news.target= :user and news.seen = false")
    List<News> findByUser(
            @Param("user") SportsMan sporstman);

    @Query("Select news from News news where news.id= :id")
    News findbyNewsId(
            @Param("id") Long idNews);

}
