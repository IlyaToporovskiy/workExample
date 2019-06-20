package hello.repository;


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
import java.util.Optional;

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

    @Override
    public void deleteAll(Iterable<? extends Person> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    public Person findByPrimaryKey(String name, String company, String country) {
        Name dn = buildDn(name, company, country);
        return (Person) ldapTemplate.lookup(dn, getContextMapper());
    }

    public List<Person> findByName(String name) {
        LdapQuery query = query()
                .where("objectclass").is("person")
                .and("cn").whitespaceWildcardsLike(name);
//        return ldapTemplate.search(query, getContextMapper());
        return ldapTemplate.search(query, new PersonContextMapper());
    }
/*
    public List<Person> findByTel(String telephon){
        LdapQuery query=query()
                .where("objectclass").is("person")
                .and("telephoneNumber").whitespaceWildcardsLike(telephon);
        return ldapTemplate.search(query, new PersonContextMapper());
    }*/


    @Override
    public <S extends Person> S save(S s) {
        return null;
    }

    @Override
    public <S extends Person> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Person> findById(Name name) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Name name) {
        return false;
    }

    /*public List findAll() {
        EqualsFilter filter = new EqualsFilter("objectclass", "person");
        return ldapTemplate.search(LdapUtils.emptyLdapName(), filter.encode(), getContextMapper());
    }*/
    public List<Person> findAll() {
        return ldapTemplate.search(query()
                .where("objectclass").is("person"),PERSON_CONTEXT_MAPPER);
    }
    private final static ContextMapper<Person> PERSON_CONTEXT_MAPPER = new AbstractContextMapper<Person>() {
        @Override
        public Person doMapFromContext(DirContextOperations context) {
            Person person = new Person();

            LdapName dn = LdapUtils.newLdapName(context.getDn());
            person.setCountry(LdapUtils.getStringValue(dn, 0));
            person.setCompany(LdapUtils.getStringValue(dn, 1));
            person.setFullName(context.getStringAttribute("cn"));
            person.setLastName(context.getStringAttribute("sn"));
            person.setDescription(context.getStringAttribute("description"));
            person.setPhone(context.getStringAttribute("telephoneNumber"));

            return person;
        }
    };
    @Override
    public Iterable<Person> findAllById(Iterable<Name> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Name name) {

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

    @Override
    public Optional<Person> findOne(LdapQuery ldapQuery) {
        return Optional.empty();
    }

    @Override
    public Iterable<Person> findAll(LdapQuery ldapQuery) {
        return null;
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

