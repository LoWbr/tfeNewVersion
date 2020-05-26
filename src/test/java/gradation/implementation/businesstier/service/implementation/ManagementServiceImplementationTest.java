package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.ActivitySettingService;
import gradation.implementation.businesstier.service.contractinterface.NewsService;
import gradation.implementation.businesstier.service.contractinterface.RoleService;
import gradation.implementation.datatier.entities.Activity;
import gradation.implementation.datatier.entities.PromotionRequest;
import gradation.implementation.datatier.entities.SportsMan;
import gradation.implementation.datatier.entities.Topic;
import gradation.implementation.datatier.repositories.ActivityRepository;
import gradation.implementation.datatier.repositories.PromotionRequestRepository;
import gradation.implementation.datatier.repositories.StatisticRepository;
import gradation.implementation.datatier.repositories.TopicRepository;
import gradation.implementation.presentationtier.form.TopicForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ManagementServiceImplementationTest {

    @Mock
    private PromotionRequestRepository promotionRequestRepository;
    @Mock
    private TopicRepository topicRepository;
    @Mock
    private NewsService newsService;
    @Mock
    private RoleService roleService;
    @InjectMocks
    private ManagementServiceImplementation managementServiceImplementation;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveRequest() {
        PromotionRequest promotionRequest = new PromotionRequest();
        given(promotionRequestRepository.save(promotionRequest)).willReturn(promotionRequest);
        assertNotNull(promotionRequestRepository.save(promotionRequest));
    }

    @Test
    public void findSpecific() {
        SportsMan sportsMan = new SportsMan();
        sportsMan.setFirstName("Test");
        PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setCandidate(sportsMan);
        given(promotionRequestRepository.findByCandidate(sportsMan)).willReturn(promotionRequest);
        PromotionRequest result = managementServiceImplementation.findSpecific(sportsMan);
        assertEquals(sportsMan.getFirstName(),result.getCandidate().getFirstName());

    }

    @Test
    public void removeRequest() {
        PromotionRequest promotionRequest1 = new PromotionRequest(), promotionRequest2 = new PromotionRequest();
        List<PromotionRequest> promotionRequestList = Arrays.asList(promotionRequest1,promotionRequest2);
        managementServiceImplementation.removeRequest(promotionRequest1);
        verify(promotionRequestRepository,times(1)).delete(any(PromotionRequest.class));
    }

    @Test
    public void getPromotionCandidates() {
        SportsMan sportsMan = new SportsMan();
        PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setCandidate(sportsMan);
        List<PromotionRequest> promotionRequests = Arrays.asList(promotionRequest);
        given(promotionRequestRepository.findAll()).willReturn(promotionRequests);
        List<SportsMan> result = managementServiceImplementation.getPromotionCandidates();
        assertEquals(1, result.size());
    }

    @Test
    public void addTopic() {
        TopicForm topicForm = new TopicForm();
        SportsMan sportsMan = new SportsMan();
        managementServiceImplementation.addTopic(sportsMan,topicForm);
        verify(topicRepository,times(1)).save(any(Topic.class));
    }

    @Test
    public void applyForConfirmedRole() {
        SportsMan sportsMan = new SportsMan();
        managementServiceImplementation.applyForConfirmedRole(sportsMan);
        verify(roleService,times(1)).findConfirmedRole();
    }

}