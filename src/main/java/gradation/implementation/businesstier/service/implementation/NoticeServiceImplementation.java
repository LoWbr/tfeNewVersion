package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.NewsService;
import gradation.implementation.businesstier.service.implementation.oldversion.SportsManService;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.NewsRepository;
import gradation.implementation.presentationtier.form.SearchNewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImplementation implements NewsService {

    private NewsRepository newsRepository;

    private SportsManService sportsManService;

    @Autowired
    public NoticeServiceImplementation(NewsRepository newsRepository, SportsManService sportsManService){
        this.newsRepository = newsRepository;
        this.sportsManService = sportsManService;
    }

    @Override
    public List<News> findForSearch(SearchNewForm searchNewForm) {
        return this.newsRepository.filter(searchNewForm.getNameSportsman(), searchNewForm.getNewsType());
    }

    @Override
    public List<NewsType> getAllNewsType() {
        List<NewsType> allTypes = new ArrayList<>();
        for (NewsType newsType:NewsType.values()) {
            allTypes.add(newsType);
        }
        return allTypes;
    }

    @Override
    public Iterable<News> findAll() {
        return this.newsRepository.findAll();
    }

    @Override
    public void saveNew(News news) {
        this.newsRepository.save(news);
    }


    @Override
    public void returnApplicationEventNew(Activity activity, SportsMan sportsMan, NewsType newsType) {
        String content = sportsMan.getFirstName() + " apply your the activity.";
        News supplyToCreator = new News(activity.getCreator(),sportsMan,activity, newsType,content, false);
        this.saveNew(supplyToCreator);
    }

    @Override
    public void returnApplicationNew(SportsMan sportsMan, NewsType newsType) {
        String content = sportsMan.getFirstName() + " applied for the the confirmed Role.";
        for (SportsMan administrator :sportsManService.selectAuthorityUsers()) {
            News supplyToAdmin = new News(administrator,sportsMan,null, newsType, content,false);
            this.saveNew(supplyToAdmin);
        }
    }

    @Override
    public void returnApplicationResultNewOrLevelUpNew(SportsMan sportsMan, NewsType newsType) {
        String content;
        if(newsType.name().equals("VALIDATED_REQUEST")){
            content = "The administrator has accepted your application";
            News answerToAdmin = new News(sportsMan,null,null,newsType, content,false);
            this.saveNew(answerToAdmin);
        }
        else if(newsType.name().equals("NEGATIVE_REQUEST")){
            content = "The administrator rejected your application";
            News answerToAdmin = new News(sportsMan,null,null,newsType,content, false);
            this.saveNew(answerToAdmin);
        }
        else{//LEVEL UP
            content = "You have passed to the next Level: " + sportsMan.getLevel().getName();
            News announceLevelUp = new News(sportsMan,null,null,newsType,content, false);
            this.saveNew(announceLevelUp);
        }

    }

    @Override
    public void returnCancelledApplictionNewOrCloseEventNew(Activity activity, NewsType newsType) {
        String content = "";
        if(newsType.name().equals("CANCELLED_EVENT")){
            content = "The activity was cancelled by the administrator";
            News announceToCreator = new News(activity.getCreator(), null, activity, newsType,content, false);
            this.saveNew(announceToCreator);
            for (SportsMan registered : activity.getRegistered()) {
                News announceToRegistered = new News(registered, null, activity, newsType, content,false);
                this.saveNew(announceToRegistered);
            }
        }
        else{
            content = "The event is now closed";
            for (SportsMan registered : activity.getRegistered()) {
                News announceToRegistered = new News(registered, activity.getCreator(), activity, newsType,content, false);
                this.saveNew(announceToRegistered);
            }
        }

    }

    @Override
    public void returnRegistrationResultNew(SportsMan sportsMan, Activity activity, NewsType newsType) {
        String content = "";
        if(newsType.name().equals("REFUSED_REGISTRATION")) {
            content = activity.getCreatorName() + " rejected your demand.";
        }
        else if(newsType.name().equals("VALIDED_REGISTRATION")){
            content = activity.getCreatorName() + " has accepted your demand.";
        }
        else{
            content = activity.getCreatorName() + " creator cancel your participation to the activity";
        }
        News answerFromBuyer = new News(sportsMan, activity.getCreator(), activity, newsType, content, false);
        this.saveNew(answerFromBuyer);
    }

    @Override
    public void returnCommentEventNew(SportsMan sportsMan, Activity activity, NewsType newsType) {
        String content = sportsMan.getFirstName() + " has commented your activity";
        News announceToCreator = new News(activity.getCreator(),sportsMan,activity,newsType,content, false);
        this.saveNew(announceToCreator);
    }

    @Override
    public List<News> getByUser(SportsMan currentUser) {
        return this.newsRepository.findByUser(currentUser);
    }

    @Override
    public void returnSendMessageNew(Message message, NewsType newsType) {
        for (SportsMan sportsman:message.getAddressee()) {
            String content = message.getContent();
            News announceToCreator = new News(sportsman,message.getOriginator(),null,newsType, content,false);
            this.saveNew(announceToCreator);
        }
    }

    @Override
    public void returnAbandonmentNew(SportsMan sportsMan, Activity activity, NewsType newsType) {
        String content = sportsMan.getFirstName() + " left the activity";
        News answerToBuyer = new News(activity.getCreator(),sportsMan,activity,newsType, content, false);
        this.saveNew(answerToBuyer);
    }

    @Override
    public void checkNews(Long idNews) {
        News notification = this.newsRepository.findbyId(idNews);
        notification.setSeen(true);
        this.newsRepository.save(notification);
    }

}
