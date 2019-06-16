package hello.repository;

/*import hello.entity.Person;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.*;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;

import javax.naming.Name;
import javax.naming.directory.*;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;


import static org.springframework.ldap.query.LdapQueryBuilder.query;

public class PersonRepoImpl implements PersonRepo {
    private LdapTemplate ldapTemplate;
    public static final String BASE_DN = "dc=example,dc=com";

    //AttributesMapper that returns a single attribute
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public List<String> getAllPersonNames() {
        return ldapTemplate.search(
                query().where("objectclass").is("person"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException, javax.naming.NamingException {
                        return (String) attrs.get("cn").get();
                    }
                });
    }

    //AttributesMapper that returns a Person object
    private class PersonAttributesMapper implements AttributesMapper<Person> {
        public Person mapFromAttributes(Attributes attrs) throws NamingException, javax.naming.NamingException {
            Person person = new Person();
            person.setFullName((String) attrs.get("cn").get());
            person.setLastName((String) attrs.get("sn").get());
            person.setDescription((String) attrs.get("description").get());
            return person;
        }
    }

    public List<Person> getAllPersons() {
        return ldapTemplate.search(query()
                .where("objectclass").is("person"), new PersonAttributesMapper());
    }

    //A lookup resulting in a Person object
    public Person findPerson(String dn) {
        return ldapTemplate.lookup(dn, new PersonAttributesMapper());
    }

    public List<String> getPersonNamesByLastName(String lastName) {

        LdapQuery query = query()
                .base("dc=261consulting,dc=com")
                .attributes("cn", "sn")
                .where("objectclass").is("person")
                .and("sn").is(lastName);

        return ldapTemplate.search(query,
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException, javax.naming.NamingException {

                        return (String) attrs.get("cn").get();
                    }
                });
    }

    protected Name buildDn(Person p) {
        return LdapNameBuilder.newInstance(BASE_DN)
                .add("c", p.getCountry())
                .add("ou", p.getCompany())
                .add("cn", p.getFullName())
                .build();
    }

    protected Person buildPerson(Name dn, Attributes attrs) {
        Person person = new Person();
        person.setCountry(LdapUtils.getStringValue(dn, "c"));
        person.setCompany(LdapUtils.getStringValue(dn, "ou"));
        person.setFullName(LdapUtils.getStringValue(dn, "cn"));
        // Populate rest of person object using attributes.

        return person;
    }

    //Adding data using Attributes

    public void create(Person p) {
        Name dn = buildDn(p);
        ldapTemplate.bind(dn, null, buildAttributes(p));
    }

    private Attributes buildAttributes(Person p) {
        Attributes attrs = new BasicAttributes();
        BasicAttribute ocattr = new BasicAttribute("objectclass");
        ocattr.add("top");
        ocattr.add("person");
        attrs.put(ocattr);
        attrs.put("cn", "Some Person");
        attrs.put("sn", "Person");
        return attrs;
    }

    public void delete(Person p) {
        Name dn = buildDn(p);
        ldapTemplate.unbind(dn);
    }

    //2.5.1. Updating using rebind
    public void update(Person p) {
        Name dn = buildDn(p);
        ldapTemplate.rebind(dn, null, buildAttributes(p));
    }

    // Updating using modifyAttributes
    public void updateDescription(Person p) {
        Name dn = buildDn(p);
        Attribute attr = new BasicAttribute("description", p.getDescription());
        ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
        ldapTemplate.modifyAttributes(dn, new ModificationItem[]{item});
    }

    private static class PersonContextMapper implements ContextMapper {
        public Object mapFromContext(Object ctx) {
            DirContextAdapter context = (DirContextAdapter) ctx;
            Person p = new Person();
            p.setFullName(context.getStringAttribute("cn"));
            p.setLastName(context.getStringAttribute("sn"));
            p.setDescription(context.getStringAttribute("description"));
            return p;
        }
    }
   *//* public Person findByPrimaryKey(
            String name, String company, String country) {
        Name dn = buildDn(name, company, country);
        return ldapTemplate.lookup(dn, new PersonContextMapper());
    }*//*

    public void update2(Person p) {
        Name dn = buildDn(p);
        DirContextOperations context = ldapTemplate.lookupContext(dn);

        context.setAttributeValue("cn", p.getFullName());
        context.setAttributeValue("sn", p.getLastName());
        context.setAttributeValue("description", p.getDescription());

        ldapTemplate.modifyAttributes(context);
    }

    public void create2(Person p) {
        Name dn = buildDn(p);
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues("objectclass", new String[] {"top", "person"});
        mapToContext(p, context);
        ldapTemplate.bind(context);
    }

    public void update3(Person person) {
        Name dn = buildDn(person);
        DirContextOperations context = ldapTemplate.lookupContext(dn);
        mapToContext(person, context);
        ldapTemplate.modifyAttributes(context);
    }

    protected void mapToContext (Person p, DirContextOperations context) {
        context.setAttributeValue("cn", p.getFullName());
        context.setAttributeValue("sn", p.getLastName());
        context.setAttributeValue("description", p.getDescription());
    }
}*/

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;

import hello.entity.Person;
import org.springframework.ldap.core.*;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

public class PersonRepoImpl implements PersonRepo {
    private LdapTemplate ldapTemplate;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public List<String> getAllPersonNames() {
        return ldapTemplate.search(
                query().where("objectclass").is("person"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException, javax.naming.NamingException {
                        return (String) attrs.get("cn").get();
                    }
                });
    }

    public void create(Person person) {
        DirContextAdapter context = new DirContextAdapter(buildDn(person));
        mapToContext(person, context);
        ldapTemplate.bind(context);
    }

    public void update(Person person) {
        Name dn = buildDn(person);
        DirContextOperations context = ldapTemplate.lookupContext(dn);
        mapToContext(person, context);
        ldapTemplate.modifyAttributes(context);
    }

    public void delete(Person person) {
        ldapTemplate.unbind(buildDn(person));
    }

    public Person findByPrimaryKey(String name, String company, String country) {
        Name dn = buildDn(name, company, country);
        return (Person) ldapTemplate.lookup(dn, getContextMapper());
    }

    public List findByName(String name) {
        LdapQuery query = query()
                .where("objectclass").is("person")
                .and("cn").whitespaceWildcardsLike("name");

        return ldapTemplate.search(query, getContextMapper());
    }

    public List findAll() {
        EqualsFilter filter = new EqualsFilter("objectclass", "person");
        return ldapTemplate.search(LdapUtils.emptyLdapName(), filter.encode(), getContextMapper());
    }

    protected ContextMapper getContextMapper() {
        return (ContextMapper) new PersonContextMapper();
    }

    protected Name buildDn(Person person) {
        return buildDn(person.getFullName(), person.getCompany(), person.getCountry());
    }

    protected Name buildDn(String fullname, String company, String country) {
        return LdapNameBuilder.newInstance()
                .add("c", country)
                .add("ou", company)
                .add("cn", fullname)
                .build();
    }

    protected void mapToContext(Person person, DirContextOperations context) {
        context.setAttributeValues("objectclass", new String[] {"top", "person"});
        context.setAttributeValue("cn", person.getFullName());
        context.setAttributeValue("sn", person.getLastName());
        context.setAttributeValue("description", person.getDescription());
    }

    private static class PersonContextMapper extends AbstractContextMapper<Person> {
        public Person doMapFromContext(DirContextOperations context) {
            Person person = new Person();
            person.setFullName(context.getStringAttribute("cn"));
            person.setLastName(context.getStringAttribute("sn"));
            person.setDescription(context.getStringAttribute("description"));
            return person;
        }
    }
}

