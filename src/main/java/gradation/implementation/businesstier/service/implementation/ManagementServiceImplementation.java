package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.databasebackup.contract.DataBackUP;
import gradation.implementation.businesstier.service.contractinterface.ManagementService;
import gradation.implementation.businesstier.service.contractinterface.NewsService;
import gradation.implementation.businesstier.service.contractinterface.RoleService;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.PromotionRequestRepository;
import gradation.implementation.datatier.repositories.TopicRepository;
import gradation.implementation.presentationtier.form.TopicForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ConditionalOnProperty(name="app.service", havingValue = "main")
@Service
public class ManagementServiceImplementation implements ManagementService {

    private PromotionRequestRepository promotionRequestRepository;
    private TopicRepository topicRepository;

    private NewsService newsService;
    private RoleService roleService;

    private DataBackUP dataBackUP;

    @Autowired
    public ManagementServiceImplementation(PromotionRequestRepository promotionRequestRepository,
                                           TopicRepository topicRepository, NewsService newsService,
                                           RoleService roleService, DataBackUP dataBackUP ){
        this.promotionRequestRepository = promotionRequestRepository;
        this.topicRepository = topicRepository;
        this.newsService = newsService;
        this.roleService = roleService;
        this.dataBackUP = dataBackUP;
    }

    @Override
    public void saveRequest(PromotionRequest promotionRequest) {
        this.promotionRequestRepository.save(promotionRequest);
    }

    @Override
    public PromotionRequest findSpecific(SportsMan sportsMan) {
        return this.promotionRequestRepository.findByCandidate(sportsMan);
    }

    @Override
    public void removeRequest(PromotionRequest promotionRequest) {
        this.promotionRequestRepository.delete(promotionRequest);
    }

    @Override
    public List<SportsMan> getPromotionCandidates() {
        List<SportsMan> candidates = new ArrayList<>();
        Iterable<PromotionRequest> allPromotions = this.promotionRequestRepository.findAll();
        for (PromotionRequest promotion: allPromotions) {
            candidates.add(promotion.getApplier());
        }
        return candidates;
    }

    @Override
    public void addTopic(SportsMan sportsMan, TopicForm topicForm) {
        Topic topic = new Topic(sportsMan, topicForm);
        this.topicRepository.save(topic);
    }

    @Override
    public List<Topic> getTopics() {
        return this.topicRepository.findRecents();
    }

    @Override
    public void applyForConfirmedRole(SportsMan sportsMan) {
        PromotionRequest promotionRequest = new PromotionRequest(sportsMan,roleService.findConfirmedRole());
        this.saveRequest(promotionRequest);
        newsService.returnApplicationNew(sportsMan,NewsType.APPLY_AS_CONFIRMED);
    }

    @Override
    public void returnDB() {

        try {
            dataBackUP.saveForDownload();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }


}
