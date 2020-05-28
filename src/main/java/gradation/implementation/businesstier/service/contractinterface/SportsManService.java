package gradation.implementation.businesstier.service.contractinterface;

import gradation.implementation.datatier.entities.*;
import gradation.implementation.presentationtier.form.MessageForm;
import gradation.implementation.presentationtier.form.SearchUserForm;
import gradation.implementation.presentationtier.form.SportsManForm;

import java.util.ArrayList;
import java.util.List;

public interface SportsManService {

    public SportsMan findCurrentUser(String mail);

    public SportsMan findSpecificUser(Long id);

    public Iterable<SportsMan> getAllUser();

    public Iterable<SportsMan> getAllExceptConnectedUser(Long id);

    public Iterable<SportsMan> getAllContacts(String mail);

    public Iterable<SportsMan> getAllNoContats(List<SportsMan> contacts, Long id);

    public Iterable<SportsMan> getPotentialContacts(SportsMan sportsMan);

    public void addOrRemoveContacts(SportsMan sportsMan, SportsMan contact, boolean flag);

    public void saveUser(SportsMan sportsMan);

    public Iterable<Statistic> findBySportsMan(SportsMan sportsMan);

    public void blockOrUnblock(SportsMan sportsMan, boolean status);

    public Iterable<SportsMan> selectAuthorityUsers();

    public void createUser(SportsManForm sportsManForm);

    public void updateUser(SportsMan sportsMan, SportsManForm sportsManForm);

    public void promoteUser(SportsMan sportsMan);

    public void saveStatistic(Statistic statistic);

    public void sendMessage(MessageForm messageForm);

    public List<Message> findMessages(SportsMan sportsMan, boolean status);

    public List<News> getNotifications(SportsMan currentUser);

    public List<SportsMan> getAllNotMarked(Activity activity);

    public void setResultForEventToParticipant(Activity activity, SportsMan sportsMan, double notation);

    List<SportsMan> getByFilter(SearchUserForm searchUserForm);
}
