package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.SportsManService;
import gradation.implementation.datatier.entities.Message;
import gradation.implementation.datatier.entities.SportsMan;
import gradation.implementation.presentationtier.form.MessageForm;
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
    @InjectMocks
    private SportsManController sportsManController;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sportsManController).build();
        final Authentication authentication = new TestingAuthenticationToken("laurent.weber", "test");
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
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("allUsers"));
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
        mockMvc.perform(post("/saveUser"))
                .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("global/signUp"));
    }

    @Test
    public void authWithHttpServletRequest() {
    }

    @Test
    public void updateSportsManForm() {
    }

    @Test
    public void updateSportsMan() {
    }

    @Test
    public void logout() {
    }

    @Test
    public void sportsManOwnDetails() throws Exception {
        SportsMan sportsMan = new SportsMan();
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(sportsMan);
        mockMvc.perform(get("/user/details").principal(SecurityContextHolder.getContext() .getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("sportsman/userDetails"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("sportsMan", "allUsers", "statistics"));
        verify(sportsManService, times(3)).findCurrentUser(SecurityContextHolder.getContext().
                getAuthentication().getName());
    }

    @Test
    public void getSportsManDetail() throws Exception {
        SportsMan sportsMan = new SportsMan();
        when(this.sportsManService.findSpecificUser(1L)).thenReturn(sportsMan);
        mockMvc.perform(get("/user/sportsman?id=1"))
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
    public void notifications() {
    }

    @Test
    public void checkNotification() {
    }

    @Test
    public void getContacts() {
    }

    @Test
    public void getUnknowUsers() {
    }

    @Test
    public void getRegisteredEvents() {
    }

    @Test
    public void addContact() {
    }

    @Test
    public void removeContact() {
    }

    @Test
    public void applyAsConfirmedUser() {
    }

    @Test
    public void noteUser() {
    }
}