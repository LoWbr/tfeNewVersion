package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.*;
import gradation.implementation.datatier.entities.Activity;
import gradation.implementation.presentationtier.form.SearchActivityForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class GlobalController {

    private ActivitySettingService activitySettingService;
    private ActivityService activityService;
    private SportsManService sportsManService;

    @Autowired
    public GlobalController(ActivitySettingService activitySettingService,
                            ActivityService activityService, SportsManService sportsManService){
        this.activityService = activityService;
        this.activitySettingService = activitySettingService;
        this.sportsManService = sportsManService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHome(Model model, Principal principal) {
        int count = 6;
        List<Activity> recent = new ArrayList<Activity>();
        for (Activity activity: activityService.OnTime(LocalDate.now())) {
            if (count > 0) {
                recent.add(activity);
            }
            count--;
        }
        if(principal != null) {
            model.addAttribute("current", sportsManService.findCurrentUser(principal.getName()));
        }
        model.addAttribute("soon", recent);
        return "global/home";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String signIn(@RequestParam(value = "error", required = false) String error,
                         Model model, Principal principal) {
        if(principal != null){
            return "redirect:/403";
        }
        String warning = null;
        if(error != null){
            byte[] getErrorBytes = Base64.getDecoder().decode(error);
            warning = new String(getErrorBytes);
        }
        model.addAttribute("warning",warning);
        return "global/signIn";
    }

    @RequestMapping(value = "/contactUs", method = RequestMethod.GET)
    public String contactUs() {
        return "global/contactUs";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "global/about";
    }

    @RequestMapping(value ="/search", method = RequestMethod.GET)
    public String search(@ModelAttribute("searchActivityForm") SearchActivityForm searchActivityForm,
                         Model model, @RequestParam(required = false) Boolean there, Principal principal) {
        if(principal != null){
            model.addAttribute("sportsMan", sportsManService.findCurrentUser(principal.getName()));
        }
        else{
            model.addAttribute("sportsMan", null);
        }
        model.addAttribute("allLevels", activitySettingService.getAllLevels());
        model.addAttribute("allKinds", activitySettingService.getAllActivityTypes());
        model.addAttribute("allEvents",activityService.OnTime(LocalDate.now()));
        model.addAttribute("searchActivityForm",searchActivityForm );
        return "global/search";
    }

    @RequestMapping(value ="/searchbyfilter", method = RequestMethod.POST)
    public String searchByFilter(@ModelAttribute("searchActivityForm") SearchActivityForm searchActivityForm,
                                 Model model, Principal principal) {
        if(principal != null){
            model.addAttribute("sportsMan", sportsManService.findCurrentUser(principal.getName()));
        }
        if(searchActivityForm.getCity().equals("")){
            searchActivityForm.setCity(null);
        }
        if(searchActivityForm.getDate().equals("")){
            searchActivityForm.setDate(null);
        }
        model.addAttribute("allLevels", activitySettingService.getAllLevels());
        model.addAttribute("allKinds", activitySettingService.getAllActivityTypes());
        model.addAttribute("allEvents", activityService.findForSearch(searchActivityForm));
        model.addAttribute("searchActivityForm", searchActivityForm);
        return "global/search";

    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String error(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            System.out.println(principal.getName());
            model.addAttribute("msg", "Hi user, you do not have permission to access this page!");
        } else {
            return "redirect:/401";
        }

        return "global/accessdenied";
    }

    @RequestMapping(value = "/401", method = RequestMethod.GET)
    public String notaccess() {
        return "global/notAuthenticated";
    }

}
