package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.*;
import gradation.implementation.presentationtier.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    SportsManRepository sportsManRepository;
    @Autowired
    RoleRepository roleRepository;

    public Iterable<SportsMan> selectAuthorityUsers() {
        return this.sportsManRepository.selectAuthorityUsers(findAdministrator());
    }

    public Role findAdministrator(){
        return this.roleRepository.find((long) 3);
    }


}
