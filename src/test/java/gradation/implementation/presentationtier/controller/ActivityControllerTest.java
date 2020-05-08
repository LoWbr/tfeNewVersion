package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.ActivityService;
import gradation.implementation.businesstier.service.contractinterface.ActivitySettingService;
import gradation.implementation.businesstier.service.contractinterface.SportsManService;
import gradation.implementation.datatier.entities.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
        mockMvc.perform(get("/events"))
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
        mockMvc.perform(get("/create"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/createEvent"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("allKinds", "allLevels",
                        "activityForm"));
    }

    @Test
    public void saveEvent() {
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
    public void refuseUser() throws Exception {
       /* SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        when(sportsManService.findSpecificUser((long) 1)).thenReturn(sportsMan);
        when(activityService.getSpecificActivity((long) 1)).thenReturn(activity);
        doNothing().when(activityService).addOrRemoveParticipants(activity,sportsMan,false);
        mockMvc.perform(post("/refuseUser?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/events"));*/
    }

    @Test
    public void addUser() {
    }

    @Test
    public void removeUser() {
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