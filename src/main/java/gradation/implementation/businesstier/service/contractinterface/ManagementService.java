package gradation.implementation.businesstier.service.contractinterface;

import gradation.implementation.datatier.entities.*;
import gradation.implementation.presentationtier.form.ActivityTypeForm;
import gradation.implementation.presentationtier.form.LevelForm;
import gradation.implementation.presentationtier.form.TopicForm;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ManagementService {

    void saveRequest(PromotionRequest promotionRequest);

    PromotionRequest findSpecific(SportsMan sportsMan);

    void removeRequest(PromotionRequest promotionRequest);

    List<SportsMan> getPromotionCandidates();

    void addTopic(SportsMan sportsMan, TopicForm topicForm);

    List<Topic> getTopics();

    public void applyForConfirmedRole(SportsMan sportsMan);

    void returnDB();

}
