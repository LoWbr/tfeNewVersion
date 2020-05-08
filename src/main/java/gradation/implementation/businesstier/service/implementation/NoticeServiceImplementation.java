package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.NewsService;
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
        News supplyToCreator = new News(activity.getCreator(),sportsMan,activity, newsType, false);
        this.saveNew(supplyToCreator);
    }

    @Override
    public void returnApplicationNew(SportsMan sportsMan, NewsType newsType) {
        for (SportsMan administrator :sportsManService.selectAuthorityUsers()) {
            News supplyToAdmin = new News(administrator,sportsMan,null, newsType, false);
            this.saveNew(supplyToAdmin);
        }
    }

    @Override
    public void returnApplicationResultNewOrLevelUpNew(SportsMan sportsMan, NewsType newsType) {

        if(newsType.name().equals("VALIDATED_REQUEST")||newsType.name().equals("NEGATIVE_REQUEST")){
            News answerToAdmin = new News(sportsMan,null,null,newsType, false);
            this.saveNew(answerToAdmin);
        }
        else {
            News announceLevelUp = new News(sportsMan,null,null,newsType, false);
            this.saveNew(announceLevelUp);
        }

    }

    @Override
    public void returnCancelledApplictionNewOrCloseEventNew(Activity activity, NewsType newsType) {

        if(newsType.name().equals("CANCELLED_EVENT")){
            News announceToCreator = new News(activity.getCreator(), null, activity, newsType, false);
            this.saveNew(announceToCreator);
            for (SportsMan registered : activity.getRegistered()) {
                News announceToRegistered = new News(registered, null, activity, newsType, false);
                this.saveNew(announceToRegistered);
            }
        }
        else{
            for (SportsMan registered : activity.getRegistered()) {
                News announceToRegistered = new News(registered, activity.getCreator(), activity, newsType, false);
                this.saveNew(announceToRegistered);
            }
        }

    }

    @Override
    public void returnRegistrationResultNew(SportsMan sportsMan, Activity activity, NewsType newsType) {
        News answerFromBuyer = new News(sportsMan, activity.getCreator(),activity,newsType, false);
        this.saveNew(answerFromBuyer);
    }

    @Override
    public void returnCommentEventNew(SportsMan sportsMan, Activity activity, NewsType newsType) {
        News announceToCreator = new News(activity.getCreator(),sportsMan,activity,newsType, false);
        this.saveNew(announceToCreator);
    }

    @Override
    public List<News> getByUser(SportsMan currentUser) {
        return this.newsRepository.findByUser(currentUser);
    }

    @Override
    public void returnSendMessageNew(Message message, NewsType newsType) {
        for (SportsMan sportsman:message.getAddressee()) {
            News announceToCreator = new News(sportsman,message.getOriginator(),null,newsType, false);
            this.saveNew(announceToCreator);
        }
    }

    @Override
    public void returnAbandonmentNew(SportsMan sportsMan, Activity activity, NewsType newsType) {
        News answerToBuyer = new News(activity.getCreator(),sportsMan,activity,newsType, false);
        this.saveNew(answerToBuyer);
    }

}
