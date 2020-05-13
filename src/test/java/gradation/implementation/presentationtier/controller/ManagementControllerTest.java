package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.*;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.presentationtier.form.ActivityTypeForm;
import gradation.implementation.presentationtier.form.LevelForm;
import gradation.implementation.presentationtier.form.TopicForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
    public void block()   {

    }

    @Test
    public void unblock()   {

    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void refusePromotionUser() throws Exception {
        SportsMan sportsMan = new SportsMan();
        PromotionRequest promotionRequest = new PromotionRequest();
        when(sportsManService.findSpecificUser((long) 1)).thenReturn(sportsMan);
        doNothing().when(newsService).returnApplicationResultNewOrLevelUpNew(sportsMan, NewsType.NEGATIVE_REQUEST);
        mockMvc.perform(get("/manage/users/deniepromote?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/users"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void promoteUser() throws Exception {
        SportsMan sportsMan = new SportsMan();
        PromotionRequest promotionRequest = new PromotionRequest();
        when(sportsManService.findSpecificUser((long) 1)).thenReturn(sportsMan);
        doNothing().when(sportsManService).promoteUser(sportsMan);
        doNothing().when(newsService).returnApplicationResultNewOrLevelUpNew(sportsMan, NewsType.VALIDATED_REQUEST);
        mockMvc.perform(get("/manage/users/promote?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/users"));
    }

    @Test
    public void createTopic()   {

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
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("activityTypeForm","activityTypes"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void updateType() throws Exception {
        ActivityType type1 = new ActivityType();
        ActivityTypeForm form = new ActivityTypeForm();
        when(activitySettingService.findSpecificActivityType((long) 1)).thenReturn(type1);
/*
        doNothing().when(activitySettingService).updateType(type1,form);
*/
        mockMvc.perform(post("/manage/types/update?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/types"));

    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void addType() throws Exception {
        ActivityTypeForm form = new ActivityTypeForm();
/*
        doNothing().when(activitySettingService).createType(form);
*/
        mockMvc.perform(post("/manage/types/add"))
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
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("levelForm","activityLevels"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void updateLevel() throws Exception {
        Level level = new Level();
        LevelForm form = new LevelForm();
        when(activitySettingService.findSpecificLevel((long) 1)).thenReturn(level);
/*
        doNothing().when(activitySettingService).updateLevel(form,level);
*/
        mockMvc.perform(post("/manage/levels/update?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manage/levels"));
    }

    @Test
    public void getHistory() {
    }
}