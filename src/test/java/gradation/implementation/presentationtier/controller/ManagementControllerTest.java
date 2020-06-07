package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.*;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.presentationtier.form.ActivityTypeForm;
import gradation.implementation.presentationtier.form.LevelForm;
import gradation.implementation.presentationtier.form.SearchNewForm;
import gradation.implementation.presentationtier.form.TopicForm;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class ManagementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ActivityService activityService;
    @Mock
    private SportsManService sportsManService;
    @Mock
    private ManagementService managementService;
    @Mock
    private NewsService newsService;

    @Mock
    private ActivitySettingService activitySettingService;

    @InjectMocks
    private ManagementController managementController;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(managementController).build();
        final Authentication authentication = new TestingAuthenticationToken("laurent.weber", "test");
        final SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void manageUserSetting() throws Exception {
        SportsMan sp1 = new SportsMan();
        SportsMan sp2 = new SportsMan();
        List<SportsMan> allUsers = Arrays.asList(sp1,sp2);
        List<SportsMan> allCandidates = Arrays.asList(sp1);
        when(sportsManService.getAllUser()).thenReturn(allUsers);
        when(managementService.getPromotionCandidates()).thenReturn(allCandidates);
        mockMvc.perform(get("/manage/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("management/manageUsers"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("allUsers","allCandidates", "topicForm"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void manageEventSetting() throws Exception {
        Activity activity1 = new Activity();
        List<Activity> allActivities = Arrays.asList(activity1);
        when(activityService.getAllActivities()).thenReturn(allActivities);
        mockMvc.perform(get("/manage/activities"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("management/manageEvents"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("allActivities"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void cancel() throws Exception {
        Activity activity1 = new Activity();
        when(activityService.getSpecificActivity((long) 1)).thenReturn(activity1);
        doNothing().when(activityService).cancelOrActivateActivity(activity1,false);
        doNothing().when(newsService).returnCancelledApplictionNewOrCloseEventNew(activity1, NewsType.CANCELLED_EVENT);
        mockMvc.perform(get("/manage/activities/cancel?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/activities"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void open() throws Exception {
        Activity activity1 = new Activity();
        when(activityService.getSpecificActivity((long) 1)).thenReturn(activity1);
        doNothing().when(activityService).cancelOrActivateActivity(activity1,true);
        mockMvc.perform(get("/manage/activities/open?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/activities"));
    }

    @Test
    public void block() throws Exception {
        Activity activity = new Activity();
        activity.setOpen(true);
        List<Activity> activities = Arrays.asList(activity);
        SportsMan sportsMan = new SportsMan();
        sportsMan.setId(1L);
        sportsMan.setBlocked(false);
        sportsMan.setCreatedActivities(activities);
        when(sportsManService.findSpecificUser(1L)).thenReturn(sportsMan);
        mockMvc.perform(get("/manage/users/block?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/users"));
        verify(sportsManService, times(2)).findSpecificUser(1L);
        verify(sportsManService, times(1)).blockOrUnblock(sportsMan,true);
        verify(activityService, times(1)).cancelOrActivateActivity(activity,false);
    }

    @Test
    public void unblock() throws Exception {
        Activity activity = new Activity();
        activity.setOpen(true);
        List<Activity> activities = Arrays.asList(activity);
        SportsMan sportsMan = new SportsMan();
        sportsMan.setId(1L);
        sportsMan.setBlocked(false);
        sportsMan.setCreatedActivities(activities);
        when(sportsManService.findSpecificUser(1L)).thenReturn(sportsMan);
        mockMvc.perform(get("/manage/users/unblock?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/users"));
        verify(sportsManService, times(2)).findSpecificUser(1L);
        verify(sportsManService, times(1)).blockOrUnblock(sportsMan,false);
        verify(activityService, times(1)).cancelOrActivateActivity(activity,true);

    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void refusePromotionUser() throws Exception {
        SportsMan sportsMan = new SportsMan();
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(sportsMan);
        mockMvc.perform(get("/manage/users/promote?id=1").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/users"));

    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void promoteUser() throws Exception {
        SportsMan sportsMan = new SportsMan();
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(sportsMan);
        mockMvc.perform(get("/manage/users/deniepromote?id=1").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/users"));
    }

    @Test
    public void createTopic() throws Exception {
        SportsMan sportsMan = new SportsMan();
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(sportsMan);
        mockMvc.perform(post("/manage/addtopic").principal(SecurityContextHolder.getContext().getAuthentication())
                .param("topicForm.content", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void manageSportsSetting() throws Exception {
        ActivityType type1 = new ActivityType();
        List<ActivityType> allTypes = Arrays.asList(type1);
        when(activitySettingService.getAllActivityTypes()).thenReturn(allTypes);
        mockMvc.perform(get("/manage/types"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("management/setSportsSetting"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("activityTypes"));
    }

    @Test
    public void manageActivityTypeForm() throws Exception {
        ActivityType activityType = new ActivityType();
        when(activitySettingService.findSpecificActivityType(1L)).thenReturn(activityType);
        mockMvc.perform(get("/manage/updatetypeform?id=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("management/settingUpdatePage"))
                .andExpect(model().size(1));
    }

    @Test
    public void manageActivityTypeFormAdd() throws Exception {
        mockMvc.perform(get("/manage/addtypeform"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("management/settingUpdatePage"))
                .andExpect(model().size(1));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void updateType() throws Exception {
        ActivityType type1 = new ActivityType();
        type1.setId(1L);
        type1.setMet(2.5f);
        type1.setName("tttttttttttttt");
        ActivityTypeForm form = new ActivityTypeForm(type1);
        when(activitySettingService.findSpecificActivityType(1L)).thenReturn(type1);
        mockMvc.perform(post("/manage/types/update"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("management/settingUpdatePage"));
        form.setName("Testtesttest");
        form.setMet(2.6f);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/manage/types/update",form))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/types"));

    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void addType() throws Exception {
        ActivityTypeForm form = new ActivityTypeForm();
        mockMvc.perform(post("/manage/types/add"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("management/settingUpdatePage"));
        form.setName("Testtesttest");
        form.setMet(2.6f);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/manage/types/add",form))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/types"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void manageLevelsSetting() throws Exception {
        Level level = new Level();
        List<Level> allLevels = Arrays.asList(level);
        when(activitySettingService.getAllLevels()).thenReturn(allLevels);
        mockMvc.perform(get("/manage/levels"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("management/setLevelsSetting"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("activityLevels"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void manageLevelForm() throws Exception {
        Level level = new Level();
        when(activitySettingService.findSpecificLevel(1L)).thenReturn(level);
        mockMvc.perform(get("/manage/updatelevelform?id=1"))
                .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("management/settingUpdatePage"))
                .andExpect(model().size(2));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void updateLevel() throws Exception {
        Level level = new Level();
        LevelForm form = new LevelForm();
        when(activitySettingService.findSpecificLevel((long) 1)).thenReturn(level);
        mockMvc.perform(post("/manage/levels/update"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("management/settingUpdatePage"));
        form.setRatioPoints(0.2f);
        form.setMaximumThreshold(15000);
        form.setName("ttestttesst");
        form.setId(1L);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/manage/levels/update",form))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/levels"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void getHistory() throws Exception {
        mockMvc.perform(get("/manage/history"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(5))
                .andExpect(view().name("management/searchNew"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void getHistoryByFilter() throws Exception {
        SearchNewForm searchNewForm = new SearchNewForm();
        searchNewForm.setNameSportsman("");
        searchNewForm.setNewsType(NewsType.MESSAGE_SEND);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/manage/history/filter", searchNewForm))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(5))
                .andExpect(view().name("management/searchNew"));
    }

}