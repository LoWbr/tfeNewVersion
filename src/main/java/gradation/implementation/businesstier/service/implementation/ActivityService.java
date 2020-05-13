package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.*;
import gradation.implementation.presentationtier.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class ActivityService {

	ActivityRepository activityRepository;
	ActivityTypeRepository activityTypeRepository;
	StatisticRepository statisticRepository;
	CommentRepository commentRepository;
	LevelRepository levelRepository;
	AddressRepository addressRepository;
	NewsService newsService;
	SportsManService sportsManService;

	@Autowired
	public ActivityService(ActivityRepository activityRepository, ActivityTypeRepository activityTypeRepository,
			StatisticRepository statisticRepository, CommentRepository commentRepository,
			LevelRepository levelRepository, SportsManService sportsManService,
			AddressRepository addressRepository, NewsService newsService) {
		this.activityRepository = activityRepository;
		this.activityTypeRepository = activityTypeRepository;
		this.statisticRepository = statisticRepository;
		this.commentRepository = commentRepository;
		this.levelRepository = levelRepository;
		this.sportsManService = sportsManService;
		this.addressRepository = addressRepository;
		this.newsService = newsService;
	}

	//----ACTIVITY, QUERIES----//

	//AllEvents
	public Iterable<Activity> getAllActivities(){
		return this.activityRepository.findAll();
	}

	//FindSpecificEvent
	public Activity getSpecificActivity(Long id){
		return this.activityRepository.findSpecific(id);
	}

	//FindByCreator
	public List<Activity> getAllOfTheSameCreator(SportsMan sportsMan){
		return this.activityRepository.findByCreator(sportsMan);
	}

	//FindForSearch
	public List<Activity> findForSearch(SearchActivityForm searchActivityForm){
/*
		return this.activityRepository.filter(searchActivityForm.getActivity(), searchActivityForm.getMinimumLevel());
*/
		return null;
	}

	//SaveEvent
	public void saveActivity(Activity activity){
		this.activityRepository.save(activity);
	}

	//CreateEvent
	public void createActivity(ActivityForm activityForm, SportsMan sportsMan, Address address) throws ParseException {
		Activity activity = new Activity(activityForm, sportsMan, address);
		this.saveActivity(activity);
	}

	//UpdateEvent
	public void updateActivity(Activity activity, ActivityForm activityForm){
		Address address = this.addressRepository.findSpecific(activity.getAddress().getId());
		address.update(activityForm);
		addressRepository.save(address);
		activity.update(activityForm, address);
		this.saveActivity(activity);
	}

	//AddSportsManCandidate
	public void applyAsCandidate(Activity activity, SportsMan sportsMan){
		activity.getCandidate().add(sportsMan);
		this.newsService.returnApplicationEventNew(activity, sportsMan, NewsType.APPLY_FOR_EVENT);
		this.saveActivity(activity);
	}

	public void refuseBuyer(Activity activity, SportsMan sportsMan) {
		activity.getCandidate().remove(sportsMan);
		this.newsService.returnRegistrationResultNew(sportsMan, activity,NewsType.REFUSED_REGISTRATION);
		this.saveActivity(activity);
	}

	//ManagingParticipants
	public void addOrRemoveParticipants(Activity activity, SportsMan sportsMan, boolean flag){
		if(flag){
			activity.addParticipant(sportsMan);
			activity.getCandidate().remove(sportsMan);
			this.newsService.returnRegistrationResultNew(sportsMan, activity,NewsType.VALIDED_REGISTRATION);
			//Rajouter activity et creator
		}
		else{
			activity.removeParticipant(sportsMan);
			this.newsService.returnRegistrationResultNew(sportsMan, activity,NewsType.CANCEL_REGISTRATION);
		}
		this.saveActivity(activity);
	}

	public void participantDropout(Activity activity, SportsMan sportsMan){
		activity.removeParticipant(sportsMan);
		this.saveActivity(activity);
		this.newsService.returnAbandonmentNew(sportsMan, activity,NewsType.PARTICIPANT_DROPOUT);
	}

	//Close Activity
	public void closeActivity(Activity activity) {
		activity.closeEvent();
		newsService.returnCancelledApplictionNewOrCloseEventNew(activity,NewsType.DONE_EVENT);
		/*for (SportsMan sportsman : activity.getRegistered()) {
			double durationInHours = (double) activity.getDuration() / 60;
			Integer energeticExpenditure = Math.toIntExact(Math.round(sportsman.getWeight() * durationInHours * activity.getActivity().getMet()));
			sportsman.setPoints(energeticExpenditure);
			if (sportsman.checkLevelStatus()){
				newsService.returnApplicationResultNewOrLevelUpNew(sportsman,NewsType.LEVEL_UP);
				Byte new_place = sportsman.getLevel().getPlace();
				new_place++;
				sportsman.setLevel(sportsManService.findSpecificLevel(Long.valueOf(new_place)));
			}
			sportsManService.saveUser(sportsman);
			Statistic statistic = new Statistic(sportsman, activity, energeticExpenditure);
			sportsManService.saveStatistic(statistic);
		}*/
		this.saveActivity(activity);
	}

	//SaveComment
	public CommentForm initiateCommentForm(Activity activity, SportsMan sportsMan){
		CommentForm commentForm = new CommentForm(sportsMan, activity);
		return commentForm;
	}

	//SaveComment
	public void createComment(Comment comment){
		this.commentRepository.save(comment);
	}



	public void cancelOrActivateActivity(Activity activity, boolean status){

		activity.setOpen(status);
		this.saveActivity(activity);

	}


	//AllActivityKinds
	public Iterable<ActivityType> getAllActivityTypes(){
		return this.activityTypeRepository.findAll();
	}

	//AllLevels
	public Iterable<Level> getAllLevels(){
		return this.levelRepository.findAll();
	}



	//FindCommentForActivity
	public Iterable<Comment> findCommentsForActivity(Activity activity){
		return this.commentRepository.findForEvent(activity);
	}

	public Address createAddress(ActivityForm activityForm) {
		Address address = new Address(activityForm);
		this.addressRepository.save(address);
		return address;
	}

    public Activity getActivityByName(String name) {
		return this.activityRepository.findByName(name);
    }

    public void inviteContact(Activity specificActivity, SportsMan specificUser) {
		specificActivity.addParticipant(specificUser);
		this.saveActivity(specificActivity);
    }

	public boolean checkAllCotationsForRegistered(Activity specificActivity) {
		if(specificActivity.getRegistered().size() == this.statisticRepository.findByActivity(specificActivity).size()){
			return true;
		}
		else{
			return false;
		}
	}
}
