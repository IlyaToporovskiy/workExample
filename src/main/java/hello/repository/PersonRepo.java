package hello.repository;

import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;

import java.util.List;
@EnableLdapRepositories
public interface PersonRepo {
   void setLdapTemplate(LdapTemplate ldapTemplate);
   List<String> getAllPersonNames();

}
