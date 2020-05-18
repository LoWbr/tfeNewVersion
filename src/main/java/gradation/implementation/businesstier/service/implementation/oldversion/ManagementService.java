package gradation.implementation.businesstier.service.implementation.oldversion;

import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.*;
import gradation.implementation.presentationtier.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagementService {

    @Autowired
    PromotionRequestRepository promotionRequestRepository;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    LevelRepository levelRepository;

    public void saveRequest(PromotionRequest promotionRequest){
        this.promotionRequestRepository.save(promotionRequest);
    }

    public PromotionRequest findSpecific(SportsMan sportsMan){
       return this.promotionRequestRepository.findByCandidate(sportsMan);
    }

    public void removeRequest(PromotionRequest promotionRequest){
        this.promotionRequestRepository.delete(promotionRequest);
    }

    public void saveNews(News news){
        this.newsRepository.save(news);
    }

    //Contains User
    public List<SportsMan> getPromotionCandidates(){
        List<SportsMan> candidates = new ArrayList<>();
        Iterable<PromotionRequest> allPromotions = this.promotionRequestRepository.findAll();
        for (PromotionRequest promotion: allPromotions) {
            candidates.add(promotion.getCandidate());
        }
        return candidates;
    }

    public void addTopic(SportsMan sportsMan, TopicForm topicForm) {

        Topic topic = new Topic(sportsMan, topicForm);
        this.topicRepository.save(topic);
    }

    public ActivityType findSpecificActivityType(Long id) {
        return this.activityTypeRepository.findSpecific(id);
    }

    public void createType(ActivityTypeForm activityTypeForm){
        ActivityType activityType = new ActivityType();
        activityType.update(activityTypeForm);
        this.saveType(activityType);
    }

    public void updateType(ActivityType activityType, ActivityTypeForm activityTypeForm){
        activityType.update(activityTypeForm);
        this.saveType(activityType);
    }


    public void saveType(ActivityType activityType) {
        this.activityTypeRepository.save(activityType);
    }

    public Level findSpecificLevel(Long id) {
        return this.levelRepository.findSpecific(id);
    }

    //AllLevels
    public Iterable<Level> getAllLevels(){
        return this.levelRepository.findAll();
    }


    public void updateLevel(LevelForm levelForm, Level level){
        level.update(levelForm);
        this.saveLevel(level);
    }

    public void saveLevel(Level level) {
        this.levelRepository.save(level);
    }

}
