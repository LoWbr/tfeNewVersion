package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.*;
import gradation.implementation.datatier.entities.SportsMan;
import gradation.implementation.presentationtier.exception.BlockedUserException;
import gradation.implementation.presentationtier.exception.CreatorNotMatchingException;
import gradation.implementation.presentationtier.exception.DoubleRequestException;
import gradation.implementation.presentationtier.exception.EmptyRequestException;
import gradation.implementation.presentationtier.form.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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

	@RequestMapping(value = "/sportsmans", method = RequestMethod.GET)
	public String getSportsMen(Model model, Principal principal) {
		model.addAttribute("all", true);
		if(principal != null) {
			model.addAttribute("current", sportsManService.findCurrentUser(principal.getName()));
		}
		model.addAttribute("allUsers", sportsManService.getAllUser());
		model.addAttribute("searchUserForm", new SearchUserForm());
		return "sportsman/users";
	}

	@RequestMapping(value = "/sportsmenbyfilter", method = RequestMethod.POST)
	public String getSportsMenByFilter(Model model, @ModelAttribute("searchUserForm") SearchUserForm searchUserForm,
									   Principal principal) {
		model.addAttribute("all", true);
		if(principal != null) {
			model.addAttribute("current", sportsManService.findCurrentUser(principal.getName()));
		}
		if (searchUserForm.getFirstName().equals("")) {
			searchUserForm.setFirstName(null);
		}
		if (searchUserForm.getLastName().equals("")) {
			searchUserForm.setLastName(null);
		}
		model.addAttribute("allUsers", sportsManService.getByFilter(searchUserForm));
		return "sportsman/users";
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String createSportsMan(Model model) {
		model.addAttribute("sportsManForm", new SportsManForm());
		return "global/signUp";
	}

	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public String saveSportsMan(@Valid SportsManForm sportsManForm, BindingResult bindingResult,
								HttpServletRequest request, HttpServletResponse response) {

		if (bindingResult.hasErrors()) {
			return "global/signUp";
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate current = LocalDate.now();
		LocalDate dateInput;
		try{
			dateInput = LocalDate.parse(sportsManForm.getDateofBirth(), formatter).plusDays(1);
		}catch (DateTimeParseException dt){
			bindingResult.rejectValue("dateofBirth", "", "Date not valid.");
			return "global/signUp";
		}


		if (this.sportsManService.findCurrentUser(sportsManForm.getMail()) != null) {
			bindingResult.rejectValue("mail", "", "This account already exists");
			return "global/signUp";
		} else if (Period.between(dateInput, current).getYears() < 18) {
			System.out.println(Period.between(dateInput, current).getYears());
			bindingResult.rejectValue("dateofBirth", "", "You must have 18 years old to register");
			return "global/signUp";
		}
		else if (Period.between(dateInput, current).getYears() > 90) {
			System.out.println(Period.between(dateInput, current).getYears());
			bindingResult.rejectValue("dateofBirth", "", "You seems to be over 90 years.. ");
			return "global/signUp";
		}
		else if (!(sportsManForm.getPassword()).equals(sportsManForm.getConfirmPassword())) {
			bindingResult.rejectValue("password", "", "The two passwords do not match.");
			return "global/signUp";
		} else {
			sportsManService.createUser(sportsManForm); //NB : if update de l'email, déconnexion à mettre en place  // ou refresh!!! (à creuser!!)
			authWithHttpServletRequest(request, sportsManForm.getMail(), sportsManForm.getPassword());
		}

		return "redirect:/user/details";
	}

	public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
		try {
			request.login(username, password);
		} catch (ServletException e) {

		}
	}

	public void refreshSession(String mail,String pwd) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
		Authentication newAuth = new UsernamePasswordAuthenticationToken(mail, pwd, updatedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}

	public void addAuthority(String mail,String pwd) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
		updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_CONFIRMED"));
		for (GrantedAuthority grantedAuthority: updatedAuthorities) {
			System.out.println(grantedAuthority.getAuthority());
		}
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
				updatedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}

	@RequestMapping(value = "/user/check", method = RequestMethod.GET)
	public String refreshStatus(){
		String mail = SecurityContextHolder.getContext().getAuthentication().getName();
		String pwd = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
		this.addAuthority(mail,pwd);
		return "redirect:/user/details";
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.GET)
	public String updateSportsManForm(Model model, Principal principal) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		model.addAttribute("sportsManForm",
				new SportsManForm(sportsManService.findCurrentUser(principal.getName())));
		model.addAttribute("blocked", sportsManService.findCurrentUser(principal.getName()).getBlocked());
		return "sportsman/updateUser";
	}

	@RequestMapping(value = "/user/updateuser", method = RequestMethod.POST)
	public String updateSportsMan(@Valid SportsManForm sportsManForm,
                                  BindingResult bindingResult, Principal principal, HttpServletRequest logout,
								  HttpServletRequest login) throws ServletException {
		if(bindingResult.hasErrors()){
			return "sportsman/updateUser";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate current = LocalDate.now();
		LocalDate dateInput;
		try{
			dateInput = LocalDate.parse(sportsManForm.getDateofBirth(), formatter).plusDays(1);
		}catch (DateTimeParseException dt){
			bindingResult.rejectValue("dateofBirth", "", "Date not valid.");
			return "sportsman/updateUser";
		}

		if(Period.between(dateInput,current).getYears() < 18){
			bindingResult.rejectValue("dateofBirth","","You must have 18 years old to register");
			return "sportsman/updateUser";
		}
		else if (Period.between(dateInput, current).getYears() > 90) {
			bindingResult.rejectValue("dateofBirth", "", "You seems to be over 90 years.. ");
			return "sportsman/updateUser";
		}
		else if(!(sportsManForm.getPassword()).equals(sportsManForm.getConfirmPassword())){
			bindingResult.rejectValue("password","","The two passwords do not match.");
			return "sportsman/updateUser";
		}
		long idcurrent = this.sportsManService.findCurrentUser(principal.getName()).getId();
		long idform = sportsManForm.getId();
		if(this.sportsManService.findCurrentUser(sportsManForm.getMail()) != null &&
				idcurrent != idform) {
			bindingResult.rejectValue("mail", "", "This account already exists");
			return "sportsman/updateUser";
		}
		else {
			SportsMan transition = sportsManService.findCurrentUser(principal.getName());
			sportsManService.updateUser(transition, sportsManForm);
			this.refreshSession(sportsManForm.getMail(), sportsManForm.getPassword());
		}
		return "redirect:/user/details";
	}

	/*public void logout(HttpServletRequest request) throws ServletException {
		SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
		securityContextLogoutHandler.logout(request, null, null);
	}*/

	@RequestMapping(value = "/user/details", method = RequestMethod.GET)
	public String sportsManOwnDetails(Principal principal, Model model) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		boolean empty = true;
		List<SportsMan> applications = managementService.getPromotionCandidates();
		for (SportsMan demander: applications) {
			long idcurrent = this.sportsManService.findCurrentUser(principal.getName()).getId();
			long idform = demander.getId();
			if(idcurrent == idform){
				empty = false;
			}
		}
		model.addAttribute("empty", empty);
		model.addAttribute("sportsMan", sportsManService.findCurrentUser(principal.getName()));
		model.addAttribute("allUsers",
				sportsManService.getPotentialContacts(sportsManService.findCurrentUser(principal.getName())));
		model.addAttribute("statistics",
				sportsManService.findBySportsMan(sportsManService.findCurrentUser(principal.getName())));
		return "sportsman/userDetails";
	}


	@RequestMapping(value = "/sportsman{id}", method = RequestMethod.GET)
	public String getSportsManDetail(@RequestParam Long id, Model model,Principal principal){
		if(principal != null) {
			model.addAttribute("current", sportsManService.findCurrentUser(principal.getName()));
		}
		model.addAttribute("sportsMan",sportsManService.findSpecificUser(id));
		return "sportsman/externDetails";
	}


	@RequestMapping(value = "/user/create/targetedmessage{id, error}", method = RequestMethod.GET)
	public String getTargetedMessageForm(Long id, @RequestParam(required = false) boolean error, Model model, Principal principal) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		if(error){
			model.addAttribute("errorMsg", error);
		}
		MessageForm messageForm = new MessageForm();
		if(!sportsManService.findCurrentUser(principal.getName()).hasContact(sportsManService.findSpecificUser(id)))
		{
			throw new EmptyRequestException(principal.getName());
		}
		messageForm.setOriginator(sportsManService.findCurrentUser(principal.getName()));
		messageForm.getAddressee().add(sportsManService.findSpecificUser(id));
		model.addAttribute("messageForm", messageForm);
		return "sportsman/createTargetedMessage";
	}

	@RequestMapping(value = "/user/sendtargetedmessage", method = RequestMethod.POST)
	public String sendTargetedMessage(@Valid MessageForm messageForm, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "redirect:/user/create/targetedmessage?id=" + messageForm.getAddressee().get(0).getId() + "&error=" + true;
		}
		sportsManService.sendMessage(messageForm);
		return "redirect:/user/details";
	}

	@RequestMapping(value = "/user/create/emptymessage{error}", method = RequestMethod.GET)
	public String getEmptyMessageForm(@RequestParam(required = false) boolean error, Model model, Principal principal) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		MessageForm messageForm = new MessageForm();
		if(error){
			model.addAttribute("errorMsg", error);
		}
		messageForm.setOriginator(sportsManService.findCurrentUser(principal.getName()));
		model.addAttribute("contacts", sportsManService.getAllContacts(principal.getName()));
		model.addAttribute("messageForm", messageForm);
		return "sportsman/createEmptyMessage";
	}

	@RequestMapping(value = "/user/sendmessage", method = RequestMethod.POST)
	public String sendMessage(@Valid MessageForm messageForm, BindingResult bindingResult){
		if(bindingResult.hasErrors() || messageForm.getAddressee().size() < 1){
			return "redirect:/user/create/emptymessage?error=" + true;
		}
		sportsManService.sendMessage(messageForm);
		return "redirect:/user/details";
	}

	@RequestMapping(value = "/user/getMessagesSent", method = RequestMethod.GET)
	public String getAllMessageSent(Model model, Principal principal) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		model.addAttribute("messages",sportsManService.findMessages(
				sportsManService.findCurrentUser(principal.getName()), true));
		String status = "sent";
		model.addAttribute("status", status);
		model.addAttribute("blocked", sportsManService.findCurrentUser(principal.getName()).getBlocked());
		return "sportsman/getMessages";
	}
	@RequestMapping(value = "/user/getReceivedMessages", method = RequestMethod.GET)
	public String getAllReceivedMessages(Model model, Principal principal) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		model.addAttribute("messages",sportsManService.findMessages(
				sportsManService.findCurrentUser(principal.getName()), false));
		String status = "received";
		model.addAttribute("status", status);
		model.addAttribute("blocked", sportsManService.findCurrentUser(principal.getName()).getBlocked());
		return "sportsman/getMessages";
	}

	@RequestMapping(value = "/user/message{id,send}", method = RequestMethod.GET)
	public String getSpecificMessage(@RequestParam Long id, @RequestParam Boolean send ,Model model,
									 Principal principal){
		model.addAttribute("send", send);
		model.addAttribute("message",
				sportsManService.findSpecificMessage(id));
		return "sportsman/message";
	}

	@RequestMapping(value = "/user/notifications", method = RequestMethod.GET)
	public String notifications(Model model, Principal principal) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		model.addAttribute("notifications",
				sportsManService.getNotifications(sportsManService.findCurrentUser(principal.getName())));
		model.addAttribute("blocked", sportsManService.findCurrentUser(principal.getName()).getBlocked());
		return "sportsman/notifications";
	}
	@RequestMapping(value = "/user/clearnotification{id}", method = RequestMethod.GET)
	public String checkNotification(@RequestParam Long id){
		newsService.checkNews(id);
		return "redirect:/user/notifications";
	}

	//FindContacts
	@RequestMapping(value = "/user/contacts", method = RequestMethod.GET)
	public String getContacts(Model model, Principal principal) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		model.addAttribute("allUsers",
				sportsManService.getAllContacts(principal.getName()));
		model.addAttribute("blocked", sportsManService.findCurrentUser(principal.getName()).getBlocked());
		return "sportsman/contacts";
	}
	//FindNotContacts
	@RequestMapping(value = "/user/findNewUsers", method = RequestMethod.GET)
	public String getUnknowUsers(Model model, Principal principal){
		model.addAttribute("all",false);
		model.addAttribute("searchUserForm", new SearchUserForm());
		model.addAttribute("current", sportsManService.findCurrentUser(principal.getName()));
		model.addAttribute("allUsers",
				sportsManService.getPotentialContacts(sportsManService.findCurrentUser(principal.getName())));
		return "sportsman/users";
	}

	@RequestMapping(value = "/user/getRegisteredEvents{id}", method = RequestMethod.GET)
	public String getRegisteredEvents(@RequestParam Long id, Model model, Principal principal) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		model.addAttribute("sportsMan", sportsManService.findSpecificUser(id));
		return "sportsman/userParticipatedEvents";
	}

	@RequestMapping(value = "/user/addContact{id}", method = RequestMethod.GET)
	public String addContact(@RequestParam Long id,
			Principal principal) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		if(sportsManService.findCurrentUser(principal.getName()).hasContact(sportsManService.findSpecificUser(id)))
		{
			throw new DoubleRequestException(principal.getName());
		}
		sportsManService.addOrRemoveContacts(sportsManService.findCurrentUser(principal.getName()),
				sportsManService.findSpecificUser(id), true);
		return "redirect:/user/contacts";
	}

	@RequestMapping(value = "/user/removeContact{id}", method = RequestMethod.GET)
	public String removeContact(@RequestParam Long id,
			Principal principal) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		if(!sportsManService.findCurrentUser(principal.getName()).hasContact(sportsManService.findSpecificUser(id)))
		{
			throw new EmptyRequestException(principal.getName());
		}
		sportsManService.addOrRemoveContacts(sportsManService.findCurrentUser(principal.getName()),
				sportsManService.findSpecificUser(id), false);
		return "redirect:/user/contacts";
	}

	@RequestMapping(value = "/user/applyAsConfirmedUser", method = RequestMethod.GET)
	public String applyAsConfirmedUser(Principal principal) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		managementService.applyForConfirmedRole(sportsManService.findCurrentUser(principal.getName()));
		return "redirect:/user/details";
	}

	@RequestMapping(value = "/factory/noteuser{idActivity,idUser}", method = RequestMethod.POST)
	public String noteUser(@RequestParam(value = "idActivity") Long idActivity,
                           @RequestParam(value = "idUser") Long idUser, NotationForm notationForm, Principal principal,
						   Model model) throws BlockedUserException {
		if(sportsManService.findCurrentUser(principal.getName()).getBlocked()){
			throw new BlockedUserException(principal.getName());
		}
		if(!activityService.getSpecificActivity(idActivity).checkCreator(sportsManService.findCurrentUser(principal.getName())))
		{
			throw new CreatorNotMatchingException(principal.getName());
		}
		this.sportsManService.setResultForEventToParticipant(activityService.getSpecificActivity(idActivity),
				sportsManService.findSpecificUser(idUser), notationForm.getNotation());

		if(activityService.checkAllCotationsForRegistered(activityService.getSpecificActivity(idActivity))){
			activityService.closeActivity(activityService.getSpecificActivity(idActivity));
			return "redirect:/factory/ownactivity?id=" + idActivity;
		}
		else{
			model.addAttribute("peopleToMark", sportsManService.getAllNotMarked(activityService.getSpecificActivity(idActivity)));
			model.addAttribute("id",idActivity);
			model.addAttribute("notationForm", new NotationForm());
			return "activity/setCotationForEvent";
		}
	}

}
