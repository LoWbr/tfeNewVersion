package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.SportsManService;
import gradation.implementation.datatier.entities.SportsMan;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
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
    public void sportsManOwnDetails() {
    }

    @Test
    public void getSportsManDetail() {
    }

    @Test
    public void getMessageForm() {
    }

    @Test
    public void sendMessage() {
    }

    @Test
    public void getAllMessageSent() {
    }

    @Test
    public void getAllReceivedMessages() {
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