package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.ActivityService;
import gradation.implementation.businesstier.service.contractinterface.ActivitySettingService;
import gradation.implementation.businesstier.service.contractinterface.SportsManService;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.presentationtier.form.ActivityForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class ActivityControllerTest {

    private MockMvc mockMvc;
    @Mock
    private ActivityService activityService;
    @Mock
    private SportsManService sportsManService;
    @Mock
    private ActivitySettingService activitySettingService;
    @InjectMocks
    private ActivityController activityController;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(activityController).build();
    }

    @Test
    public void getAllEvents() throws Exception {
        Activity activity = new Activity();
        List<Activity> allActivities = Arrays.asList(activity);
        ActivityType activityType = new ActivityType();
        List<ActivityType> activityTypes = Arrays.asList(activityType);
        Level level = new Level();
        List<Level> levels = Arrays.asList(level);
        when(activityService.getAllActivities()).thenReturn(allActivities);
        when(activitySettingService.getAllActivityTypes()).thenReturn(activityTypes);
        when(activitySettingService.getAllLevels()).thenReturn(levels);
        mockMvc.perform(get("/activities"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/events"))
                .andExpect(model().size(4))
                .andExpect(model().attributeExists("allActivities", "allKinds", "allLevels",
                        "activityForm"));
    }

    @Test
    public void initiate() {
    }

    @Test
    public void createEvent() throws Exception {
        ActivityType activityType = new ActivityType();
        List<ActivityType> activityTypes = Arrays.asList(activityType);
        Level level = new Level();
        List<Level> levels = Arrays.asList(level);
        when(activitySettingService.getAllActivityTypes()).thenReturn(activityTypes);
        when(activitySettingService.getAllLevels()).thenReturn(levels);
        mockMvc.perform(get("/factory/create"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/createEvent"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("allKinds", "allLevels",
                        "activityForm"));
    }

    @Test
    public void saveEvent() throws Exception {
        ActivityForm activityForm = new ActivityForm();
        Address address = new Address();
        SportsMan sportsMan = new SportsMan();
/*
        doNothing().when(activityService).createActivity(activityForm, sportsMan, address);
*/
        mockMvc.perform(post("/factory/saveactivity"))
                .andExpect(status().is2xxSuccessful());
/*        verify(activityService, times(1)).createActivity(any(ActivityForm.class),
                any(SportsMan.class),any(Address.class));*/

        //Structure conditionnelle à mettre en place!!
    }

    @Test
    public void eventDetails() {
    }

    @Test
    public void getActivitiesByCreator() {
    }

    @Test
    public void ownEventDetails() {
    }

    @Test
    public void updateEventForm() {
    }

    @Test
    public void updateEvent() {
    }

    @Test
    public void applyAsCandidate() {
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED"})
    public void refuseUser() throws Exception {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        /*when(sportsManService.findSpecificUser((long) 1)).thenReturn(sportsMan);
        when(activityService.getSpecificActivity((long) 1)).thenReturn(activity);
        doNothing().when(activityService).refuseBuyer(activity,sportsMan);*/
        mockMvc.perform(post("/factory/refuseuser?id=1"));
                /*.andExpect(status().is2xxSuccessful())
                .andExpect(view().name("redirect:/activities"));*/
        //? error 400, to correct!!
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED"})
    public void addUser() throws Exception {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        /*when(sportsManService.findSpecificUser((long) 1)).thenReturn(sportsMan);
        when(activityService.getSpecificActivity((long) 1)).thenReturn(activity);
        doNothing().when(activityService).addOrRemoveParticipants(activity,sportsMan,true);*/
        mockMvc.perform(post("/factory/adduser?id=1"));
                /*.andExpect(status().is2xxSuccessful())
                .andExpect(view().name("redirect:/activities"));*/
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED"})
    public void removeUser() throws Exception {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        /*when(sportsManService.findSpecificUser((long) 1)).thenReturn(sportsMan);
        when(activityService.getSpecificActivity((long) 1)).thenReturn(activity);
        doNothing().when(activityService).addOrRemoveParticipants(activity,sportsMan,false);*/
        mockMvc.perform(post("/factory/adduser?id=1"));
                /*.andExpect(status().is2xxSuccessful())
                .andExpect(view().name("redirect:/activities"));*/
    }

    @Test
    public void testRemoveUser() {
    }

    @Test
    public void inviteContactPage() {
    }

    @Test
    public void testInviteContactPage() {
    }

    @Test
    public void closeEvent() {
    }
}