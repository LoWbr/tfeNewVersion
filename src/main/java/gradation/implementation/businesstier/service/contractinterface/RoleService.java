package gradation.implementation.businesstier.service.contractinterface;

import gradation.implementation.datatier.entities.Role;

import java.util.List;

public interface RoleService {

    Role findSimplyRole();

    Role findConfirmedRole();

    Role findAdministrator();

    List<Role> findRoles(Long id);

}
