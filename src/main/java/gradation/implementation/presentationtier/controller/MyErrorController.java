package gradation.implementation.presentationtier.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping(value = "error", method = RequestMethod.GET)
    public String getErrorPage(HttpServletRequest httpRequest, Model model) {

        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
        String index = String.valueOf(String.valueOf(httpErrorCode).charAt(0));
        /*switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
            default:{
                errorMsg = Integer.toString(httpErrorCode);
                break;
            }
        }*/
        System.out.println(httpErrorCode);
        if(index.equals("4")){
            errorMsg = "You tried a bad or unauthorized request.";
        }
        else{
            errorMsg = "There is an internal error.";
        }
        model.addAttribute("errorMsg", errorMsg);
        return "global/error";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
