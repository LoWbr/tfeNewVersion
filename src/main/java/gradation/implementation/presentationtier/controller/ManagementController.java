package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.*;
import gradation.implementation.businesstier.databasebackup.MediaTypeSetting;
import gradation.implementation.datatier.entities.*;

import gradation.implementation.presentationtier.form.ActivityTypeForm;
import gradation.implementation.presentationtier.form.LevelForm;
import gradation.implementation.presentationtier.form.SearchNewForm;
import gradation.implementation.presentationtier.form.TopicForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;

@Controller
public class ManagementController {

	private ActivityService activityService;
	private SportsManService sportsManService;
	private ManagementService managementService;
	private NewsService newsService;
	private ActivitySettingService activitySettingService;
	private ServletContext servletContext;

	@Autowired
	public ManagementController(ActivityService activityService, SportsManService sportsManService,
			ManagementService managementService, NewsService newsService, ActivitySettingService activitySettingService,
								ServletContext servletContext) {
		this.activityService = activityService;
		this.sportsManService = sportsManService;
		this.managementService = managementService;
		this.newsService = newsService;
		this.activitySettingService = activitySettingService;
		this.servletContext = servletContext;
	}

	@RequestMapping(value="/manage/users", method = RequestMethod.GET)
	public String manageUserSetting(Model model) {
		TopicForm topicForm = new TopicForm();
		model.addAttribute("allUsers", sportsManService.getAllUser());
		model.addAttribute("allCandidates", managementService.getPromotionCandidates());
		model.addAttribute("topicForm", topicForm);
		return "management/manageUsers";
	}

	@RequestMapping(value="/manage/activities", method = RequestMethod.GET)
	public String manageEventSetting(Model model) {
		model.addAttribute("allActivities", activityService.getAllActivities());
		return "management/manageEvents";
	}

	@RequestMapping(value = "/manage/activities/cancel{id}", method = RequestMethod.GET)
	public String cancel(@RequestParam(value = "id") Long id) {
		activityService.cancelOrActivateActivity(activityService.getSpecificActivity(id), false);
		newsService.returnCancelledApplictionNewOrCloseEventNew(activityService.getSpecificActivity(id),
				NewsType.CANCELLED_EVENT);
		return "redirect:/manage/activities";
	}

	@RequestMapping(value = "/manage/activities/open{id}", method = RequestMethod.GET)
	public String open(@RequestParam(value = "id") Long id) {
		activityService.cancelOrActivateActivity(activityService.getSpecificActivity(id), true);
		return "redirect:/manage/activities";
	}

	@RequestMapping(value = "/manage/users/block{id}", method = RequestMethod.GET)
	public String block(@RequestParam(value = "id") Long id) {
		sportsManService.blockOrUnblock(sportsManService.findSpecificUser(id),true);
		for (Activity activity : sportsManService.findSpecificUser(id).getCreatedActivities()) {
			activityService.cancelOrActivateActivity(activity,false);
		}
		return "redirect:/manage/users";
	}

	@RequestMapping(value = "/manage/users/unblock{id}", method = RequestMethod.GET)
	public String unblock(@RequestParam(value = "id") Long id) {
		sportsManService.blockOrUnblock(sportsManService.findSpecificUser(id),false);
		for (Activity activity : sportsManService.findSpecificUser(id).getCreatedActivities()) {
			activityService.cancelOrActivateActivity(activity,true);
		}
		return "redirect:/manage/users";
	}

	@RequestMapping(value = "/manage/users/deniepromote{id}", method = RequestMethod.GET)
	public String refusePromotionUser(@RequestParam(value = "id") Long id) {
		SportsMan sportsMan = sportsManService.findSpecificUser(id);
		managementService.removeRequest(managementService.findSpecific(sportsMan));
		newsService.returnApplicationResultNewOrLevelUpNew(sportsMan, NewsType.NEGATIVE_REQUEST);
		return "redirect:/manage/users";
	}

	@RequestMapping(value = "/manage/users/promote{id}", method = RequestMethod.GET)
	public String promoteUser(@RequestParam(value = "id") Long id) {
		sportsManService.promoteUser(sportsManService.findSpecificUser(id));
		managementService.removeRequest(managementService.findSpecific
				(sportsManService.findSpecificUser(id)));
		newsService.returnApplicationResultNewOrLevelUpNew(sportsManService.findSpecificUser(id),
				NewsType.VALIDATED_REQUEST);
		//Add notification!!
		return "redirect:/manage/users";
	}

	@RequestMapping(value = "/manage/addtopic", method = RequestMethod.POST)
	public String createTopic(@ModelAttribute("topicForm") TopicForm topicForm,
			Principal principal) {
		this.managementService.addTopic(
				sportsManService.findCurrentUser(principal.getName()),
				topicForm);
		return "redirect:/";
	}

	@RequestMapping(value = "/manage/types", method = RequestMethod.GET)
	public String manageSportsSetting(Model model) {
		model.addAttribute("activityTypeForm",new ActivityTypeForm());
		model.addAttribute("activityTypes",activitySettingService.getAllActivityTypes());
		return "management/setSportsSetting";
	}

	@RequestMapping(value = "/manage/types/update{id}", method = RequestMethod.POST)
	public String updateType(@RequestParam(value = "id") Long id, @ModelAttribute("activityTypeForm") ActivityTypeForm activityTypeForm) {
		activitySettingService.updateType(activitySettingService.findSpecificActivityType(id),activityTypeForm);
		return "redirect:/manage/types";
	}

	@RequestMapping(value = "/manage/types/add", method = RequestMethod.POST)
	public String addType(@ModelAttribute("activityTypeForm") ActivityTypeForm activityTypeForm) {
		activitySettingService.createType(activityTypeForm);
		return "redirect:/manage/types";
	}

	@RequestMapping(value = "/manage/levels", method = RequestMethod.GET)
	public String manageLevelsSetting(Model model) {
		model.addAttribute("levelForm",new LevelForm());
		model.addAttribute("activityLevels",activitySettingService.getAllLevels());
		return "management/setLevelsSetting";
	}

	@RequestMapping(value = "/manage/levels/update{id}", method = RequestMethod.POST)
	public String updateLevel(@RequestParam(value = "id") Long id, @ModelAttribute("levelForm") LevelForm levelForm) {
		activitySettingService.updateLevel(levelForm,activitySettingService.findSpecificLevel(id));
		return "redirect:/manage/levels";
	}

	@RequestMapping(value = "/manage/history", method = RequestMethod.GET)
	public String getHistory(@ModelAttribute("searchNewForm") SearchNewForm searchNewForm,
							 Model model, @RequestParam(required = false) Boolean there) {
		model.addAttribute("allTypes", newsService.getAllNewsType());
		model.addAttribute("allUsers", sportsManService.getAllUser());

		model.addAttribute("allActs",newsService.findAll());
		model.addAttribute("searchActivityForm",searchNewForm);
		return "management/searchNew";

	}

	@RequestMapping(value = "/manage/history/filter", method = RequestMethod.POST)
	public String getHistoryByFilter(@ModelAttribute("searchNewForm") SearchNewForm searchNewForm,
									 Model model, @RequestParam(required = false) Boolean there) {
		if(searchNewForm.getNameSportsman().equals("")){
			searchNewForm.setNameSportsman(null);
		}
		model.addAttribute("allTypes", newsService.getAllNewsType());
		model.addAttribute("allUsers", sportsManService.getAllUser());
		model.addAttribute("allActs",newsService.findForSearch(searchNewForm));
		model.addAttribute("searchActivityForm",searchNewForm);
		return "management/searchNew";
	}

	@RequestMapping(value = "/manage/backupdb/cyclic", method = RequestMethod.GET)
	public String launchBackUpProcess(){
		return "managementHome";
	}

	@RequestMapping(value = "/manage/backupdb/download", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> makeDBBackUp() throws IOException, InterruptedException, NullPointerException {

		/*this.managementService.returnDB();*/

		String dbName = "tfe";
		String fileName = "Gradation_DB_BackUp";
		String folderPath = "/home/laurent/ultimateProjects/phase3/tfe_implementation/backupForDownload";
		File f1 = new File(folderPath);
		f1.mkdir();

		String saveFileName = fileName + ".sql";
		String savePath = f1.getAbsolutePath() + File.separator + saveFileName;

		String executeCmd = "mysqldump -u " + "lolo" + " -p" + "lolo" + "  --databases " + dbName
				+ " -r " + savePath;
		Process runtimeProcess = null;
		/*try {*/
			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
		/*} catch (IOException e) {
			e.printStackTrace();
		}
		*/int processComplete = 0;
		processComplete = runtimeProcess.waitFor();/*
		try {
			processComplete = runtimeProcess.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

/*
		String folderPath = "/home/laurent/ultimateProjects/phase3/tfe_implementation/backupForDownload";
*/
		String filename = "Gradation_DB_BackUp.sql";
		MediaType mediaType = MediaTypeSetting.returnForFileName(this.servletContext, filename);
		File file = new File(folderPath + "/" + filename);
		System.out.println(file.getAbsolutePath());
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachement;filename=" + file.getName())
				.contentType(mediaType)
				.contentLength(file.length())
				.body(resource);
	}

}
