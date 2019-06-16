package hello.repository;

import org.springframework.ldap.core.LdapTemplate;

import java.util.List;

public interface PersonRepo {
   void setLdapTemplate(LdapTemplate ldapTemplate);
   List<String> getAllPersonNames();

}
