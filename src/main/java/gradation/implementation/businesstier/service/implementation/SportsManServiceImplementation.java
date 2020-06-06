package gradation.implementation.businesstier.service.implementation;

import gradation.implementation.businesstier.service.contractinterface.ActivitySettingService;
import gradation.implementation.businesstier.service.contractinterface.NewsService;
import gradation.implementation.businesstier.service.contractinterface.RoleService;
import gradation.implementation.businesstier.service.contractinterface.SportsManService;
import gradation.implementation.datatier.entities.*;
import gradation.implementation.datatier.repositories.MessageRepository;
import gradation.implementation.datatier.repositories.SportsManRepository;
import gradation.implementation.datatier.repositories.StatisticRepository;
import gradation.implementation.presentationtier.form.MessageForm;
import gradation.implementation.presentationtier.form.SearchUserForm;
import gradation.implementation.presentationtier.form.SportsManForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@ConditionalOnProperty(name="app.service", havingValue = "main")
@Service
public class SportsManServiceImplementation implements SportsManService {

    private SportsManRepository sportsManRepository;
    private StatisticRepository statisticRepository;
    private MessageRepository messageRepository;

    private PasswordEncoder passwordEncoder;

    private NewsService newsService;
    private ActivitySettingService activitySettingService;
    private RoleService roleService;

    @Autowired
    public SportsManServiceImplementation(SportsManRepository sportsManRepository, PasswordEncoder passwordEncoder,
                                          StatisticRepository statisticRepository, MessageRepository messageRepository,
                                          NewsService newsService, ActivitySettingService activitySettingService ,
                                          RoleService roleService){
        this.sportsManRepository = sportsManRepository;
        this.statisticRepository = statisticRepository;
        this.messageRepository = messageRepository;
        this.passwordEncoder = passwordEncoder;
        this.newsService = newsService;
        this.activitySettingService = activitySettingService;
        this.roleService = roleService;
    }


    @Override
    public SportsMan findCurrentUser(String mail) {
        return this.sportsManRepository.findSpecific(mail);
    }

    @Override
    public SportsMan findSpecificUser(Long id) {
        return this.sportsManRepository.findSpecific(id);
    }

    @Override
    public Iterable<SportsMan> getAllUser() {
        return this.sportsManRepository.findAll();
    }

    @Override
    public Iterable<SportsMan> getAllExceptConnectedUser(Long id) {

        return this.sportsManRepository.findAllWithoutMe(id);

    }

    @Override
    public Iterable<SportsMan> getAllContacts(String mail) {

        return this.sportsManRepository.findSpecific(mail).getContacts();

    }

    @Override
    public Iterable<SportsMan> getAllNoContats(List<SportsMan> contacts, Long id) {

        return this.sportsManRepository.findNotContacts(contacts, id);

    }

    @Override
    public Iterable<SportsMan> getPotentialContacts(SportsMan sportsMan) {

        if (sportsMan.getContacts().size() == 0) {
            return this.getAllExceptConnectedUser(sportsMan.getId());
        } else {
            return this.getAllNoContats(sportsMan.getContacts(), sportsMan.getId());
        }

    }

    @Override
    public void addOrRemoveContacts(SportsMan sportsMan, SportsMan contact, boolean flag) {

        if(flag){
            sportsMan.addContact(contact);
        }
        else{
            sportsMan.removeContact(contact);
        }
        this.saveUser(sportsMan);

    }

    @Override
    public void saveUser(SportsMan sportsMan) {

        this.sportsManRepository.save(sportsMan);

    }

    @Override
    public Iterable<Statistic> findBySportsMan(SportsMan sportsMan) {

        return this.statisticRepository.findBySportsMan(sportsMan);

    }

    @Override
    public void blockOrUnblock(SportsMan sportsMan, boolean status) {

        sportsMan.setBlocked(status);
        this.saveUser(sportsMan);

    }

    @Override
    public Iterable<SportsMan> selectAuthorityUsers() {

        return this.sportsManRepository.selectAuthorityUsers(roleService.findAdministrator());

    }

    @Override
    public void createUser(SportsManForm sportsManForm) {

        SportsMan sportsMan = new SportsMan(sportsManForm);
        sportsMan.setLevel(activitySettingService.findBeginner());
        sportsMan.addRoles(roleService.findSimplyRole());
        sportsMan.setPassword(passwordEncoder.encode(sportsManForm.getPassword()));
        this.saveUser(sportsMan);

    }

    @Override
    public void updateUser(SportsMan sportsMan, SportsManForm sportsManForm) {

        sportsMan.updateSportsMan(sportsManForm);
        sportsMan.setPassword(passwordEncoder.encode(sportsManForm.getPassword()));
        this.saveUser(sportsMan);

    }

    @Override
    public void promoteUser(SportsMan sportsMan) {

        sportsMan.addRoles(roleService.findConfirmedRole());
        this.saveUser(sportsMan);

    }

    @Override
    public void saveStatistic(Statistic statistic) {

        this.statisticRepository.save(statistic);

    }

    @Override
    public void sendMessage(MessageForm messageForm) {
        Message message = new Message(messageForm);
        this.messageRepository.save(message);
        this.newsService.returnSendMessageNew(message, NewsType.MESSAGE_SEND);

    }

    @Override
    public List<Message> findMessages(SportsMan sportsMan, boolean status) {

        if (status) {
            return this.messageRepository.findByCreator(sportsMan);
        } else {
            return this.messageRepository.findByReceptor(sportsMan);

        }

    }

    @Override
    public List<News> getNotifications(SportsMan currentUser) {
        return this.newsService.getByUser(currentUser);
    }

    @Override
    public List<SportsMan> getAllNotMarked(Activity activity) {

        List<SportsMan> toTreat = new ArrayList<>();
        //All for the event
        List<SportsMan> registered = activity.getParticipants();
        //All who do not have statistic for this event
        for (SportsMan sportsMan: registered) {
            if(this.statisticRepository.findForActivityAndSportsMan(activity, sportsMan).size() == 0){
                toTreat.add(sportsMan);
            }
        }
        return toTreat;

    }

    @Override
    public void setResultForEventToParticipant(Activity activity, SportsMan sportsMan, double notation) {

        if (notation != 0) {
            double durationInHours = (double) activity.getDuration() / 60;
            //Calcul de la dépense énergétique
            Integer energeticExpenditure = Math.toIntExact(Math.round(sportsMan.getWeight() * durationInHours * activity.getTypeActivity().getMet()));
            //Calcul des points acquis : base du ratio du niveau utilisateur, et de la cotation
            Integer earnedPoints = Math.toIntExact((long) (energeticExpenditure * sportsMan.getLevel().getRatioPoints() * notation));
            sportsMan.addPoints(earnedPoints);
            while(sportsMan.checkLevelStatus()){
                Byte new_place = sportsMan.setLevelUp();
                sportsMan.setLevel(activitySettingService.findLevelByPlace(new_place));
                newsService.returnApplicationResultNewOrLevelUpNew(sportsMan,null,NewsType.LEVEL_UP);
            }
            this.saveUser(sportsMan);
            Statistic statistic = sportsMan.generateStatistic(activity, earnedPoints, energeticExpenditure);
            this.saveStatistic(statistic);
            //NEWS à mettre en place pour clôture cotation
        }
        else{
            Integer energeticExpenditure = 0;
            Integer earnedPoints = 0;
            Statistic statistic = sportsMan.generateStatistic(activity, earnedPoints, energeticExpenditure);
            this.saveStatistic(statistic);
            //NEWS à mettre en place pour clôture cotation
        }

    }

    @Override
    public List<SportsMan> getByFilter(SearchUserForm searchUserForm) {
        return this.sportsManRepository.findbyForm(searchUserForm.getFirstName(), searchUserForm.getLastName());
    }

    @Override
    public Message findSpecificMessage(Long id) {
        return this.messageRepository.findSpecific(id);
    }


}
