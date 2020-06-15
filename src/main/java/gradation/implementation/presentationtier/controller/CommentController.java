package gradation.implementation.presentationtier.controller;

import gradation.implementation.businesstier.service.contractinterface.*;
import gradation.implementation.datatier.entities.Comment;
import gradation.implementation.datatier.entities.NewsType;
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

@Controller
public class CommentController {

    private ActivityService activityService;
    private ActivitySettingService activitySettingService;
    private SportsManService sportsManService;
    NewsService newsService;

    @Autowired
    public CommentController(ActivityService activityService, SportsManService sportsManService,
                             NewsService newsService, ActivitySettingService activitySettingService) {
        this.activityService = activityService;
        this.sportsManService = sportsManService;
        this.newsService = newsService;
        this.activitySettingService = activitySettingService;
    }

    @RequestMapping(value = "/user/createcomment{id}", method = RequestMethod.GET)
    public String createComment(@RequestParam Long id, Model model, Principal principal) {
        model.addAttribute("current_activity", activityService.getSpecificActivity(id));
        model.addAttribute("commentForm",
                activitySettingService.initiateCommentForm(activityService.getSpecificActivity(id),
                        sportsManService.findCurrentUser(principal.getName())));
        return "activity/createComment";
    }

    @RequestMapping(value="/user/addcomment", method = RequestMethod.POST)
    public String addComment(@Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if(commentForm.getAuthor().getId() == commentForm.getActivity().getCreator().getId()){
                return "redirect:/factory/ownactivity?id=" + commentForm.getActivity().getId() + "&error=" + true;
            }
            else {
                return "redirect:/activity?id=" + commentForm.getActivity().getId() + "&error=" + true;
            }
        }
        this.activitySettingService.createComment(commentForm);
        this.newsService.returnCommentEventNew(commentForm.getAuthor(), commentForm.getActivity(), NewsType.COMMENTED_EVENT);
        if(commentForm.getAuthor().getId() == commentForm.getActivity().getCreator().getId()){
            return "redirect:/ownactivity?id=" + commentForm.getActivity().getId();
        }
        else {
            return "redirect:/activity?id=" + commentForm.getActivity().getId();
        }
    }

}
