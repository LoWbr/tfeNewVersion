package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.*;
import gradation.implementation.datatier.entities.Activity;
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

@Controller
public class ActivityController {

    private ActivityService activityService;
    private SportsManService sportsManService;
    private ActivitySettingService activitySettingService;

    @Autowired
    public ActivityController(ActivityService activityService, SportsManService sportsManService,
                              ActivitySettingService activitySettingService) {
        this.activityService = activityService;
        this.sportsManService = sportsManService;
        this.activitySettingService = activitySettingService;
    }

    @RequestMapping(value ="activities", method = RequestMethod.GET)
    public String getAllEvents(Model model) {
        model.addAttribute("allActivities", activityService.getAllActivities());
        return "activity/events";
    }

    @ModelAttribute
    public void initiate(Model model) {
        model.addAttribute("activityForm", new ActivityForm());
        model.addAttribute("allKinds", activitySettingService.getAllActivityTypes());
        model.addAttribute("allLevels", activitySettingService.getAllLevels());
    }

    @RequestMapping(value ="/factory/create", method = RequestMethod.GET)
    public String createEvent(Model model) {
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
        LocalDate dateInput = LocalDate.parse(activityForm.getPlannedTo(),formatter);
        LocalDate current = LocalDate.now();

        LocalTime start = LocalTime.now();
        LocalTime end = LocalTime.parse(activityForm.getHour().concat(":00"));
        Duration duration = Duration.between(start, end);

        if(Period.between(dateInput,current).getDays() > 0){
            bindingResult.rejectValue("plannedTo","","You have to set a valid date");
            return "activity/createEvent";
        }
        else if(Period.between(dateInput,current).getDays() >= 0 && duration.getSeconds() < 3600){
            bindingResult.rejectValue("hour","","You have to set a valid hour");
            return "createEvent";
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
            activityService.createActivity(activityForm, sportsManService.findCurrentUser(principal.getName()),
                    activitySettingService.createAddress(activityForm));
        }
        return "redirect:/events";
    }

    @RequestMapping(value = "activity{id}", method = RequestMethod.GET)
    public String eventDetails(@RequestParam Long id, Model model, Principal principal) {
        if(principal != null){
            model.addAttribute("sportsMan", sportsManService.findCurrentUser(principal.getName()));
        }
        Activity activity = activityService.getSpecificActivity(id);
        model.addAttribute("activity", activity);//Raccourci encore faisable
        model.addAttribute("participants", activity.getRegistered());
        model.addAttribute("comments", activitySettingService.findCommentsForActivity(activity));
        return "activity/eventDetails";
    }

    @RequestMapping(value = "/factory/activitiesbycreator", method = RequestMethod.GET)
    public String getActivitiesByCreator(Model model, Principal principal) {
        model.addAttribute("ownCreations",
                activityService.getAllOfTheSameCreator(sportsManService.findCurrentUser(principal.getName())));
        return "activity/ownEvents";
    }

    @RequestMapping(value = "/factory/ownactivity{id}", method = RequestMethod.GET)
    public String ownEventDetails(@RequestParam Long id, Model model) {
        Activity activity = activityService.getSpecificActivity(id);
        model.addAttribute("activity", activity);//Raccourci encore faisable
        model.addAttribute("candidates", activity.getCandidate());
        model.addAttribute("participants", activity.getRegistered());
        model.addAttribute("comments", activitySettingService.findCommentsForActivity(activity));
        return "activity/ownEventDetails";
    }

    @RequestMapping(value = "/factory/activity/update{id}", method = RequestMethod.GET)
    public String updateEventForm(@RequestParam Long id, Model model) {
        ActivityForm activityForm = new ActivityForm(activityService.getSpecificActivity(id),
                activityService.getSpecificActivity(id).getAddress());
        model.addAttribute("allKinds", activitySettingService.getAllActivityTypes());
        model.addAttribute("activityForm", activityForm);
        model.addAttribute("allLevels", activitySettingService.getAllLevels());
        return "activity/updateEvent";
    }

    @RequestMapping(value = "/factory/updateactiviy", method = RequestMethod.POST)
    public String updateEvent(@Valid @ModelAttribute("activityForm") ActivityForm activityForm, BindingResult
                              bindingResult) {
        if(bindingResult.hasErrors()){
            System.out.println("Errors: " + bindingResult.getErrorCount());
            return "activity/createEvent";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateInput = LocalDate.parse(activityForm.getPlannedTo(),formatter);
        LocalDate current = LocalDate.now();

        LocalTime start = LocalTime.now();
        LocalTime end = LocalTime.parse(activityForm.getHour().concat(":00"));
        Duration duration = Duration.between(start, end);

        if(Period.between(dateInput,current).getDays() > 0){
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

        return "activity/events";
    }

    @RequestMapping(value = "/user/postulate{id}", method = RequestMethod.GET)
    public String applyAsCandidate(@RequestParam(value = "id") Long id, Principal principal) {
        activityService.applyAsCandidate(activityService.getSpecificActivity(id),
                sportsManService.findCurrentUser(principal.getName()));
        return "redirect:/events";
    }

    @RequestMapping(value = "/factory/refuseuser{id}", method = RequestMethod.POST)
    public String refuseUser(@RequestParam(value = "candidate") Long idParticipant,
                          @RequestParam(value = "id") Long idActivity) {
        activityService.refuseBuyer(activityService.getSpecificActivity(idActivity),
                sportsManService.findSpecificUser(idParticipant));
        return "redirect:/events";
    }

    @RequestMapping(value = "/factory/adduser{id}", method = RequestMethod.POST)
    public String addUser(@RequestParam(value = "candidate") Long idParticipant,
                          @RequestParam(value = "id") Long idActivity) {
        activityService.addOrRemoveParticipants(activityService.getSpecificActivity(idActivity),
                sportsManService.findSpecificUser(idParticipant),true);
        return "redirect:/events";
    }

    @RequestMapping(value = "/factory/removeuser{id}", method = RequestMethod.POST)
    public String removeUser(@RequestParam(value = "participant") Long idParticipant,
                             @RequestParam(value = "id") Long idActivity) {
        activityService.addOrRemoveParticipants(activityService.getSpecificActivity(idActivity),
                sportsManService.findSpecificUser(idParticipant),false);
        return "redirect:/events";
    }

    @RequestMapping(value = "/user/quit{id}", method = RequestMethod.GET)
    public String removeUser(@RequestParam(value = "id") Long idActivity, Principal principal) {
        activityService.participantDropout(activityService.getSpecificActivity(idActivity),
                sportsManService.findCurrentUser(principal.getName()));
        return "redirect:/events";
    }

    @RequestMapping(value = "/factory/invitecontactpage{id}", method = RequestMethod.GET)
    public String inviteContactPage(@RequestParam(value = "id") Long id, Model model, Principal principal) {
        model.addAttribute("contacts", sportsManService.getAllContacts(principal.getName()));
        model.addAttribute("activity", activityService.getSpecificActivity(id));
        return "activity/inviteContactToActivity";
    }

    @RequestMapping(value = "/factory/inviteusertoactivity{idActivity,idUser}", method = RequestMethod.GET)
    public String inviteContactPage(@RequestParam(value = "idActivity") Long idActivity,
                                    @RequestParam(value = "idUser") Long idUser, Model model) {
        System.out.println(idActivity+ " " + idUser);
        activityService.inviteContact(activityService.getSpecificActivity(idActivity), sportsManService.findSpecificUser(idUser));
        return "redirect:/inviteContactPage?id=" + idActivity;
    }

    @RequestMapping(value = "/factory/close{id}", method = RequestMethod.GET)
    public String closeEvent(@RequestParam(value = "id") Long id, Model model) {
        //check cotations => if stat avec id event pour chaque participant, close event.
        if(activityService.checkAllCotationsForRegistered(activityService.getSpecificActivity(id))){
            activityService.closeActivity(activityService.getSpecificActivity(id));
            return "redirect:/events";
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
