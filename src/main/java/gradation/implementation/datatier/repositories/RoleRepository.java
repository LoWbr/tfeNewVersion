package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository  extends CrudRepository<Role, Long> {

    @Query("Select role from Role role where role.id = :id")
    List<Role> findForInitialize(
            @Param("id") Long id);


    @Query("Select role from Role role where role.id = :id")
    Role find(
            @Param("id") Long id);

}
