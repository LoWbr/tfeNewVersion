package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.*;
import gradation.implementation.datatier.entities.SportsMan;
import gradation.implementation.presentationtier.form.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Controller
public class SportsManController {

    private SportsManService sportsManService;

    private ActivityService activityService;

    private ManagementService managementService;

    private NewsService newsService;

	@Autowired
	public SportsManController(SportsManService sportsManService, ActivityService activityService,
							   ManagementService managementService, NewsService newsService) {
		this.sportsManService = sportsManService;
		this.activityService = activityService;
		this.managementService = managementService;
		this.newsService = newsService;
	}

	@RequestMapping(value= "/sportsmans", method = RequestMethod.GET)
	public String getSportsMen(Model model) {
		model.addAttribute("allUsers", sportsManService.getAllUser());
		return "sportsman/users";
	}

	@RequestMapping(value="/signUp", method = RequestMethod.GET)
	public String createSportsMan(Model model) {
		model.addAttribute("sportsManForm", new SportsManForm());
		return "global/signUp";
	}

	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public String saveSportsMan(@Valid SportsManForm sportsManForm, BindingResult bindingResult,
                                HttpServletRequest request, HttpServletResponse response){

		if(bindingResult.hasErrors()){
			System.out.println("Errors: " + bindingResult.getErrorCount());
			return "global/signUp";
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateInput = LocalDate.parse(sportsManForm.getDateofBirth(),formatter).plusDays(1);
		LocalDate current = LocalDate.now();

		if(this.sportsManService.findCurrentUser(sportsManForm.getMail()) != null) {
			bindingResult.rejectValue("mail", "", "This account already exists");
			return "global/signUp";
		}
		else if(Period.between(dateInput,current).getYears() < 18){
			System.out.println(Period.between(dateInput,current).getYears());
			bindingResult.rejectValue("dateofBirth","","You must have 18 years old to register");
			return "global/signUp";
		}
		else if(!(sportsManForm.getPassword()).equals(sportsManForm.getConfirmPassword())){
			bindingResult.rejectValue("password","","The two passwords do not match.");
			return "global/signUp";
		}
		else{
			sportsManService.createUser(sportsManForm); //NB : if update de l'email, déconnexion à mettre en place  // ou refresh!!! (à creuser!!)
			authWithHttpServletRequest(request,sportsManForm.getMail(), sportsManForm.getPassword());
		}

		return "redirect:/user/details";
	}
	public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
		try {
			request.login(username, password);
		} catch (ServletException e) {

		}
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.GET)
	public String updateSportsManForm(Model model, Principal principal) {
		model.addAttribute("sportsManForm",
				new SportsManForm(sportsManService.findCurrentUser(principal.getName())));
		return "sportsman/updateUser";
	}

	@RequestMapping(value = "/user/updateuser", method = RequestMethod.POST)
	public String updateSportsMan(@Valid SportsManForm sportsManForm,
                                  BindingResult bindingResult, Principal principal, HttpServletRequest request) throws ServletException {
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getFieldError());
			return "sportsman/updateUser";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateInput = LocalDate.parse(sportsManForm.getDateofBirth(),formatter).plusDays(1);
		LocalDate current = LocalDate.now();

		if(this.sportsManService.findCurrentUser(sportsManForm.getMail()) != null &&
				this.sportsManService.findCurrentUser(sportsManForm.getMail()).getId() != sportsManForm.getId()) {
			bindingResult.rejectValue("mail", "", "This account already exists");
			return "sportsman/updateUser";
		}
		else if(Period.between(dateInput,current).getYears() < 18){
			System.out.println(Period.between(dateInput,current).getYears());
			bindingResult.rejectValue("dateofBirth","","You must have 18 years old to register");
			return "sportsman/updateUser";
		}
		else if(!(sportsManForm.getPassword()).equals(sportsManForm.getConfirmPassword())){
			bindingResult.rejectValue("password","","The two passwords do not match.");
			return "sportsman/updateUser";
		}
		else {
			SportsMan transition = sportsManService.findCurrentUser(principal.getName());
			this.logout(request);// Check logout redirection // Problème
			sportsManService.updateUser(transition, sportsManForm);
			this.authWithHttpServletRequest(request,sportsManForm.getMail(), sportsManForm.getPassword());
			/* To check
			https://stackoverflow.com/questions/7889660/how-to-reload-spring-security-principal-after-updating-in-hibernate
			https://stackoverflow.com/questions/23072235/reload-userdetails-object-from-database-every-request-in-spring-security
			https://stackoverflow.com/questions/9910252/how-to-reload-authorities-on-user-update-with-spring-security
			 */
		}
		return "redirect:/user/details";
	}

	public void logout(HttpServletRequest request) throws ServletException {
		SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
		securityContextLogoutHandler.logout(request, null, null);
	}

	@RequestMapping(value = "/user/details", method = RequestMethod.GET)
	public String sportsManOwnDetails(Principal principal, Model model) {
		model.addAttribute("sportsMan", sportsManService.findCurrentUser(principal.getName()));
		model.addAttribute("allUsers",
				sportsManService.getPotentialContacts(sportsManService.findCurrentUser(principal.getName())));
		model.addAttribute("statistics",
				sportsManService.findBySportsMan(sportsManService.findCurrentUser(principal.getName())));
		return "sportsman/userDetails";
	}

	//Show User Details
	@RequestMapping(value = "/sportsman{id}", method = RequestMethod.GET)
	public String getSportsManDetail(@RequestParam Long id, Model model){
		model.addAttribute("sportsMan",sportsManService.findSpecificUser(id));
		return "sportsman/externDetails";
	}
	//Send Message to user (à mettre sur la page du contact)

	@RequestMapping(value = "/user/createMessage{id}", method = RequestMethod.GET)
	public String getMessageForm(@RequestParam(required = false) Long id, Model model, Principal principal){
		MessageForm messageForm = new MessageForm();
		boolean showInput;
		if (id != null) {
			showInput = false;
			model.addAttribute("showInput",showInput);
			messageForm.setOriginator(sportsManService.findCurrentUser(principal.getName()));
			messageForm.getAddressee().add(sportsManService.findSpecificUser(id));
		}
		else{
			showInput = true;
			model.addAttribute("showInput",showInput);
			model.addAttribute("contacts", sportsManService.getAllContacts(principal.getName()));
			messageForm.setOriginator(sportsManService.findCurrentUser(principal.getName()));
		}
		model.addAttribute("messageForm", messageForm);
		return "sportsman/createMessage";
	}

	@RequestMapping(value = "/user/sendMessage", method = RequestMethod.POST)
	public String sendMessage(@Valid MessageForm messageForm, BindingResult bindingResult){
		sportsManService.sendMessage(messageForm);
		return "redirect:/user/details";
	}

	@RequestMapping(value = "/user/getMessagesSent", method = RequestMethod.GET)
	public String getAllMessageSent(Model model, Principal principal){
		model.addAttribute("messages",sportsManService.findMessages(
				sportsManService.findCurrentUser(principal.getName()), true));
		String status = "sent";
		model.addAttribute("status", status);
		return "sportsman/getMessages";
	}
	@RequestMapping(value = "/user/getReceivedMessages", method = RequestMethod.GET)
	public String getAllReceivedMessages(Model model, Principal principal){
		model.addAttribute("messages",sportsManService.findMessages(
				sportsManService.findCurrentUser(principal.getName()), false));
		String status = "received";
		model.addAttribute("status", status);
		return "sportsman/getMessages";
	}
	//Get Notification Page
	@RequestMapping(value = "/user/notifications", method = RequestMethod.GET)
	public String notifications(Model model, Principal principal){
		model.addAttribute("notifications",
				sportsManService.getNotifications(sportsManService.findCurrentUser(principal.getName())));
		return "sportsman/notifications";
	}
	@RequestMapping(value = "/user/clearnotification{id}", method = RequestMethod.GET)
	public String checkNotification(@RequestParam Long id){
		newsService.checkNews(id);
		return "redirect:/user/notifications";
	}

	//FindContacts
	@RequestMapping(value = "/user/contacts", method = RequestMethod.GET)
	public String getContacts(Model model, Principal principal){
		model.addAttribute("allUsers",
				sportsManService.getAllContacts(principal.getName()));
		return "sportsman/contacts";
	}
	//FindNotContacts
	@RequestMapping(value = "/user/findNewUsers", method = RequestMethod.GET)
	public String getUnknowUsers(Model model, Principal principal){
		model.addAttribute("allUsers",
				sportsManService.getPotentialContacts(sportsManService.findCurrentUser(principal.getName())));
		return "sportsman/users";
	}

	@RequestMapping(value = "/user/getRegisteredEvents{id}", method = RequestMethod.GET)
	public String getRegisteredEvents(@RequestParam Long id, Model model) {
		model.addAttribute("sportsMan", sportsManService.findSpecificUser(id));
		return "sportsman/userParticipatedEvents";
	}

	@RequestMapping(value = "/user/addContact{id}", method = RequestMethod.POST)
	public String addContact(@RequestParam(value = "contact") Long idContact,
			Principal principal) {
		sportsManService.addOrRemoveContacts(sportsManService.findCurrentUser(principal.getName()),
				sportsManService.findSpecificUser(idContact), true);
		return "redirect:/user";
	}

	@RequestMapping(value = "/user/removeContact{id}", method = RequestMethod.POST)
	public String removeContact(@RequestParam(value = "contact") Long idContact,
			Principal principal) {
		sportsManService.addOrRemoveContacts(sportsManService.findCurrentUser(principal.getName()),
				sportsManService.findSpecificUser(idContact), false);
		return "redirect:/user";
	}

	@RequestMapping(value = "/user/applyAsConfirmedUser", method = RequestMethod.GET)
	public String applyAsConfirmedUser(Principal principal) {
		managementService.applyForConfirmedRole(sportsManService.findCurrentUser(principal.getName()));
		return "redirect:/user/details";
	}

	@RequestMapping(value = "/factory/noteuser{idActivity,idUser}", method = RequestMethod.POST)
	public String noteUser(@RequestParam(value = "idActivity") Long idActivity,
                           @RequestParam(value = "idUser") Long idUser, NotationForm notationForm){
		this.sportsManService.setResultForEventToParticipant(activityService.getSpecificActivity(idActivity),
				sportsManService.findSpecificUser(idUser), notationForm.getNotation());
		return "redirect:/factory/ownactivity?id=" + idActivity;
	}

}
