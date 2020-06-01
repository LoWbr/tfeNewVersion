package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.ActivityService;
import gradation.implementation.businesstier.service.contractinterface.ManagementService;
import gradation.implementation.businesstier.service.contractinterface.NewsService;
import gradation.implementation.businesstier.service.contractinterface.SportsManService;
import gradation.implementation.datatier.entities.Activity;
import gradation.implementation.datatier.entities.Message;
import gradation.implementation.datatier.entities.News;
import gradation.implementation.datatier.entities.SportsMan;
import gradation.implementation.presentationtier.form.MessageForm;
import gradation.implementation.presentationtier.form.NotationForm;
import gradation.implementation.presentationtier.form.SportsManForm;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class SportsManControllerTest {

    private MockMvc mockMvc;
    @Mock
    private SportsManService sportsManService;
    @Mock
    private NewsService newsService;
    @Mock
    private ManagementService managementService;
    @Mock
    private ActivityService activityService;
    @InjectMocks
    private SportsManController sportsManController;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sportsManController).build();
        final Authentication authentication = new TestingAuthenticationToken("laurent@gmail.com", "test");
        final SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void getSportsMen() throws Exception {
        SportsMan sportsMan = new SportsMan();
        List<SportsMan> all = Arrays.asList(sportsMan);
        given(sportsManService.getAllUser()).willReturn(all);
        mockMvc.perform(get("/sportsmans"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/users"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("allUsers","all","searchUserForm"));
    }

    @Test
    public void createSportsMan() throws Exception {
        mockMvc.perform(get("/signUp"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("global/signUp"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists(
                        "sportsManForm"));
    }

    @Test
    public void saveSportsMan() throws Exception {
        SportsMan current = new SportsMan();
        current.setEmail("test@gmail.com");
        current.setId(1L);
        SportsManForm sportsManForm = new SportsManForm();
        sportsManForm.setFirstname("Test");
        sportsManForm.setMail("test@gmail.com");
        sportsManForm.setDescription("complete");
        sportsManForm.setWeight(70.0);
        sportsManForm.setDateofBirth("2010-05-22");
        sportsManForm.setPassword("testtesttest");
        sportsManForm.setConfirmPassword("wrongwrong");
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(current);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/saveUser",sportsManForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("global/signUp"));
        sportsManForm.setLastname("testLastName");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/saveUser",sportsManForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("global/signUp"));
        sportsManForm.setDateofBirth("20101-05-22");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/saveUser",sportsManForm))
                .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("global/signUp"));
        sportsManForm.setDateofBirth("1900-05-22");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/saveUser",sportsManForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("global/signUp"));
        sportsManForm.setDateofBirth("1990-05-22");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/saveUser",sportsManForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("global/signUp"));
        sportsManForm.setId(2L);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/saveUser",sportsManForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("global/signUp"));
    }

    @Test
    public void updateSportsManForm() throws Exception {
        SportsMan current = new SportsMan();
        current.setDateOfBirth(LocalDate.of(1990,05,15));
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(current);
        mockMvc.perform(get("/user/update").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists(
                        "sportsManForm"))
                .andExpect(view().name("sportsman/updateUser"));
    }

    @Test
    public void updateSportsMan() throws Exception {
        SportsMan current = new SportsMan();
        SportsManForm sportsManForm = new SportsManForm();
        sportsManForm.setFirstname("Test");
        sportsManForm.setMail("test@gmail.com");
        sportsManForm.setDescription("complete");
        sportsManForm.setWeight(70.0);
        sportsManForm.setDateofBirth("2010-05-22");
        sportsManForm.setPassword("testtesttest");
        sportsManForm.setConfirmPassword("wrongwrong");
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(current);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/user/updateuser", sportsManForm).principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/updateUser"));
        sportsManForm.setLastname("testLastName");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/user/updateuser",sportsManForm).principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/updateUser"));
        sportsManForm.setDateofBirth("20101-05-22");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/user/updateuser",sportsManForm).principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/updateUser"));
        sportsManForm.setDateofBirth("1900-05-22");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/user/updateuser", sportsManForm).principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/updateUser"));
        sportsManForm.setDateofBirth("1990-05-22");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/user/updateuser", sportsManForm).principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/updateUser"));
        sportsManForm.setConfirmPassword("testtesttest");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/user/updateuser", sportsManForm).principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/details"));
    }

    @Test
    public void sportsManOwnDetails() throws Exception {
        SportsMan sportsMan = new SportsMan();
        List<SportsMan> applications = new ArrayList<>();
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(sportsMan);
        when(this.managementService.getPromotionCandidates()).thenReturn(applications);
        mockMvc.perform(get("/user/details").principal(SecurityContextHolder.getContext() .getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/userDetails"))
                .andExpect(model().size(4))
                .andExpect(model().attributeExists("sportsMan", "allUsers", "statistics", "empty"));
        applications.add(sportsMan);
        mockMvc.perform(get("/user/details").principal(SecurityContextHolder.getContext() .getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/userDetails"))
                .andExpect(model().size(4))
                .andExpect(model().attributeExists("sportsMan", "allUsers", "statistics", "empty"));
        verify(sportsManService, times(7)).findCurrentUser(SecurityContextHolder.getContext().
                getAuthentication().getName());
    }

    @Test
    public void getSportsManDetail() throws Exception {
        SportsMan sportsMan = new SportsMan();
        when(this.sportsManService.findSpecificUser(1L)).thenReturn(sportsMan);
        mockMvc.perform(get("/sportsman?id=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/externDetails"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("sportsMan"));
        verify(sportsManService, times(1)).findSpecificUser(1L);
    }

    @Test
    public void getMessageForm() throws Exception {
        SportsMan current = new SportsMan(), sportsMan = new SportsMan();
        when(this.sportsManService.findSpecificUser(1L)).thenReturn(sportsMan);
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(current);
        mockMvc.perform(get("/user/createMessage?id=1").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/createMessage"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("showInput", "messageForm"));
        List<SportsMan> contacts = Arrays.asList(sportsMan);
        when(this.sportsManService.getAllContacts(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(contacts);
        mockMvc.perform(get("/user/createMessage").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/createMessage"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("showInput", "messageForm", "contacts"));
    }

    @Test
    public void sendMessage() throws Exception {
        mockMvc.perform(post("/user/sendMessage"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/details"));
        verify(sportsManService, times(1)).sendMessage(any(MessageForm.class));

    }

    @Test
    public void getAllMessageSent() throws Exception {
        Message message = new Message();
        List<Message> messages = Arrays.asList(message);
        SportsMan current = new SportsMan();
        when(sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(current);
        when(sportsManService.findMessages(current,true)).thenReturn(messages);
        mockMvc.perform(get("/user/getMessagesSent").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(2))
                .andExpect(view().name("sportsman/getMessages"));
    }

    @Test
    public void getAllReceivedMessages() throws Exception {
        Message message = new Message();
        List<Message> messages = Arrays.asList(message);
        SportsMan current = new SportsMan();
        when(sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(current);
        when(sportsManService.findMessages(current,true)).thenReturn(messages);
        mockMvc.perform(get("/user/getReceivedMessages").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(2))
                .andExpect(view().name("sportsman/getMessages"));
    }

    @Test
    public void notifications() throws Exception {
        SportsMan current = new SportsMan();
        News news = new News();
        List<News> notices = Arrays.asList(news);
        when(sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(current);
        when(sportsManService.getNotifications(current)).thenReturn(notices);
        mockMvc.perform(get("/user/notifications").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/notifications"));
    }

    @Test
    public void checkNotification() throws Exception {
        mockMvc.perform(get("/user/clearnotification?id=1").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/notifications"));
        verify(newsService,times(1)).checkNews(1L);
    }

    @Test
    public void getContacts() throws Exception {
        SportsMan sportsMan = new SportsMan(), current = new SportsMan();
        List<SportsMan> sportsMEN = Arrays.asList(sportsMan);
        when(sportsManService.getAllContacts(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(sportsMEN);
        mockMvc.perform(get("/user/contacts").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/contacts"));
        verify(sportsManService,times(1)).getAllContacts(SecurityContextHolder.getContext().getAuthentication().getName());

    }

    @Test
    public void getUnknowUsers() throws Exception {
        SportsMan sportsMan = new SportsMan(), current = new SportsMan();
        List<SportsMan> sportsMEN = Arrays.asList(sportsMan);
        when(sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(current);
        when(sportsManService.getPotentialContacts(current)).thenReturn(sportsMEN);
        mockMvc.perform(get("/user/findNewUsers").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/users"));
        verify(sportsManService,times(1)).getPotentialContacts(current);
    }

    @Test
    public void getRegisteredEvents() throws Exception {
        SportsMan current = new SportsMan();
        when(sportsManService.findSpecificUser(1L)).thenReturn(current);
        mockMvc.perform(get("/user/getRegisteredEvents?id=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(view().name("sportsman/userParticipatedEvents"));
        verify(sportsManService,times(1)).findSpecificUser(1L);
    }

    @Test
    public void addContact() throws Exception {
        SportsMan current = new SportsMan();
        when(sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(current);
        mockMvc.perform(get("/user/addContact?id=1").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/findNewUsers"));
    }

    @Test
    public void removeContact() throws Exception {
        SportsMan current = new SportsMan();
        when(sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(current);
        mockMvc.perform(get("/user/removeContact?id=1").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/contacts"));
    }

    @Test
    public void applyAsConfirmedUser() throws Exception {
        SportsMan current = new SportsMan();
        when(sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(current);
        mockMvc.perform(get("/user/applyAsConfirmedUser").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/details"));
        verify(managementService,times(1)).applyForConfirmedRole(current);
    }

    @Test
    public void noteUser() throws Exception {
        NotationForm notationForm = new NotationForm();
        notationForm.setNotation(0.2);
        SportsMan sportsMan = new SportsMan(), current = new SportsMan();
        Activity activity = new Activity();
        when(sportsManService.findSpecificUser(1L)).thenReturn(sportsMan);
        when(activityService.getSpecificActivity(1L)).thenReturn(activity);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/factory/noteuser?idActivity=1&idUser=1", notationForm))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/factory/ownactivity?id=1"));
        verify(sportsManService,times(1)).setResultForEventToParticipant(activity, sportsMan, notationForm.getNotation());
    }
}