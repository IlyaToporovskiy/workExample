package hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AtributController {

    @RequestMapping(method = RequestMethod.GET,value = "/response")
    public ModelAndView home()
    {
        return new ModelAndView("test");
    }
}
