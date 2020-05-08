package gradation.implementation.datatier.repositories;

import gradation.implementation.datatier.entities.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends CrudRepository<Address, Long> {
    @Query("Select address from Address address where address.id = :id")
    Address findSpecific(
            @Param("id") Long id);
}
