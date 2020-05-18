package gradation.implementation.businesstier.service.contractinterface;

import gradation.implementation.datatier.entities.*;
import gradation.implementation.presentationtier.form.SearchNewForm;

import java.util.List;

public interface NewsService {

    List<News> findForSearch(SearchNewForm searchNewForm);

    public List<NewsType> getAllNewsType();

    Iterable<News> findAll();

    void saveNew(News news);

    void returnApplicationEventNew(Activity activity, SportsMan sportsMan, NewsType newsType);

    void returnApplicationNew(SportsMan sportsMan, NewsType newsType);

    void returnApplicationResultNewOrLevelUpNew(SportsMan sportsMan, NewsType newsType);

    void returnCancelledApplictionNewOrCloseEventNew(Activity activity, NewsType newsType);

    void returnRegistrationResultNew(SportsMan sportsMan, Activity activity, NewsType newsType);

    void returnCommentEventNew(SportsMan sportsMan, Activity activity, NewsType newsType);

    List<News> getByUser(SportsMan currentUser);

    void returnSendMessageNew(Message message, NewsType newsType);

    void returnAbandonmentNew(SportsMan sportsMan, Activity activity, NewsType newsType);

    void checkNews(Long idNews);

}
