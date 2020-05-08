package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.implementation.ActivityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class GlobalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private GlobalController globalController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(globalController).build();
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
    public void search() {
    }

    @Test
    public void error() throws Exception {
        mockMvc.perform(get("/403"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("global/accessdenied"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("msg"));
    }
}