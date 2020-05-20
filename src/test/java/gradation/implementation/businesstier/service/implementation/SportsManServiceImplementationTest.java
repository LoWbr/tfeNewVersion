package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.ActivitySettingService;
import gradation.implementation.businesstier.service.contractinterface.RoleService;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.SportsManRepository;
import gradation.implementation.datatier.repositories.StatisticRepository;
import gradation.implementation.presentationtier.form.SportsManForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class SportsManServiceImplementationTest {

    @Mock
    private SportsManRepository sportsManRepository;

    @Mock
    private StatisticRepository statisticRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private ActivitySettingService activitySettingService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SportsManServiceImplementation sportsManServiceImplementation;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void findCurrentUser() {
        String mail = "test@gmail.com";
        SportsMan sportsMan = new SportsMan();
        sportsMan.setEmail(mail);
        sportsMan.setFirstName("Laurent");

        given(sportsManRepository.findSpecific(mail)).willReturn(sportsMan);
        SportsMan result = sportsManServiceImplementation.findCurrentUser(mail);

        assertEquals(sportsMan.getFirstName(), result.getFirstName());
        verify(sportsManRepository, times(1)).findSpecific(mail);
        verifyNoMoreInteractions(sportsManRepository);
    }

    @Test
    public void findSpecificUser() {
        Long id = 1L;
        SportsMan sportsMan = new SportsMan();
        sportsMan.setId(id);
        sportsMan.setFirstName("Laurent");

        given(sportsManRepository.findSpecific(id)).willReturn(sportsMan);
        SportsMan result = sportsManServiceImplementation.findSpecificUser(id);

        assertEquals(sportsMan.getFirstName(), result.getFirstName());
        verify(sportsManRepository, times(1)).findSpecific(id);
        verifyNoMoreInteractions(sportsManRepository);
    }

    @Test
    public void getAllUser() {
        SportsMan sportsMan = new SportsMan(), sportsMan1 = new SportsMan();
        Iterable<SportsMan> sportsMen = Arrays.asList(sportsMan,sportsMan1);

        given(sportsManRepository.findAll()).willReturn(sportsMen);
        Iterable<SportsMan> result = sportsManServiceImplementation.getAllUser();

        assertEquals(sportsMen, result);
        verify(sportsManRepository, times(1)).findAll();
        verifyNoMoreInteractions(sportsManRepository);
    }

    @Test
    public void getAllExceptConnectedUser() {
        Long id = 1L;
        SportsMan sportsMan = new SportsMan();
        sportsMan.setId(id);
        SportsMan sportsMan2 = new SportsMan(), sportsMan3 = new SportsMan();
        List<SportsMan> sportsMen = Arrays.asList(sportsMan2,sportsMan3);

        given(sportsManRepository.findAllWithoutMe(id)).willReturn(sportsMen);
        Iterable<SportsMan> result = sportsManServiceImplementation.getAllExceptConnectedUser(id);

        assertEquals(sportsMen, result);
        verify(sportsManRepository, times(1)).findAllWithoutMe(id);
        verifyNoMoreInteractions(sportsManRepository);
    }

    @Test
    public void getAllContacts() {
        SportsMan sportsMan = new SportsMan();
        String mail = "test@gmail.com";
        sportsMan.setEmail(mail);
        SportsMan sportsMan2 = new SportsMan(), sportsMan3 = new SportsMan();
        sportsMan.getContacts().add(sportsMan2);
        sportsMan.getContacts().add(sportsMan3);
        List<SportsMan> contacts = sportsMan.getContacts();

        given(sportsManRepository.findSpecific(mail)).willReturn(sportsMan);
        Iterable<SportsMan> result = sportsManServiceImplementation.getAllContacts(mail);

        assertEquals(sportsMan.getContacts(), result);
        verify(sportsManRepository, times(1)).findSpecific(mail);
        verifyNoMoreInteractions(sportsManRepository);
    }

    @Test
    public void getAllNoContats() {
        SportsMan sportsMan = new SportsMan();
        Long id = 1L;
        SportsMan sportsMan2 = new SportsMan(), sportsMan3 = new SportsMan(), sportsMan4 = new SportsMan();
        sportsMan.getContacts().add(sportsMan2);
        sportsMan.getContacts().add(sportsMan3);
        List<SportsMan> notContacts = Arrays.asList(sportsMan4);

        given(sportsManRepository.findNotContacts(sportsMan.getContacts(), id)).willReturn(notContacts);
        Iterable<SportsMan> result = sportsManServiceImplementation.getAllNoContats(sportsMan.getContacts(),id);

        assertEquals(notContacts, result);
        verify(sportsManRepository, times(1)).findNotContacts(sportsMan.getContacts(), id);
        verifyNoMoreInteractions(sportsManRepository);
    }

    @Test
    public void addOrRemoveContacts() {
        SportsMan sportsMan = new SportsMan(), sportsMan2 = new SportsMan();
        assertEquals(0, sportsMan.getContacts().size());
        sportsManServiceImplementation.addOrRemoveContacts(sportsMan, sportsMan2,true);
        assertNotEquals(0,sportsMan.getContacts().size());
        sportsManServiceImplementation.addOrRemoveContacts(sportsMan, sportsMan2,false);
        assertEquals(0,sportsMan.getContacts().size());
    }

    @Test
    public void saveUser() {
        SportsMan sportsMan = new SportsMan();
        sportsMan.setFirstName("Lolo");
        Long id = 1L;
        sportsMan.setId(id);
        given(sportsManRepository.save(sportsMan)).willReturn(sportsMan);
        assertNotNull(sportsManRepository.save(sportsMan));
    }

    @Test
    public void findBySportsMan() {
        Statistic statistic = new Statistic(), statistic1 = new Statistic();
        SportsMan sportsMan = new SportsMan();
        statistic.setSportsMan(sportsMan);
        statistic1.setSportsMan(sportsMan);
        List<Statistic> statistics = Arrays.asList(statistic,statistic1);

        given(statisticRepository.findBySportsMan(sportsMan)).willReturn(statistics);
        Iterable<Statistic> result = sportsManServiceImplementation.findBySportsMan(sportsMan);
        assertEquals(statistics, result);
    }

    @Test
    public void blockOrUnblock() {
        SportsMan sportsMan = new SportsMan();
        sportsManServiceImplementation.blockOrUnblock(sportsMan,true);
        assertTrue(sportsMan.getBlocked());
        sportsManServiceImplementation.blockOrUnblock(sportsMan,false);
        assertFalse(sportsMan.getBlocked());
    }

    @Test
    public void selectAuthorityUsers() {
        SportsMan sportsMan = new SportsMan(), sportsMan1 = new SportsMan();
        Role role1 = new Role();
        sportsMan.getRoles().add(role1);
        List<SportsMan> admins = Arrays.asList(sportsMan);
        given(roleService.findAdministrator()).willReturn(role1);
        given(sportsManRepository.selectAuthorityUsers(role1)).willReturn(admins);
        Iterable<SportsMan> result = sportsManServiceImplementation.selectAuthorityUsers();
        assertEquals(admins, result);
    }

    @Test
    public void createUser() {
        SportsMan sportsMan = new SportsMan();
        Role role = new Role();
        Level level = new Level();
        SportsManForm sportsManForm = new SportsManForm();
        sportsManForm.setFirstname("Test");
        sportsManForm.setDateofBirth("1990-12-25");
        sportsManForm.setPassword("testpwd");
        given(roleService.findSimplyRole()).willReturn(role);
        given(activitySettingService.findBeginner()).willReturn(level);
        given(passwordEncoder.encode(sportsManForm.getPassword())).
                willReturn("$2y$10$3pZaLkmR/1jy0SP7ahBsJ.ExPxxugtrZeao1CVrJ9rDM6gR/SOLUG");
        sportsManServiceImplementation.createUser(sportsManForm);
        verify(roleService, times(1)).findSimplyRole();
        verifyNoMoreInteractions(roleService);
        verify(activitySettingService, times(1)).findBeginner();
        verifyNoMoreInteractions(activitySettingService);
        verify(passwordEncoder, times(1)).encode(sportsManForm.getPassword());
        verifyNoMoreInteractions(passwordEncoder);
    }

    @Test
    public void updateUser() {
        SportsMan sportsMan = new SportsMan();
        sportsMan.setFirstName("Initial");
        SportsManForm sportsManForm = new SportsManForm();
        sportsManForm.setFirstname("Test");
        sportsManForm.setDateofBirth("1990-12-25");
        sportsManForm.setPassword("testpwd");
        given(passwordEncoder.encode(sportsManForm.getPassword())).
                willReturn("$2y$10$3pZaLkmR/1jy0SP7ahBsJ.ExPxxugtrZeao1CVrJ9rDM6gR/SOLUG");
        sportsManServiceImplementation.updateUser(sportsMan,sportsManForm);
        assertEquals(sportsMan.getFirstName(),sportsManForm.getFirstname());

    }

    @Test
    public void promoteUser() {
    }

    @Test
    public void saveStatistic() {
    }

    @Test
    public void sendMessage() {
    }

    @Test
    public void findMessages() {
    }

    @Test
    public void getNotifications() {
    }

    @Test
    public void getAllNotMarked() {
    }

    @Test
    public void setResultForEventToParticipant() {
    }
}