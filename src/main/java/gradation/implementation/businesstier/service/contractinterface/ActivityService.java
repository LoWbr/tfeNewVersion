package gradation.implementation.businesstier.service.contractinterface;

import gradation.implementation.datatier.entities.*;
import gradation.implementation.presentationtier.form.ActivityForm;
import gradation.implementation.presentationtier.form.CommentForm;
import gradation.implementation.presentationtier.form.SearchActivityForm;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface ActivityService {

    Iterable<Activity> getAllActivities();

    Iterable<Activity> OnTime(LocalDate now);

    Activity getSpecificActivity(Long id);

    List<Activity> getAllOfTheSameCreator(SportsMan sportsMan);

    List<Activity> findForSearch(SearchActivityForm searchActivityForm);

    void saveActivity(Activity activity);

    void createActivity(ActivityForm activityForm, SportsMan sportsMan, Address address) throws ParseException;

    void updateActivity(Activity activity, ActivityForm activityForm);

    void applyAsCandidate(Activity activity, SportsMan sportsMan);

    void refuseBuyer(Activity activity, SportsMan sportsMan);

    void addOrRemoveParticipants(Activity activity, SportsMan sportsMan, boolean flag);

    void participantDropout(Activity activity, SportsMan sportsMan);

    void closeActivity(Activity activity);

    void cancelOrActivateActivity(Activity activity, boolean status);

    Activity getActivityByName(String name);

    void inviteContact(Activity activity, SportsMan sportsMan);

    boolean checkAllCotationsForRegistered(Activity activity);

}
