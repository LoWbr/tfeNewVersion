package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.RoleService;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.NewsRepository;
import gradation.implementation.datatier.repositories.SportsManRepository;
import gradation.implementation.presentationtier.form.SearchNewForm;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class NoticeServiceImplementationTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private SportsManRepository sportsManRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private NoticeServiceImplementation noticeServiceImplementation;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findForSearch() {
        SearchNewForm searchNewForm = new SearchNewForm();
        searchNewForm.setNewsType(NewsType.CANCELLED_EVENT);
        searchNewForm.setNameSportsman("Test");
        SportsMan sportsMan = new SportsMan();
        sportsMan.setFirstName("Test");
        News notice = new News();
        notice.setSource(sportsMan);
        notice.setType(NewsType.CANCELLED_EVENT);
        List<News> notices = Arrays.asList(notice);
        given(newsRepository.filter(searchNewForm.getNameSportsman(),
                searchNewForm.getNewsType())).willReturn(notices);
        List<News> result = noticeServiceImplementation.findForSearch(searchNewForm);
        assertEquals(notices, result);
    }

    @Test
    public void getAllNewsType() {
        assertEquals(13, noticeServiceImplementation.getAllNewsType().size());
    }

    @Test
    public void findAll() {
        News news = new News(), news1 = new News();
        Iterable<News> allNews = Arrays.asList(news, news1);

        given(newsRepository.findAll()).willReturn(allNews);
        Iterable<News> result = noticeServiceImplementation.findAll();

        assertEquals(allNews, result);
        verify(newsRepository, times(1)).findAll();
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    public void returnApplicationEventNew() {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        News news = new News();
        given(newsRepository.save(news)).willReturn(news);
        noticeServiceImplementation.returnApplicationEventNew(activity,sportsMan,NewsType.APPLY_FOR_EVENT);
        assertNotNull(newsRepository.save(news));
    }

    @Test
    public void returnApplicationNew() {
        SportsMan sportsMan = new SportsMan(), sportsMan1 = new SportsMan();
        List<SportsMan> authority = Arrays.asList(sportsMan1);
        News news = new News();
        Role role = new Role();
        given(newsRepository.save(news)).willReturn(news);
        noticeServiceImplementation.returnApplicationNew(sportsMan,NewsType.APPLY_AS_CONFIRMED);
        assertNotNull(newsRepository.save(news));
    }

    @Test
    public void returnApplicationResultNewOrLevelUpNew() {
        SportsMan sportsMan = new SportsMan(), admin = new SportsMan();
        Level level = new Level();
        level.setName("Test");
        sportsMan.setLevel(level);
        News newsforValidatedRequest = new News(), newsforRefusedRequest = new News(),
                newsForLevelUP = new News();
        given(newsRepository.save(newsforValidatedRequest)).willReturn(newsforValidatedRequest);
        noticeServiceImplementation.returnApplicationResultNewOrLevelUpNew(sportsMan,admin,NewsType.VALIDATED_REQUEST);
        assertNotNull(newsRepository.save(newsforValidatedRequest));

        given(newsRepository.save(newsforRefusedRequest)).willReturn(newsforRefusedRequest);
        noticeServiceImplementation.returnApplicationResultNewOrLevelUpNew(sportsMan,admin,NewsType.NEGATIVE_REQUEST);
        assertNotNull(newsRepository.save(newsforRefusedRequest));

        given(newsRepository.save(newsForLevelUP)).willReturn(newsForLevelUP);
        noticeServiceImplementation.returnApplicationResultNewOrLevelUpNew(sportsMan,admin,NewsType.LEVEL_UP);
        assertNotNull(newsRepository.save(newsForLevelUP));
    }

    @Test
    public void returnCancelledApplictionNewOrCloseEventNew() {
        News newsCancelled = new News(), newsClosed = new News();
        SportsMan sportsMan = new SportsMan(), creator = new SportsMan();
        Activity activity = new Activity();
        activity.getRegistered().add(sportsMan);
        activity.setCreator(creator);
        given(newsRepository.save(newsCancelled)).willReturn(newsCancelled);
        noticeServiceImplementation.returnCancelledApplictionNewOrCloseEventNew(activity,NewsType.CANCELLED_EVENT);
        assertNotNull(newsRepository.save(newsCancelled));
        given(newsRepository.save(newsClosed)).willReturn(newsClosed);
        noticeServiceImplementation.returnCancelledApplictionNewOrCloseEventNew(activity,NewsType.DONE_EVENT);
        assertNotNull(newsRepository.save(newsClosed));
    }

    @Test
    public void returnRegistrationResultNew() {
        News newsRefused = new News(), newsValided = new News(), newsCancelled = new News();
        SportsMan sportsMan = new SportsMan(), creator = new SportsMan();
        creator.setFirstName("Test");
        Activity activity = new Activity();
        activity.setCreator(creator);
        given(newsRepository.save(newsRefused)).willReturn(newsRefused);
        noticeServiceImplementation.returnRegistrationResultNew(sportsMan,activity,NewsType.REFUSED_REGISTRATION);
        assertNotNull(newsRepository.save(newsRefused));
        given(newsRepository.save(newsValided)).willReturn(newsValided);
        noticeServiceImplementation.returnRegistrationResultNew(sportsMan,activity,NewsType.VALIDED_REGISTRATION);
        assertNotNull(newsRepository.save(newsValided));
        given(newsRepository.save(newsCancelled)).willReturn(newsCancelled);
        noticeServiceImplementation.returnRegistrationResultNew(sportsMan,activity,NewsType.NEGATIVE_REQUEST);
        assertNotNull(newsRepository.save(newsCancelled));
    }

    @Test
    public void returnCommentEventNew(){
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        News news = new News();
        given(newsRepository.save(news)).willReturn(news);
        noticeServiceImplementation.returnCommentEventNew(sportsMan,activity,NewsType.COMMENTED_EVENT);
        assertNotNull(newsRepository.save(news));
    }

    @Test
    public void getByUser() {
        News news = new News();
        SportsMan sportsMan = new SportsMan();
        List<News> allNews = Arrays.asList(news);
        given(newsRepository.findByUser(sportsMan)).willReturn(allNews);
        List<News> result = noticeServiceImplementation.getByUser(sportsMan);
        assertEquals(allNews, result);
    }

    @Test
    public void returnSendMessageNew() {
        SportsMan sportsMan = new SportsMan();
        List<SportsMan> addressee = Arrays.asList(sportsMan);
        Message message = new Message();
        message.setAddressee(addressee);
        News news = new News();
        given(newsRepository.save(news)).willReturn(news);
        noticeServiceImplementation.returnSendMessageNew(message,NewsType.MESSAGE_SEND);
        assertNotNull(newsRepository.save(news));
    }

    @Test
    public void returnAbandonmentNew() {
        SportsMan sportsMan = new SportsMan();
        Activity activity = new Activity();
        News news = new News();
        given(newsRepository.save(news)).willReturn(news);
        noticeServiceImplementation.returnAbandonmentNew(sportsMan,activity,NewsType.PARTICIPANT_DROPOUT);
        assertNotNull(newsRepository.save(news));

    }

    @Test
    public void checkNews() {
        News news = new News();
        news.setId(1L);
        given(newsRepository.findbyNewsId(1L)).willReturn(news);
        noticeServiceImplementation.checkNews(1L);
        assertTrue(news.isSeen());
    }
}