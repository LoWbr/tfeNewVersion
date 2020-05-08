package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.RoleService;
import gradation.implementation.datatier.entities.Role;
import gradation.implementation.datatier.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImplementation implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImplementation(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findSimplyRole(){
        return this.roleRepository.find((long) 1);
    }

    @Override
    public Role findConfirmedRole(){
        return this.roleRepository.find((long) 2);
    }

    @Override
    public Role findAdministrator(){
        return this.roleRepository.find((long) 3);
    }

    @Override
    public List<Role> findRoles(Long id) {
        return this.roleRepository.findForInitialize(id);
    }
}
