package gradation.implementation.presentationtier.controller;

import gradation.implementation.presentationtier.exception.CreatorNotMatchingException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CreatorNotMatchingException.class)
    public String errorMatchingRedirection(CreatorNotMatchingException ex, Model model){
        String errorMsg = "You do not have the permission to do that : not your own activity.";
        model.addAttribute("errorMsg", errorMsg);
        return "global/error";
    }

}
