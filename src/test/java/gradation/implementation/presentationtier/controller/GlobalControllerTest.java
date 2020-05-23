package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.ActivityService;
import gradation.implementation.businesstier.service.contractinterface.ActivitySettingService;
import gradation.implementation.businesstier.service.contractinterface.SportsManService;
import gradation.implementation.datatier.entities.Activity;
import gradation.implementation.datatier.entities.ActivityType;
import gradation.implementation.datatier.entities.Level;
import gradation.implementation.datatier.entities.SportsMan;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class GlobalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ActivityService activityService;
    @Mock
    private ActivitySettingService activitySettingService;
    @Mock
    private SportsManService sportsManService;
    @InjectMocks
    private GlobalController globalController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(globalController).build();
        final Authentication authentication = new TestingAuthenticationToken("celine.gilet", "netapsys");
        final SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void getHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("global/home"));
    }

    @Test
    public void signIn() throws Exception {
        mockMvc.perform(get("/signIn"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("global/signIn"))
                .andExpect(model().size(1));
    }

    @Test
    public void contactUs() throws Exception {
        mockMvc.perform(get("/contactUs"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("global/contactUs"));
    }

    @Test
    public void about() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("global/about"));
    }

    @Test
    public void search() throws Exception {
        final SportsMan userExpected = new SportsMan();
        userExpected.setId(1L);
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(userExpected);
        List<ActivityType> activitytypes = Arrays.asList();
        List<Level> levels = Arrays.asList();
        List<Activity> activities = Arrays.asList();
        when(activityService.getAllActivities()).thenReturn(activities);
        when(activitySettingService.getAllLevels()).thenReturn(levels);
        when(activitySettingService.getAllActivityTypes()).thenReturn(activitytypes);
        mockMvc.perform(get("/search").principal(SecurityContextHolder.getContext() .getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(5))
                .andExpect(view().name("global/search"));
    }

    @Test
    public void searchByFilter() throws Exception {
        /*mockMvc.perform(get("/searchbyfilter"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(4))
                .andExpect(view().name("global/search"));*/
    }

    @Test
    public void error() throws Exception {
        mockMvc.perform(get("/403"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/401"));
    }
}