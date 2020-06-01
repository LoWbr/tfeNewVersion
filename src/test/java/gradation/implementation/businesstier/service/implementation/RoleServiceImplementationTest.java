package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.datatier.entities.Role;
import gradation.implementation.datatier.repositories.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

public class RoleServiceImplementationTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImplementation roleServiceImplementation;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findSimplyRole() {
        Role role = new Role();
        given(roleRepository.find(1L)).willReturn(role);
        Role role1 = roleServiceImplementation.findSimplyRole();
        assertEquals(role,role1);
    }

    @Test
    public void findConfirmedRole() {
        Role role = new Role();
        given(roleRepository.find(2L)).willReturn(role);
        Role role1 = roleServiceImplementation.findConfirmedRole();
        assertEquals(role,role1);
    }

    @Test
    public void findAdministrator() {
        Role role = new Role();
        given(roleRepository.find(3L)).willReturn(role);
        Role role1 = roleServiceImplementation.findAdministrator();
        assertEquals(role,role1);
    }

    @Test
    public void findRoles() {
        Role role = new Role();
        List<Role> roles = Arrays.asList(role);
        given(roleRepository.findForInitialize(1L)).willReturn(roles);
        List<Role> test = roleServiceImplementation.findRoles(1L);
        assertEquals(test.size(), roles.size());

    }
}