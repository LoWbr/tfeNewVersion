package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.ActivityService;
import gradation.implementation.businesstier.service.contractinterface.ActivitySettingService;
import gradation.implementation.businesstier.service.contractinterface.SportsManService;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.presentationtier.form.ActivityForm;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
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
        final Authentication authentication = new TestingAuthenticationToken("laurent.weber", "test");
        final SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
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
        SportsMan sportsMan = new SportsMan();
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(sportsMan);
        ActivityForm activityForm = new ActivityForm();
        activityForm.setDescription("test");
        activityForm.setPlannedTo("2020-04-08");
        activityForm.setHour("06:15");
        activityForm.setDuration((short) 60);
        activityForm.setCountry("test");
        activityForm.setStreet("test");
        activityForm.setCity("test");
        Level level0 = new Level(), level1 = new Level();
        level0.setPlace((byte)1);
        level1.setPlace((byte)0);
        activityForm.setMinimumLevel(level0);
        activityForm.setMaximumLevel(level1);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/factory/saveactivity",activityForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/createEvent"));
        activityForm.setName("TestTEstTEST_TEST");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/factory/saveactivity",activityForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/createEvent"));
        activityForm.setPlannedTo("2020-08-08");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/factory/saveactivity",activityForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/createEvent"));
        activityForm.setHour("14:15");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/factory/saveactivity",activityForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/createEvent"));
        activityForm.setMaximumLevel(level0);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/factory/saveactivity",activityForm).principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/activities"));
    }

    @Test
    public void eventDetails() throws Exception {
        Activity activity = new Activity();
        given(activityService.getSpecificActivity(1L)).willReturn(activity);
        mockMvc.perform(get("/activity?id=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(6))
                .andExpect(view().name("activity/eventDetails"));
    }

    @Test
    public void getActivitiesByCreator() throws Exception {
        Activity activity = new Activity();
        SportsMan sportsMan = new SportsMan();
        List<Activity> activities = Arrays.asList(activity);
        given(activityService.getAllOfTheSameCreator(sportsMan)).willReturn(activities);
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(sportsMan);
        mockMvc.perform(get("/factory/activitiesbycreator").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(4))
                .andExpect(view().name("activity/ownEvents"));
    }

    @Test
    public void ownEventDetails() throws Exception {
        Activity activity = new Activity();
        when(this.activityService.getSpecificActivity(1L))
                .thenReturn(activity);
        mockMvc.perform(get("/factory/ownactivity?id=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(7))
                .andExpect(view().name("activity/ownEventDetails"));
    }

    @Test
    public void updateEventForm() throws Exception {
        Activity activity = new Activity();
        Address address = new Address();
        activity.setAddress(address);
        activity.setPlannedTo(LocalDate.of(2020,06,20));
        activity.setHour(LocalTime.of(15,15,00));
        ActivityType activityType = new ActivityType();
        List<ActivityType> activityTypes = Arrays.asList(activityType);
        Level level = new Level();
        List<Level> levels = Arrays.asList(level);
        when(activityService.getSpecificActivity((long) 1)).thenReturn(activity);
        when(activitySettingService.getAllActivityTypes()).thenReturn(activityTypes);
        when(activitySettingService.getAllLevels()).thenReturn(levels);
        mockMvc.perform(get("/factory/activity/update?id=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(3))
                .andExpect(view().name("activity/updateEvent"));
    }

    @Test
    public void updateEvent() throws Exception {
        ActivityForm activityForm = new ActivityForm();
        activityForm.setDescription("test");
        activityForm.setPlannedTo("2020-04-08");
        activityForm.setHour("06:15");
        activityForm.setDuration((short) 60);
        activityForm.setCountry("test");
        activityForm.setStreet("test");
        activityForm.setCity("test");
        Level level0 = new Level(), level1 = new Level();
        level0.setPlace((byte)1);
        level1.setPlace((byte)0);
        activityForm.setMinimumLevel(level0);
        activityForm.setMaximumLevel(level1);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/factory/updateactivity",activityForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/createEvent"));
        activityForm.setName("TestTEstTEST_TEST");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/factory/updateactivity",activityForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/createEvent"));
        activityForm.setPlannedTo("2020-08-08");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/factory/updateactivity",activityForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/createEvent"));
        activityForm.setHour("14:15");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/factory/updateactivity",activityForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/createEvent"));
        activityForm.setMaximumLevel(level0);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/factory/updateactivity",activityForm))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/activities"));
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED"})
    public void applyAsCandidate() throws Exception {
        SportsMan sportsMan = new SportsMan();
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(sportsMan);
        Activity activity = new Activity();
        given(activityService.getSpecificActivity(1L)).willReturn(activity);
        mockMvc.perform(get("/user/postulate?id=1").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().size(3))
                .andExpect(view().name("redirect:/events"));
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED"})
    public void refuseUser() throws Exception {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        when(sportsManService.findSpecificUser(1L)).thenReturn(sportsMan);
        when(activityService.getSpecificActivity(1L)).thenReturn(activity);
        mockMvc.perform(get("/factory/refuseuser?idActivity=1&idUser=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/factory/managecandidates?id=1"));
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED"})
    public void manageParticipants() throws Exception {
        Activity activity = new Activity();
        SportsMan sportsMan = new SportsMan();
        List<SportsMan> participants = Arrays.asList(sportsMan);
        when(activityService.getSpecificActivity(1L)).thenReturn(activity);
        mockMvc.perform(get("/factory/manageparticipants?id=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(6))
                .andExpect(view().name("activity/usersForEvent"));
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED"})
    public void manageCandidates() throws Exception {
        Activity activity = new Activity();
        SportsMan sportsMan = new SportsMan();
        List<SportsMan> participants = Arrays.asList(sportsMan);
        when(activityService.getSpecificActivity(1L)).thenReturn(activity);
        mockMvc.perform(get("/factory/managecandidates?id=1"))
                .andExpect(model().size(6))
                .andExpect(view().name("activity/usersForEvent"));
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED"})
    public void addUser() throws Exception {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        when(sportsManService.findSpecificUser(1L)).thenReturn(sportsMan);
        when(activityService.getSpecificActivity(1L)).thenReturn(activity);
        mockMvc.perform(get("/factory/adduser?idActivity=1&idUser=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/factory/managecandidates?id=1"));
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED"})
    public void removeUser() throws Exception {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        when(sportsManService.findSpecificUser(1L)).thenReturn(sportsMan);
        when(activityService.getSpecificActivity(1L)).thenReturn(activity);
        mockMvc.perform(get("/factory/removeuser?idActivity=1&idUser=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/factory/manageparticipants?id=1"));
    }

    @Test
    @WithMockUser(roles = {"CONFIRMED"})
    public void userLeave() throws Exception {
        Activity activity = new Activity();
        when(activityService.getSpecificActivity(1L)).thenReturn(activity);
        mockMvc.perform(get("/user/quit?id=1").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/events"));
    }

    @Test
    public void inviteContactPage() throws Exception {
        SportsMan sportsMan = new SportsMan();
        List<SportsMan> contacts = Arrays.asList(sportsMan);
        Activity activity = new Activity();
        given(activityService.getSpecificActivity(1L)).willReturn(activity);
        mockMvc.perform(get("/factory/invitecontactpage?id=1").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/inviteContactToActivity"));
    }

    @Test
    public void inviteContactToEvent() throws Exception {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        given(activityService.getSpecificActivity(1L)).willReturn(activity);
        given(sportsManService.findSpecificUser(1L)).willReturn(sportsMan);
        mockMvc.perform(get("/factory/inviteusertoactivity?idActivity=1&idUser=1").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/invitecontactpage?id=1"));
        verify(activityService, times(1)).inviteContact(activity,sportsMan);
    }

    @Test
    public void closeEvent() throws Exception {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        activity.getRegistered().add(sportsMan);
        Statistic statistic = new Statistic();
        given(activityService.getSpecificActivity(1L)).willReturn(activity);
        mockMvc.perform(get("/factory/close?id=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/setCotationForEvent"));
        given(activityService.checkAllCotationsForRegistered(activity)).willReturn(true);
        mockMvc.perform(get("/factory/close?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/factory/activitiesbycreator"));
    }
}