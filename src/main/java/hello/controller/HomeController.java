package hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//@RestController
//public class HomeController {
//
//
//    @GetMapping("/")
//    public String index() {
//        return "Welcome to the home page!";
//    }
//}

@Controller
@RequestMapping("/")
public class HomeController {


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView home()
    {
        return new ModelAndView("index");
    }


    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public ModelAndView interfaces()
    {
        return new ModelAndView("profile");
    }
}

