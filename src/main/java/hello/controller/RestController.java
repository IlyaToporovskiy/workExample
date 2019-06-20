package hello.controller;

import hello.entity.Person;
import hello.repository.PersonRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

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

    @GetMapping(value = "/showDescr")
    public @ResponseBody String showDescr(){
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        PersonRepoImpl personRepo = new PersonRepoImpl();
        personRepo.setLdapTemplate(ldapTemplate);
        List<Person> users = personRepo.findByName(userName);
        String message;
        if(users.isEmpty()){
            message="Нет такого пользователя";
        }else {
            message=users.get(0).getDescription();
        }
//        System.out.println("findDescr"+personRepo.findDescr(userName));
//        System.out.println("findByUid"+personRepo.findByUid(userName));
        return message;
    }

    @GetMapping(value = "/showGroupRole")
    public @ResponseBody List showGroupRole(){
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        PersonRepoImpl personRepo = new PersonRepoImpl();
        personRepo.setLdapTemplate(ldapTemplate);

//        List<Person> users = personRepo.findByName(userName);
       List a= (List) userDetails.getAuthorities();
        System.out.println(a.get(0).toString());

        return a;
    }


}
