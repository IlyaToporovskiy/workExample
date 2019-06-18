package hello.service;

import hello.entity.Person;
import hello.repository.PersonRepo;
import org.apache.tomcat.jni.Directory;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.ldap.LdapName;

public class PersonService implements /*PersonService,*/ BaseLdapNameAware {


    private PersonRepo personRepo;
//    DirectoryType
    private LdapName basePath;

    public void setBaseLdapPath(LdapName basePath) {
        this.basePath = basePath;
    }

    private LdapName getFullPersonDn(Person person) {
        return LdapNameBuilder.newInstance(basePath)
                .add(person.getDn())
                .build();
    }

}

