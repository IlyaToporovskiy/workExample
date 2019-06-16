package hello.repository;

import hello.entity.Person;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.core.DirContextAdapter;

import javax.naming.Name;
import javax.naming.ldap.LdapName;

public class GroupRepo implements BaseLdapNameAware {
    private LdapTemplate ldapTemplate;
    private LdapName baseLdapPath;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public void setBaseLdapPath(LdapName baseLdapPath) {
        this.setBaseLdapPath(baseLdapPath);
    }

    public void addMemberToGroup(String groupName, Person person) {
        Name groupDn = buildGroupDn(groupName);
        Name userDn = buildPersonDn(
                person.getFullName(),
                person.getCompany(),
                person.getCountry());

        DirContextOperations ctx = ldapTemplate.lookupContext(groupDn);
        ctx.addAttributeValue("member", userDn);

        ldapTemplate.update(ctx);
    }

    public void removeMemberFromGroup(String groupName, Person person) {
        Name groupDn = buildGroupDn(groupName);
        Name userDn = buildPersonDn(
                person.getFullName(),
                person.getCompany(),
                person.getCountry());

        DirContextOperations ctx = ldapTemplate.lookupContext(groupDn);
        ctx.removeAttributeValue("member", userDn);

        ldapTemplate.update(ctx);
    }

    private Name buildGroupDn(String groupName) {
        return LdapNameBuilder.newInstance("ou=Groups")
                .add("cn", groupName).build();
    }

    private Name buildPersonDn(String fullname, String company, String country) {
        return LdapNameBuilder.newInstance(baseLdapPath)
                .add("c", country)
                .add("ou", company)
                .add("cn", fullname)
                .build();
    }
}
