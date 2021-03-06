package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.*;
import gradation.implementation.datatier.entities.Activity;
import gradation.implementation.datatier.entities.NewsType;
import gradation.implementation.presentationtier.exception.*;
import gradation.implementation.presentationtier.form.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class ActivityController {

    private ActivityService activityService;
    private SportsManService sportsManService;
    private ActivitySettingService activitySettingService;
    private NewsService newsService;


    @Autowired
    public ActivityController(ActivityService activityService, SportsManService sportsManService,
                              ActivitySettingService activitySettingService, NewsService newsService) {
        this.activityService = activityService;
        this.sportsManService = sportsManService;
        this.activitySettingService = activitySettingService;
        this.newsService = newsService;
    }

    @RequestMapping(value ="/activities", method = RequestMethod.GET)
    public String getAllEvents(Model model, Principal principal) {
        if(principal != null){
            model.addAttribute("current", sportsManService.findCurrentUser(principal.getName()));
        }
        model.addAttribute("allActivities", activityService.OnTime(LocalDate.now()));
        return "activity/events";
    }

    @ModelAttribute
    public void initiate(Model model) {
        model.addAttribute("activityForm", new ActivityForm());
        model.addAttribute("allKinds", activitySettingService.getAllActivityTypes());
        model.addAttribute("allLevels", activitySettingService.getAllLevels());
    }

    @RequestMapping(value ="/factory/create", method = RequestMethod.GET)
    public String createEvent(Model model, Principal principal) throws BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        model.addAttribute("activityForm", new ActivityForm());
        model.addAttribute("allKinds", activitySettingService.getAllActivityTypes());
        model.addAttribute("allLevels", activitySettingService.getAllLevels());
        return "activity/createEvent";
    }

    @RequestMapping(value = "/factory/saveactivity", method = RequestMethod.POST)
    public String saveEvent(@Valid @ModelAttribute("activityForm") ActivityForm activityForm, BindingResult bindingResult,
                            Principal principal) throws ParseException {
        if(bindingResult.hasErrors()){
            System.out.println("Errors: " + bindingResult.getErrorCount());
            return "activity/createEvent";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate current = LocalDate.now();
        LocalDate dateInput;
        try{
            dateInput = LocalDate.parse(activityForm.getPlannedTo(), formatter);
        }catch (DateTimeParseException dt){
            bindingResult.rejectValue("plannedTo", "", "Date not valid.");
            return "activity/createEvent";
        }

        LocalTime start = LocalTime.now();
        LocalTime end = LocalTime.parse(activityForm.getHour().concat(":00"));
        Duration duration = Duration.between(start, end);

        if(Period.between(dateInput,current).getDays() > 0 || Period.between(dateInput,current).getMonths() > 0 ||
                Period.between(dateInput,current).getYears() > 0){
            bindingResult.rejectValue("plannedTo","","You have to set a valid date");
            return "activity/createEvent";
        }
        else if(Period.between(dateInput,current).getDays() >= 0 && duration.getSeconds() < 3600){
            bindingResult.rejectValue("hour","","You have to set a valid hour");
            return "activity/createEvent";
        }
        else if(activityForm.getMinimumLevel().getPlace() > activityForm.getMaximumLevel().getPlace() ||
                (activityForm.getMaximumLevel().getPlace() - activityForm.getMinimumLevel().getPlace()) > 1 ){
            bindingResult.rejectValue("maximumLevel", "", "Should be equal or just one level up");
            return "activity/createEvent";
        }
        else if(activityForm.getMinimumLevel().getPlace() != sportsManService.findCurrentUser(principal.getName()).getLevel().getPlace() &&
                (activityForm.getMaximumLevel().getPlace() != sportsManService.findCurrentUser(principal.getName()).getLevel().getPlace()) ) {
            bindingResult.rejectValue("minimumLevel", "", "One of the two levels has to match your level");
            return "activity/createEvent";
        }
        else if(activityService.getActivityByName(activityForm.getName()) != null){
            bindingResult.rejectValue("name", "", "this event already exists");
            return "activity/createEvent";
        }
        else {
            activityService.createActivity(activityForm, sportsManService.findCurrentUser(principal.getName()),
                    activitySettingService.createAddress(activityForm));
        }
        return "redirect:/factory/activitiesbycreator";
    }

    @RequestMapping(value = "/activity{id, error}", method = RequestMethod.GET)
    public String eventDetails(@RequestParam Long id, Model model, Principal principal, @RequestParam(required = false) boolean error) {
        if(principal != null){
            model.addAttribute("sportsMan", sportsManService.findCurrentUser(principal.getName()));
        }
        Activity activity = activityService.getSpecificActivity(id);
        model.addAttribute("activity", activity);//Raccourci encore faisable
        model.addAttribute("participants", activity.getParticipants());
        model.addAttribute("comments", activitySettingService.findCommentsForActivity(activity));
        if(principal != null) {
            if(error){
                model.addAttribute("errorMsg", error);
            }
            model.addAttribute("commentForm",
                    activitySettingService.initiateCommentForm(activityService.getSpecificActivity(id),
                            sportsManService.findCurrentUser(principal.getName())));
        }
        return "activity/eventDetails";
    }

    @RequestMapping(value = "/factory/activitiesbycreator", method = RequestMethod.GET)
    public String getActivitiesByCreator(Model model, Principal principal) throws BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        model.addAttribute("ownCreations",
                activityService.getAllOfTheSameCreator(sportsManService.findCurrentUser(principal.getName())));
        return "activity/ownevents";
    }

    @RequestMapping(value = "/factory/ownactivity{id, error}", method = RequestMethod.GET)
    public String ownEventDetails(@RequestParam Long id, Model model, Principal principal, @RequestParam(required = false) boolean error) throws BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        if(error){
            model.addAttribute("errorMsg", error);
        }
        Activity activity = activityService.getSpecificActivity(id);
        model.addAttribute("activity", activity);//Raccourci encore faisable
        model.addAttribute("candidates", activity.getCandidates());
        model.addAttribute("participants", activity.getParticipants());
        model.addAttribute("comments", activitySettingService.findCommentsForActivity(activity));
        model.addAttribute("commentForm",
                activitySettingService.initiateCommentForm(activityService.getSpecificActivity(id),
                        sportsManService.findCurrentUser(principal.getName())));
        return "activity/ownEventDetails";
    }

    @RequestMapping(value = "/factory/activity/update{id}", method = RequestMethod.GET)
    public String updateEventForm(@RequestParam Long id, Model model, Principal principal) throws CloseOrCancelEventException, BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        if(!activityService.getSpecificActivity(id).checkCreator(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new CreatorNotMatchingException(principal.getName());
        }
        if(!activityService.getSpecificActivity(id).getOpen() || activityService.getSpecificActivity(id).getOver())
        {
            if(activityService.getSpecificActivity(id).getOver())
                throw new CloseOrCancelEventException("CLOSED");
            else
                throw new CloseOrCancelEventException("CANCELLED");
        }
        ActivityForm activityForm = new ActivityForm(activityService.getSpecificActivity(id),
                activityService.getSpecificActivity(id).getAddress());
        model.addAttribute("allKinds", activitySettingService.getAllActivityTypes());
        model.addAttribute("activityForm", activityForm);
        model.addAttribute("allLevels", activitySettingService.getAllLevels());
        return "activity/updateEvent";
    }

    @RequestMapping(value = "/factory/updateactivity", method = RequestMethod.POST)
    public String updateEvent(@Valid @ModelAttribute("activityForm") ActivityForm activityForm, BindingResult
                              bindingResult) {
        if(bindingResult.hasErrors()){
            System.out.println("Errors: " + bindingResult.getErrorCount());
            return "activity/createEvent";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate current = LocalDate.now();
        LocalDate dateInput;
        try{
            dateInput = LocalDate.parse(activityForm.getPlannedTo(), formatter);
        }catch (DateTimeParseException dt){
            bindingResult.rejectValue("plannedTo", "", "Date not valid.");
            return "activity/createEvent";
        }

        LocalTime start = LocalTime.now();
        LocalTime end = LocalTime.parse(activityForm.getHour().concat(":00"));
        Duration duration = Duration.between(start, end);

        if(Period.between(dateInput,current).getDays() > 0 || Period.between(dateInput,current).getMonths() > 0 ||
                Period.between(dateInput,current).getYears() > 0){
            bindingResult.rejectValue("plannedTo","","You have to set a valid date");
            return "activity/createEvent";
        }
        else if(Period.between(dateInput,current).getDays() >= 0 && duration.getSeconds() < 3600){
            bindingResult.rejectValue("hour","","You have to set a valid hour");
            return "activity/createEvent";
        }
        else if(activityForm.getMinimumLevel().getPlace() > activityForm.getMaximumLevel().getPlace()){
            bindingResult.rejectValue("maximumLevel", "", "Should be equal or greater than" +
                    " Minimum Level");
            return "activity/createEvent";
        }
        else if(activityService.getActivityByName(activityForm.getName()) != null){
            bindingResult.rejectValue("name", "", "this event already exists");
            return "activity/createEvent";
        }
        else {
            activityService.updateActivity(activityService.getSpecificActivity(activityForm.getId()), activityForm);
        }

        return "redirect:/activities";
    }

    @RequestMapping(value = "/user/postulate{id}", method = RequestMethod.GET)
    public String applyAsCandidate(@RequestParam(value = "id") Long id, Principal principal) throws CloseOrCancelEventException, BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        if(!activityService.getSpecificActivity(id).getOpen() || activityService.getSpecificActivity(id).getOver())
        {
            if(activityService.getSpecificActivity(id).getOver())
                throw new CloseOrCancelEventException("CLOSED");
            else
                throw new CloseOrCancelEventException("CANCELLED");
        }
        if(activityService.getSpecificActivity(id).getCandidates().contains(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new DoubleRequestException(principal.getName());
        }
        activityService.applyAsCandidate(activityService.getSpecificActivity(id),
                sportsManService.findCurrentUser(principal.getName()));
        return "redirect:/activity?id="+id;
    }

    /*@RequestMapping(value = "/factory/managecandidates{id}", method = RequestMethod.GET)
    public String manageCandidates(@RequestParam Long id, Model model, Principal principal) {
        if(!activityService.getSpecificActivity(id).checkCreator(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new CreatorNotMatchingException(principal.getName());
        }
        model.addAttribute("users",activityService.getSpecificActivity(id).getCandidates());
        model.addAttribute("activity",activityService.getSpecificActivity(id));
        model.addAttribute("status", false);
        return "activity/usersForEvent";
    }

    @RequestMapping(value = "/factory/manageparticipants{id}", method = RequestMethod.GET)
    public String manageParticipants(@RequestParam Long id, Model model, Principal principal) {
        if(!activityService.getSpecificActivity(id).checkCreator(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new CreatorNotMatchingException(principal.getName());
        }
        model.addAttribute("users",activityService.getSpecificActivity(id).getParticipants());
        model.addAttribute("activity",activityService.getSpecificActivity(id));
        model.addAttribute("status", true);
        return "activity/usersForEvent";
    }*/

    @RequestMapping(value = "/factory/refuseuser{idActivity,idUser}", method = RequestMethod.GET)
    public String refuseUser(@RequestParam Long idUser,
                          @RequestParam Long idActivity, Principal principal) throws CloseOrCancelEventException, BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        if(!activityService.getSpecificActivity(idActivity).getOpen() || activityService.getSpecificActivity(idActivity).getOver())
        {
            if(activityService.getSpecificActivity(idActivity).getOver())
                throw new CloseOrCancelEventException("CLOSED");
            else
                throw new CloseOrCancelEventException("CANCELLED");
        }
        if(!activityService.getSpecificActivity(idActivity).checkCreator(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new CreatorNotMatchingException(principal.getName());
        }
        activityService.refuseBuyer(activityService.getSpecificActivity(idActivity),
                sportsManService.findSpecificUser(idUser));
        return "redirect:/factory/ownactivity?id="+idActivity;
    }

    @RequestMapping(value = "/factory/adduser{idActivity,idUser}", method = RequestMethod.GET)
    public String addUser(@RequestParam Long idUser,
                             @RequestParam Long idActivity, Principal principal) throws CloseOrCancelEventException, BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        if(!activityService.getSpecificActivity(idActivity).getOpen() || activityService.getSpecificActivity(idActivity).getOver())
        {
            if(activityService.getSpecificActivity(idActivity).getOver())
                throw new CloseOrCancelEventException("CLOSED");
            else
                throw new CloseOrCancelEventException("CANCELLED");
        }
        if(!activityService.getSpecificActivity(idActivity).checkCreator(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new CreatorNotMatchingException(principal.getName());
        }
        activityService.addOrRemoveParticipants(activityService.getSpecificActivity(idActivity),
                sportsManService.findSpecificUser(idUser),true);
        return "redirect:/factory/ownactivity?id="+idActivity;
    }

    @RequestMapping(value = "/factory/removeuser{idActivity,idUser}", method = RequestMethod.GET)
    public String removeUser(@RequestParam Long idUser,
                             @RequestParam Long idActivity, Principal principal) throws CloseOrCancelEventException, BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        if(!activityService.getSpecificActivity(idActivity).getOpen() || activityService.getSpecificActivity(idActivity).getOver())
        {
            if(activityService.getSpecificActivity(idActivity).getOver())
                throw new CloseOrCancelEventException("CLOSED");
            else
                throw new CloseOrCancelEventException("CANCELLED");
        }
        if(!activityService.getSpecificActivity(idActivity).checkCreator(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new CreatorNotMatchingException(principal.getName());
        }
        activityService.addOrRemoveParticipants(activityService.getSpecificActivity(idActivity),
                sportsManService.findSpecificUser(idUser),false);
        return "redirect:/factory/ownactivity?id="+idActivity;
    }

    @RequestMapping(value = "/user/quit{id}", method = RequestMethod.GET)
    public String userLeave(@RequestParam(value = "id") Long idActivity, Principal principal) throws BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        if(!activityService.getSpecificActivity(idActivity).getParticipants().contains(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new EmptyRequestException(principal.getName());
        }
            activityService.participantDropout(activityService.getSpecificActivity(idActivity),
                sportsManService.findCurrentUser(principal.getName()));
        return "redirect:/user/getRegisteredEvents?id="+ sportsManService.findCurrentUser(principal.getName()).getId();
    }

    @RequestMapping(value = "/factory/invitecontactpage{id}", method = RequestMethod.GET)
    public String inviteContactPage(@RequestParam(value = "id") Long id, Model model, Principal principal) throws BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        if(!activityService.getSpecificActivity(id).checkCreator(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new CreatorNotMatchingException(principal.getName());
        }
        model.addAttribute("contacts", sportsManService.getAllContacts(principal.getName()));
        model.addAttribute("activity", activityService.getSpecificActivity(id));
        return "activity/inviteContactToActivity";
    }

    @RequestMapping(value = "/factory/inviteusertoactivity{idActivity,idUser}", method = RequestMethod.GET)
    public String inviteContactToEvent(@RequestParam(value = "idActivity") Long idActivity,
                                    @RequestParam(value = "idUser") Long idUser, Model model, Principal principal) throws CloseOrCancelEventException, BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        if(!activityService.getSpecificActivity(idActivity).getOpen() || activityService.getSpecificActivity(idActivity).getOver())
        {
            if(activityService.getSpecificActivity(idActivity).getOver())
                throw new CloseOrCancelEventException("CLOSED");
            else
                throw new CloseOrCancelEventException("CANCELLED");
        }
        if(!activityService.getSpecificActivity(idActivity).checkCreator(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new CreatorNotMatchingException(principal.getName());
        }
        activityService.inviteContact(activityService.getSpecificActivity(idActivity), sportsManService.findSpecificUser(idUser));
        return "redirect:/factory/ownactivity?id=" + idActivity;
    }

    /*@RequestMapping(value = "/factory/active{id}", method = RequestMethod.GET)
    public String active(@RequestParam(value = "id") Long id, Principal principal) {
        if(!activityService.getSpecificActivity(id).checkCreator(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new CreatorNotMatchingException(principal.getName());
        }
        activityService.cancelOrActivateActivity(activityService.getSpecificActivity(id), true);
        *//*newsService.returnCancelledApplictionNewOrCloseEventNew(activityService.getSpecificActivity(id),
                NewsType.RESTORE_EVENT);*//*
        return "redirect:/factory/activitiesbycreator";
    }*/

    @RequestMapping(value = "/factory/cancel{id}", method = RequestMethod.GET)
    public String cancel(@RequestParam(value = "id") Long id, Principal principal) throws CreatorNotMatchingException, CloseOrCancelEventException, BlockedUserException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        if(!activityService.getSpecificActivity(id).getOpen() || activityService.getSpecificActivity(id).getOver())
        {
            if(activityService.getSpecificActivity(id).getOver())
                throw new CloseOrCancelEventException("CLOSED");
            else
                throw new CloseOrCancelEventException("CANCELLED");
        }
        if(!activityService.getSpecificActivity(id).checkCreator(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new CreatorNotMatchingException(principal.getName());
        }
        activityService.cancelOrActivateActivity(activityService.getSpecificActivity(id), false);
        newsService.returnCancelledApplictionNewOrCloseEventNew(sportsManService.findCurrentUser(principal.getName()),
                activityService.getSpecificActivity(id),
                NewsType.CANCELLED_EVENT);
        return "redirect:/factory/activitiesbycreator";
    }

    @RequestMapping(value = "/factory/close{id}", method = RequestMethod.GET)
    public String closeEvent(@RequestParam(value = "id") Long id, Model model, Principal principal) throws CloseOrCancelEventException, BlockedUserException, InProgressActivityException {
        if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
            throw new BlockedUserException(principal.getName());
        }
        if (!activityService.getSpecificActivity(id).checkDate()){
            throw new InProgressActivityException(activityService.getSpecificActivity(id).getName());
        }
        if(!activityService.getSpecificActivity(id).getOpen() || activityService.getSpecificActivity(id).getOver())
        {
            if(activityService.getSpecificActivity(id).getOver())
                throw new CloseOrCancelEventException("CLOSED");
            else
                throw new CloseOrCancelEventException("CANCELLED");
        }
        if(!activityService.getSpecificActivity(id).checkCreator(sportsManService.findCurrentUser(principal.getName())))
        {
            throw new CreatorNotMatchingException(principal.getName());
        }
        if(activityService.checkAllCotationsForRegistered(activityService.getSpecificActivity(id))){
            activityService.closeActivity(activityService.getSpecificActivity(id));
            return "redirect:/factory/ownactivity?id=" + id;
        }
        //Sinon, redirection sur la page de check avec les personnes à noter
        else{
            model.addAttribute("peopleToMark", sportsManService.getAllNotMarked(activityService.getSpecificActivity(id)));
            model.addAttribute("id",id);
            model.addAttribute("notationForm", new NotationForm());
            return "activity/setCotationForEvent";
        }
    }
}
