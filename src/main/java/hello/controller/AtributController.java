package hello.controller;

import hello.entity.Person;
import hello.repository.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AtributController {

    @Autowired
    public PersonRepo personRepo;

    @RequestMapping(method = RequestMethod.GET,value = "/")
    public ModelAndView home()
    {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername().toString());


        return new ModelAndView("index");
    }

    @RequestMapping(method = RequestMethod.GET,value = "/response")
    public ModelAndView response()
    {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername().toString());


        return new ModelAndView("login");
    }

    @RequestMapping(method = RequestMethod.GET,value = "/show")
    public String show(Person person)
    {
        return "login";
    }
    @RequestMapping(method = RequestMethod.POST,value = "/show")
    public String show(@ModelAttribute @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("error");
            return "login";
        }
        System.out.println(person);
        return "login";

    }

}
