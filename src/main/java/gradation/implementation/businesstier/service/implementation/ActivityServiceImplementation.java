package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.ActivityService;
import gradation.implementation.businesstier.service.contractinterface.ActivitySettingService;
import gradation.implementation.businesstier.service.contractinterface.NewsService;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.ActivityRepository;
import gradation.implementation.datatier.repositories.StatisticRepository;
import gradation.implementation.presentationtier.form.ActivityForm;
import gradation.implementation.presentationtier.form.SearchActivityForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class ActivityServiceImplementation implements ActivityService {

    private ActivityRepository activityRepository;
    private StatisticRepository statisticRepository;

    private NewsService newsService;
    private ActivitySettingService activitySettingService;

    @Autowired
    public  ActivityServiceImplementation(ActivityRepository activityRepository,
                                          StatisticRepository statisticRepository, NewsService newsService,
                                          ActivitySettingService activitySettingService){
        this.activityRepository = activityRepository;
        this.statisticRepository = statisticRepository;
        this.newsService = newsService;
        this.activitySettingService = activitySettingService;

    }

    @Override
    public Iterable<Activity> getAllActivities() {
        return this.activityRepository.findAll();
    }

    @Override
    public Activity getSpecificActivity(Long id) {
        return this.activityRepository.findSpecific(id);
    }

    @Override
    public List<Activity> getAllOfTheSameCreator(SportsMan sportsMan) {
        return this.activityRepository.findByCreator(sportsMan);
    }

    @Override
    public List<Activity> findForSearch(SearchActivityForm searchActivityForm) {
        return this.activityRepository.filter(searchActivityForm.getActivity(), searchActivityForm.getMinimumLevel());
    }

    @Override
    public void saveActivity(Activity activity) {
        this.activityRepository.save(activity);
    }

    @Override
    public void createActivity(ActivityForm activityForm, SportsMan sportsMan, Address address) throws ParseException {
        Activity activity = new Activity(activityForm, sportsMan, address);
        this.saveActivity(activity);
    }

    @Override
    public void updateActivity(Activity activity, ActivityForm activityForm) {
        Address address = this.activitySettingService.findSpecificAddress(activity);
        this.activitySettingService.updateAddress(address, activityForm);
        activity.update(activityForm, address);
        this.saveActivity(activity);
    }

    @Override
    public void applyAsCandidate(Activity activity, SportsMan sportsMan) {
        activity.getCandidate().add(sportsMan);
        this.newsService.returnApplicationEventNew(activity, sportsMan, NewsType.APPLY_FOR_EVENT);
        this.saveActivity(activity);
    }

    @Override
    public void refuseBuyer(Activity activity, SportsMan sportsMan) {
        activity.getCandidate().remove(sportsMan);
        this.newsService.returnRegistrationResultNew(sportsMan, activity,NewsType.REFUSED_REGISTRATION);
        this.saveActivity(activity);
    }

    @Override
    public void addOrRemoveParticipants(Activity activity, SportsMan sportsMan, boolean flag) {
        if(flag){
            activity.addParticipant(sportsMan);
            activity.getCandidate().remove(sportsMan);
            this.newsService.returnRegistrationResultNew(sportsMan, activity,NewsType.VALIDED_REGISTRATION);
        }
        else{
            activity.removeParticipant(sportsMan);
            this.newsService.returnRegistrationResultNew(sportsMan, activity,NewsType.CANCEL_REGISTRATION);
        }
        this.saveActivity(activity);
    }

    @Override
    public void participantDropout(Activity activity, SportsMan sportsMan) {
        activity.removeParticipant(sportsMan);
        this.saveActivity(activity);
        this.newsService.returnAbandonmentNew(sportsMan, activity,NewsType.PARTICIPANT_DROPOUT);
    }

    @Override
    public void closeActivity(Activity activity) {
        activity.closeEvent();
        newsService.returnCancelledApplictionNewOrCloseEventNew(activity,NewsType.DONE_EVENT);
        this.saveActivity(activity);
    }

    @Override
    public void cancelOrActivateActivity(Activity activity, boolean status) {
        activity.setOpen(status);
        this.saveActivity(activity);
    }

    @Override
    public Activity getActivityByName(String name) {
        return null;
    }

    @Override
    public void inviteContact(Activity activity, SportsMan sportsMan) {
        activity.addParticipant(sportsMan);
        this.saveActivity(activity);
    }

    @Override
    public boolean checkAllCotationsForRegistered(Activity activity) {
        if(activity.getRegistered().size() == this.statisticRepository.findByActivity(activity).size()){
            return true;
        }
        else{
            return false;
        }
    }


}
