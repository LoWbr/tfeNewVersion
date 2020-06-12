package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.NewsService;
import gradation.implementation.businesstier.service.contractinterface.RoleService;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.NewsRepository;
import gradation.implementation.datatier.repositories.SportsManRepository;
import gradation.implementation.presentationtier.form.SearchNewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@ConditionalOnProperty(name="app.service", havingValue = "main")
@Service
public class NoticeServiceImplementation implements NewsService {

    private NewsRepository newsRepository;

    private SportsManRepository sportsManRepository;

    private RoleService roleService;

    @Autowired
    public NoticeServiceImplementation(NewsRepository newsRepository, SportsManRepository sportsManRepository,
                                       RoleService roleService){
        this.newsRepository = newsRepository;
        this.sportsManRepository = sportsManRepository;
        this.roleService = roleService;
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
    public void returnApplicationEventNew(Activity activity, SportsMan sportsMan, NewsType newsType) {
        String content = sportsMan.getFirstName() + " apply your the activity.";
        News supplyToCreator = new News(activity.getCreator(),sportsMan,activity, newsType,content, false);
        this.newsRepository.save(supplyToCreator);
    }

    @Override
    public void returnApplicationNew(SportsMan sportsMan, NewsType newsType) {
        String content = sportsMan.getFirstName() + " applied for the the confirmed Role.";
        for (SportsMan administrator :sportsManRepository.selectAuthorityUsers(roleService.findAdministrator())) {
            News supplyToAdmin = new News(administrator,sportsMan,null, newsType, content,false);
            this.newsRepository.save(supplyToAdmin);
        }
    }

    @Override
    public void returnApplicationResultNewOrLevelUpNew(SportsMan sportsMan, SportsMan validator, NewsType newsType) {
        String content;
        if(newsType.name().equals("VALIDATED_REQUEST")){
            content = "The administrator has accepted your application";
            News answerToAdmin = new News(sportsMan,validator,null,newsType, content,false);
            this.newsRepository.save(answerToAdmin);
        }
        else if(newsType.name().equals("NEGATIVE_REQUEST")){
            content = "The administrator rejected your application";
            News answerToAdmin = new News(sportsMan,validator,null,newsType,content, false);
            this.newsRepository.save(answerToAdmin);
        }
        else{//LEVEL UP
            content = "You have passed to the next Level: " + sportsMan.getLevel().getName();
            News announceLevelUp = new News(sportsMan,null,null,newsType,content, false);
            this.newsRepository.save(announceLevelUp);
        }

    }

    @Override
    public void returnCancelledApplictionNewOrCloseEventNew(SportsMan sportsMan, Activity activity, NewsType newsType) {
        String content = "";
        if(newsType.name().equals("CANCELLED_EVENT")){
            content = "The activity was cancelled by the administrator";
            News announceToCreator = new News(activity.getCreator(), sportsMan, activity, newsType,content, false);
            this.newsRepository.save(announceToCreator);
            for (SportsMan registered : activity.getParticipants()) {
                News announceToRegistered = new News(registered, sportsMan, activity, newsType, content,false);
                this.newsRepository.save(announceToRegistered);
            }
        }
        else{
            content = "The event is now closed";
            for (SportsMan registered : activity.getParticipants()) {
                News announceToRegistered = new News(registered, activity.getCreator(), activity, newsType,content, false);
                this.newsRepository.save(announceToRegistered);
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
        this.newsRepository.save(answerFromBuyer);
    }

    @Override
    public void returnCommentEventNew(SportsMan sportsMan, Activity activity, NewsType newsType) {
        String content = sportsMan.getFirstName() + " has commented your activity";
        News announceToCreator = new News(activity.getCreator(),sportsMan,activity,newsType,content, false);
        this.newsRepository.save(announceToCreator);
    }

    @Override
    public List<News> getByUser(SportsMan currentUser) {
        return this.newsRepository.findByUser(currentUser);
    }

    @Override
    public void returnSendMessageNew(Message message, NewsType newsType) {
        for (SportsMan sportsman:message.getAddressees()) {
            String content = message.getContent();
            News announceToCreator = new News(sportsman,message.getAuthor(),null,newsType, content,false);
            this.newsRepository.save(announceToCreator);
        }
    }

    @Override
    public void returnAbandonmentNew(SportsMan sportsMan, Activity activity, NewsType newsType) {
        String content = sportsMan.getFirstName() + " left the activity";
        News answerToBuyer = new News(activity.getCreator(),sportsMan,activity,newsType, content, false);
        this.newsRepository.save(answerToBuyer);
    }

    @Override
    public void checkNews(Long idNews) {
        News notification = this.newsRepository.findbyNewsId(idNews);
        notification.setSeen(true);
        this.newsRepository.save(notification);
    }

}
