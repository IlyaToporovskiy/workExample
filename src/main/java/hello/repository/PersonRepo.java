package hello.repository;

import hello.entity.Person;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;

import java.util.List;
@EnableLdapRepositories
public interface PersonRepo extends LdapRepository<Person>{
   void setLdapTemplate(LdapTemplate ldapTemplate);
   List<String> getAllPersonNames();

}
