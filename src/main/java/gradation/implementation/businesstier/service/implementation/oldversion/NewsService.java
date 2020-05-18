package gradation.implementation.businesstier.service.implementation.oldversion;

import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.*;
import gradation.implementation.presentationtier.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    private NewsRepository newsRepository;
    private PromotionRequestRepository promotionRequestRepository;
    private AdminService adminService;

    @Autowired
    public NewsService(NewsRepository newsRepository, PromotionRequestRepository promotionRequestRepository,
                       AdminService adminService) {
        this.newsRepository = newsRepository;
        this.promotionRequestRepository = promotionRequestRepository;
        this.adminService = adminService;
    }

    //!!! Pas fonctionnel, à corriger
    public List<News> findForSearch(SearchNewForm searchNewForm){
        return this.newsRepository.filter(searchNewForm.getNameSportsman(), searchNewForm.getNewsType());
    }

    // All Kinds : OK
    public List<NewsType> getAllNewsType(){
        List<NewsType> allTypes = new ArrayList<>();
        for (NewsType newsType:NewsType.values()) {
            allTypes.add(newsType);
        }
        return allTypes;
    }
    // All : OK
    public Iterable<News> findAll(){
        return this.newsRepository.findAll();
    }

    // Save
    public void saveNew(News news){
        this.newsRepository.save(news);
    }

    // La globale : point d'entrée des autres services
    // Arguments : target, source, type, event (optionnel)
    public void makeNews(NewsType newsType, @Nullable SportsMan targetOrSource, @Nullable Activity activity){
        switch (newsType.name()){
            case "APPLY_AS_CONFIRMED":
                this.returnApplicationNew(targetOrSource, newsType);
                break;
            case "APPLY_FOR_EVENT":
                this.returnApplicationEventNew(activity, targetOrSource, newsType);
                break;
            case "VALIDATED_REQUEST":
            case "NEGATIVE_REQUEST":
            case "LEVEL_UP":
                this.returnApplicationResultNewOrLevelUpNew(targetOrSource, newsType);
                break;
            case "CANCELLED_EVENT":
            case "DONE_EVENT":
                this.returnCancelledApplictionNewOrCloseEventNew(activity, newsType);
                break;
            case "REFUSED_REGISTRATION":
            case "CANCEL_REGISTRATION":
            case "VALIDED_REGISTRATION":
                this.returnRegistrationResultNew(targetOrSource, activity, newsType);
                break;
            case "COMMENTED_EVENT":
                this.returnCommentEventNew(targetOrSource, activity, newsType);
                break;
            default:
                break;
        }
    }

    public void returnApplicationEventNew(Activity activity, SportsMan sportsMan, NewsType newsType) {
        News supplyToCreator = new News(activity.getCreator(),sportsMan,activity, newsType, null,false);
        this.saveNew(supplyToCreator);
    }

    public void returnApplicationNew(SportsMan sportsMan, NewsType newsType){
        for (SportsMan administrator :adminService.selectAuthorityUsers()) {
            News supplyToAdmin = new News(administrator,sportsMan,null, newsType,null, false);
            this.saveNew(supplyToAdmin);
        }

    }

    public void returnApplicationResultNewOrLevelUpNew(SportsMan sportsMan, NewsType newsType){
        if(newsType.name().equals("VALIDATED_REQUEST")||newsType.name().equals("NEGATIVE_REQUEST")){
            News answerToAdmin = new News(sportsMan,null,null,newsType,null, false);
            this.saveNew(answerToAdmin);
        }
        else {
            News announceLevelUp = new News(sportsMan,null,null,newsType, null,false);
            this.saveNew(announceLevelUp);
        }
    }

    public void returnCancelledApplictionNewOrCloseEventNew(Activity activity, NewsType newsType){
        if(newsType.name().equals("CANCELLED_EVENT")){
            News announceToCreator = new News(activity.getCreator(), null, activity, newsType, null,false);
            this.saveNew(announceToCreator);
            for (SportsMan registered : activity.getRegistered()) {
                News announceToRegistered = new News(registered, null, activity, newsType,null, false);
                this.saveNew(announceToRegistered);
            }
        }
        else{
            for (SportsMan registered : activity.getRegistered()) {
                News announceToRegistered = new News(registered, activity.getCreator(), activity, newsType,null, false);
                this.saveNew(announceToRegistered);
            }
        }
    }

    public void returnRegistrationResultNew(SportsMan sportsMan, Activity activity, NewsType newsType){
            News answerFromBuyer = new News(sportsMan, activity.getCreator(),activity,newsType, null,false);
            this.saveNew(answerFromBuyer);
    }


    public void returnCommentEventNew(SportsMan sportsMan, Activity activity, NewsType newsType){
        News announceToCreator = new News(activity.getCreator(),sportsMan,activity,newsType,null, false);
        this.saveNew(announceToCreator);
    }


    public List<News> getByUser(SportsMan currentUser) {
        return this.newsRepository.findByUser(currentUser);
    }

    public void returnSendMessageNew(Message message, NewsType newsType) {
        for (SportsMan sportsman:message.getAddressee()) {
            News announceToCreator = new News(sportsman,message.getOriginator(),null,newsType,null, false);
            this.saveNew(announceToCreator);
        }
    }

    public void returnAbandonmentNew(SportsMan sportsMan, Activity activity, NewsType newsType) {
        News answerToBuyer = new News(activity.getCreator(),sportsMan,activity,newsType,null, false);
        this.saveNew(answerToBuyer);
    }
}
