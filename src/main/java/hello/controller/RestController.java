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

    @GetMapping(value = "/showAllPerson2")
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
}
