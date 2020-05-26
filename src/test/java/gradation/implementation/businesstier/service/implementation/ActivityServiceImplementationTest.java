package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.*;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.*;
import gradation.implementation.presentationtier.form.ActivityForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceImplementationTest {

    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private StatisticRepository statisticRepository;
    @Mock
    private NewsService newsService;
    @Mock
    private ActivitySettingService activitySettingService;
    @InjectMocks
    private ActivityServiceImplementation activityServiceImplementation;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllActivities() {
        Activity activity = new Activity(), activity1 = new Activity();
        Iterable<Activity> activities = Arrays.asList(activity,activity1);

        given(activityRepository.findAll()).willReturn(activities);
        Iterable<Activity> result = activityServiceImplementation.getAllActivities();

        assertEquals(activities, result);
        verify(activityRepository, times(1)).findAll();
        verifyNoMoreInteractions(activityRepository);
    }

    @Test
    public void getSpecificActivity() {
        Long id = 1L;
        Activity activity = new Activity();
        activity.setId(id);
        activity.setName("Activity");

        given(activityRepository.findSpecific(id)).willReturn(activity);
        Activity result = activityServiceImplementation.getSpecificActivity(id);

        assertEquals(activity.getName(), result.getName());
        verify(activityRepository, times(1)).findSpecific(id);
        verifyNoMoreInteractions(activityRepository);
    }

    @Test
    public void getAllOfTheSameCreator() {
        Activity activity = new Activity(), activity1 = new Activity();
        SportsMan sportsMan = new SportsMan();
        activity.setCreator(sportsMan);
        activity1.setCreator(sportsMan);
        List<Activity> activities = Arrays.asList(activity,activity1);

        given(activityRepository.findByCreator(sportsMan)).willReturn(activities);
        List<Activity> result = activityServiceImplementation.getAllOfTheSameCreator(sportsMan);

        assertEquals(2, result.size());
        verify(activityRepository, times(1)).findByCreator(sportsMan);
        verifyNoMoreInteractions(activityRepository);
    }

    /*@Test
    public void findForSearch() {


    }*/

    @Test
    public void saveActivity() {
        Activity activity = new Activity();
        given(activityRepository.save(activity)).willReturn(activity);
        assertNotNull(activityRepository.save(activity));

    }

    @Test
    public void createActivity() throws ParseException {
        Address address = new Address();
        ActivityForm activityForm = new ActivityForm();
        SportsMan sportsMan = new SportsMan();
        activityForm.setName("test");
        activityForm.setPlannedTo("2020-08-08");
        activityForm.setHour("14:15");
        activityServiceImplementation.createActivity(activityForm, sportsMan, address);
        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    public void updateActivity() {
        Address address = new Address();
        Activity activity = new Activity();
        ActivityForm activityForm = new ActivityForm();
        activityForm.setName("test");
        activityForm.setPlannedTo("2020-08-08");
        activityForm.setHour("14:15");
        activityServiceImplementation.updateActivity(activity,activityForm);
        assertEquals(activityForm.getName(), activity.getName());
    }

    @Test
    public void applyAsCandidate() {
        Activity activity = new Activity();
        SportsMan sportsMan = new SportsMan();
        assertEquals(0,activity.getCandidate().size());
        activityServiceImplementation.applyAsCandidate(activity,sportsMan);
        assertEquals(1,activity.getCandidate().size());
    }

    @Test
    public void refuseBuyer() {
        Activity activity = new Activity();
        SportsMan sportsMan = new SportsMan();
        activity.getCandidate().add(sportsMan);
        activityServiceImplementation.refuseBuyer(activity,sportsMan);
        assertEquals(0,activity.getCandidate().size());
    }

    @Test
    public void addOrRemoveParticipants() {
        Activity activity = new Activity();
        SportsMan sportsMan = new SportsMan();
        activity.getCandidate().add(sportsMan);
        activityServiceImplementation.addOrRemoveParticipants(activity,sportsMan,true);
        assertEquals(1,activity.getRegistered().size());
        activityServiceImplementation.addOrRemoveParticipants(activity,sportsMan,false);
        assertEquals(0,activity.getRegistered().size());
    }

    @Test
    public void participantDropout() {
        Activity activity = new Activity();
        SportsMan sportsMan = new SportsMan();
        activity.getRegistered().add(sportsMan);
        activityServiceImplementation.participantDropout(activity,sportsMan);
        assertEquals(0, activity.getRegistered().size());
    }

    @Test
    public void closeActivity() {
        Activity activity = new Activity();
        activityServiceImplementation.closeActivity(activity);
        assertTrue(activity.getOver());
    }

    @Test
    public void cancelOrActivateActivity() {
        Activity activity = new Activity();
        activity.setOpen(true);
        activityServiceImplementation.cancelOrActivateActivity(activity,false);
        assertFalse(activity.getOpen());
        activityServiceImplementation.cancelOrActivateActivity(activity,true);
        assertTrue(activity.getOpen());
    }

    @Test
    public void getActivityByName() {
        assertNull(activityServiceImplementation.getActivityByName("test"));
    }

    @Test
    public void inviteContact() {
        Activity activity = new Activity();
        SportsMan sportsMan = new SportsMan();
        activityServiceImplementation.inviteContact(activity, sportsMan);
        assertEquals(1, activity.getRegistered().size());
    }

    @Test
    public void checkAllCotationsForRegistered() {
        Activity activity = new Activity();
        SportsMan sportsMan = new SportsMan();
        activity.getRegistered().add(sportsMan);
        List<Statistic> allStat = new ArrayList<>();
        given(statisticRepository.findByActivity(activity)).willReturn(allStat);
        assertFalse(activityServiceImplementation.checkAllCotationsForRegistered(activity));
        Statistic statistic = new Statistic();
        statistic.setActivity(activity);
        allStat.add(statistic);
        given(statisticRepository.findByActivity(activity)).willReturn(allStat);
        assertTrue(activityServiceImplementation.checkAllCotationsForRegistered(activity));
    }
}