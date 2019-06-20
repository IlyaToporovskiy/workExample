package hello.controller;

import hello.entity.Person;
import hello.repository.PersonRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    public LdapTemplate ldapTemplate;

    @GetMapping(value = "/showAllPerson")
    public @ResponseBody List<String> showAllPerson(){
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = userDetails.getUsername();
        PersonRepoImpl personRepo = new PersonRepoImpl();
        personRepo.setLdapTemplate(ldapTemplate);
        List<Person> users = personRepo.findByName(userName);
        List<String> allPersonNames= personRepo.getAllPersonNames();

        return allPersonNames;
    }

    @GetMapping(value = "/showCurrentUser")
    public @ResponseBody String showCurrentUser(){
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = userDetails.getUsername();
        PersonRepoImpl personRepo = new PersonRepoImpl();
        personRepo.setLdapTemplate(ldapTemplate);
        List<Person> users = personRepo.findByName(userName);
        personRepo.getAllPersonNames();
        System.out.println(users.get(0).getPhone() );
        System.out.println(personRepo.getAllPersonNames());

        return users.get(0).getFullName();
    }

//    /*@GetMapping(value = "/showTelUser")
//    public @ResponseBody String showTelUser(){
//        UserDetails userDetails =
//                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        String userName = userDetails.getUsername();
//        PersonRepoImpl personRepo = new PersonRepoImpl();
//        personRepo.setLdapTemplate(ldapTemplate);
//
//        List<Person> users = personRepo.findByName(userName);
//        personRepo.getAllPersonNames();
//        users.get(0).getPhone();
//        List<Person> telUser=personRepo.findByTel("123");
//        System.out.println(users.get(0).getPhone() );
//        System.out.println(personRepo.getAllPersonNames());
//
//        return users.get(0).getFullName();
//    }*/




}
