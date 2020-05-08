package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.*;
import gradation.implementation.presentationtier.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SportsManService {

	SportsManRepository sportsManRepository;
	StatisticRepository statisticRepository;
	LevelRepository levelRepository;
	RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	ManagementService managementService;
	NewsService newsService;
	private MessageRepository messageRepository;

	@Autowired
	public SportsManService(SportsManRepository sportsManRepository, StatisticRepository statisticRepository,
							LevelRepository levelRepository, RoleRepository roleRepository,
							ManagementService managementService, PasswordEncoder passwordEncoder,
							NewsService newsService, MessageRepository messageRepository) {
		this.sportsManRepository = sportsManRepository;
		this.statisticRepository = statisticRepository;
		this.levelRepository = levelRepository;
		this.roleRepository = roleRepository;
		this.managementService = managementService;
		this.passwordEncoder = passwordEncoder;
		this.newsService = newsService;
		this.messageRepository = messageRepository;
	}

	//FindCurrentUser
	public SportsMan findCurrentUser(String mail){
		return this.sportsManRepository.findSpecific(mail);
	}

	//FindSpecificUser
	public SportsMan findSpecificUser(Long id){
		return this.sportsManRepository.findSpecific(id);
	}

	//AllUsers
	public Iterable<SportsMan> getAllUser(){
		return this.sportsManRepository.findAll();
	}

	//AllExceptCurrent
	public Iterable<SportsMan> getAllExceptConnectedUser(Long id){
		return this.sportsManRepository.findAllWithoutMe(id);
	}

	//AllContacts
	public Iterable<SportsMan> getAllContacts(String mail){
		return this.sportsManRepository.findSpecific(mail).getContacts();
	}

	//AllNotContactUsers
	public Iterable<SportsMan> getAllNoContats(List<SportsMan> contacts, Long id){
		return this.sportsManRepository.findNotContacts(contacts, id);
	}

	//FindAllNoContactsUser
	public Iterable<SportsMan> getPotentialContacts(SportsMan sportsMan){
		if (sportsMan.getContacts().size() == 0) {
			return this.getAllExceptConnectedUser(sportsMan.getId());
		} else {
			return this.getAllNoContats(sportsMan.getContacts(), sportsMan.getId());
		}
	}

	//ManagingContacts
	public void addOrRemoveContacts(SportsMan sportsMan, SportsMan contact, boolean flag){
		if(flag){
			sportsMan.addContact(contact);
		}
		else{
			sportsMan.removeContact(contact);
		}
		this.saveUser(sportsMan);
	}

	//SaveUser
	public void saveUser(SportsMan sportsMan){
		this.sportsManRepository.save(sportsMan);
	}

	//FindbyUser
	public Iterable<Statistic> findBySportsMan(SportsMan sportsMan){
		return this.statisticRepository.findBySportsMan(sportsMan);
	}

	//ApplyForRoleConfirmed
	public void applyForConfirmedRole(SportsMan sportsMan){
		PromotionRequest promotionRequest = new PromotionRequest(sportsMan,findConfirmedRole());
		managementService.saveRequest(promotionRequest);
		newsService.returnApplicationNew(sportsMan,NewsType.APPLY_AS_CONFIRMED);
	}


	public void blockOrUnblock(SportsMan sportsMan, boolean status){
		sportsMan.setBlocked(status);
		this.saveUser(sportsMan);
	}


	public Iterable<SportsMan> selectAuthorityUsers() {
		return this.sportsManRepository.selectAuthorityUsers(findAdministrator());
	}

	//FindSpecificConfirmedRole
	public Role findSimplyRole(){
		return this.roleRepository.find((long) 1);
	}

	public Role findConfirmedRole(){
		return this.roleRepository.find((long) 2);
	}

	public Role findAdministrator(){
		return this.roleRepository.find((long) 3);
	}

	public void createUser(SportsManForm sportsManForm){
		SportsMan sportsMan = new SportsMan(sportsManForm);
		sportsMan.setLevel(findBeginner());
		sportsMan.addRoles(findSimplyRole());
		sportsMan.setPassword(passwordEncoder.encode(sportsManForm.getPassword()));
		this.saveUser(sportsMan);
	}

	public void updateUser(SportsMan sportsMan, SportsManForm sportsManForm){
		sportsMan.updateSportsMan(sportsManForm);
		this.saveUser(sportsMan);
	}

	public void promoteUser(SportsMan sportsMan){
		sportsMan.addRoles(this.findConfirmedRole());
		this.saveUser(sportsMan);
	}





	// A valider!!!!!!!


	//SaveStatistic
	public void saveStatistic(Statistic statistic){
		this.statisticRepository.save(statistic);
	}

	//FindSpecificLevel
	public Level findSpecificLevel(Long id){
		return this.levelRepository.findSpecific(id);
	}

	public Level findBeginner(){
		return this.levelRepository.findSpecific((long) 1);
	}

	//FindAllRole
	public List<Role> findRoles(Long id){
		return this.roleRepository.findForInitialize(id);
	}


    public void sendMessage(MessageForm messageForm) {
		Message message = new Message(messageForm);
		this.messageRepository.save(message);
		this.newsService.returnSendMessageNew(message, NewsType.MESSAGE_SEND);
    }


	public List<Message> findMessages(SportsMan sportsMan, boolean status) {
		if (status) {
			return this.messageRepository.findByCreator(sportsMan);
		} else {
			return this.messageRepository.findByReceptor(sportsMan);

		}
	}

    public List<News> getNotifications(SportsMan currentUser) {
		return this.newsService.getByUser(currentUser);
    }

    public List<SportsMan> getAllNotMarked(Activity specificActivity) {
		List<SportsMan> toTreat = new ArrayList<>();
		//All for the event
		List<SportsMan> registered = specificActivity.getRegistered();
		//All who do not have statistic for this event
		for (SportsMan sportsMan: registered) {
			if(this.statisticRepository.findForActivityAndSportsMan(specificActivity, sportsMan).size() == 0){
				toTreat.add(sportsMan);
			}
		}
		return toTreat;
    }

	public void setResultForEventToParticipant(Activity activity, SportsMan sportsMan, double notation) {
		if (notation != 0) {
			double durationInHours = (double) activity.getDuration() / 60;
			//Calcul de la dépense énergétique
			Integer energeticExpenditure = Math.toIntExact(Math.round(sportsMan.getWeight() * durationInHours * activity.getActivity().getMet()));
			//Calcul des points acquis : base du ratio du niveau utilisateur, et de la cotation
			Integer earnedPoints = Math.toIntExact((long) (energeticExpenditure * sportsMan.getLevel().getRatioPoints() * notation));
			sportsMan.setPoints(earnedPoints);
			if (sportsMan.checkLevelStatus()){// Récursivité pour les niveaux suivant à mettre en place!!
				newsService.returnApplicationResultNewOrLevelUpNew(sportsMan,NewsType.LEVEL_UP);
				Byte new_place = sportsMan.getLevel().getPlace();
				new_place++;
				sportsMan.setLevel(this.findSpecificLevel(Long.valueOf(new_place)));
			}
			this.saveUser(sportsMan);
			Statistic statistic = new Statistic(sportsMan, activity, earnedPoints, energeticExpenditure);
			this.saveStatistic(statistic);
			//NEWS à mettre en place pour clôture cotation
		}
		else{
			Integer energeticExpenditure = 0;
			Integer earnedPoints = 0;
			Statistic statistic = new Statistic(sportsMan, activity, earnedPoints, energeticExpenditure);
			this.saveStatistic(statistic);
			//NEWS à mettre en place pour clôture cotation
		}

	}
}
