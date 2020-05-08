package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SportsManRepository extends CrudRepository<SportsMan, Long> {

    Optional<SportsMan> findByEmail(String email);

    @Query("Select sportsman from SportsMan sportsman where sportsman.id = :id")
    SportsMan findSpecific(
            @Param("id") Long id);

    @Query("Select sportsman from SportsMan sportsman where sportsman.id <> :id")
    List<SportsMan> findAllWithoutMe(
            @Param("id") Long id);

    @Query("Select sportsman from SportsMan sportsman where sportsman not in (:list)" +
            "and sportsman.id <> :id")
    List<SportsMan> findNotContacts(
            @Param("list") List<SportsMan> listContacts,
            @Param("id") Long id);

    @Query("Select sportsman from SportsMan sportsman where sportsman.email = :email")
    SportsMan findSpecific(
            @Param("email") String email);

    @Query("Select sportsman from SportsMan sportsman where :role member of sportsman.roles")
    List<SportsMan> selectAuthorityUsers(
            @Param("role") Role role);

}
