package gradation.implementation.presentationtier.controller;

import gradation.implementation.presentationtier.exception.CreatorNotMatchingException;
import gradation.implementation.presentationtier.exception.DoubleRequestException;
import gradation.implementation.presentationtier.exception.EmptyRequestException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CreatorNotMatchingException.class)
    public String errorMatchingRedirection(CreatorNotMatchingException ex, Model model){
        String errorMsg = "You do not have the permission to do that : you aren't the creator of this activity.";
        model.addAttribute("errorMsg", errorMsg);
        return "global/error";
    }

    @ExceptionHandler(DoubleRequestException.class)
    public String errorMatchingRedirection(DoubleRequestException ex, Model model){
        String errorMsg = "Bad Request, it is a duplicate action.";
        model.addAttribute("errorMsg", errorMsg);
        return "global/error";
    }

    @ExceptionHandler(EmptyRequestException.class)
    public String errorMatchingRedirection(EmptyRequestException ex, Model model){
        String errorMsg = "This is an empty request : any matching component.";
        model.addAttribute("errorMsg", errorMsg);
        return "global/error";
    }

}
