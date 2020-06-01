package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.ActivityService;
import gradation.implementation.businesstier.service.contractinterface.ActivitySettingService;
import gradation.implementation.businesstier.service.contractinterface.NewsService;
import gradation.implementation.businesstier.service.contractinterface.SportsManService;
import gradation.implementation.datatier.entities.Activity;
import gradation.implementation.datatier.entities.NewsType;
import gradation.implementation.datatier.entities.SportsMan;
import gradation.implementation.presentationtier.form.CommentForm;
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

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class CommentControllerTest {

    private MockMvc mockMvc;
    @Mock
    private ActivityService activityService;
    @Mock
    private SportsManService sportsManService;
    @Mock
    private NewsService newsService;
    @Mock
    private ActivitySettingService activitySettingService;
    @InjectMocks
    private CommentController commentController;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        final Authentication authentication = new TestingAuthenticationToken("laurent.weber", "test");
        final SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void createComment() throws Exception {
        Activity activity = new Activity();
        SportsMan sportsMan = new SportsMan();
        when(this.sportsManService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(sportsMan);
        when(activityService.getSpecificActivity(1L)).thenReturn(activity);
        mockMvc.perform(get("/user/createcomment?id=1").principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("activity/createComment"))
                .andExpect(model().size(2));
    }

    @Test
    public void addComment() throws Exception {
        Activity activity = new Activity();
        activity.setId(1L);
        SportsMan sportsMan = new SportsMan();
        sportsMan.setId(1L);
        CommentForm commentForm = new CommentForm(sportsMan,activity);
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/user/addcomment", commentForm).principal(SecurityContextHolder.getContext().getAuthentication()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/activity?id=1"));
        verify(activitySettingService, times(1)).createComment(any(CommentForm.class));
       /* verify(newsService, times(1)).returnCommentEventNew(any(SportsMan.class), any(Activity.class),
               NewsType.COMMENTED_EVENT);*/
    }
}