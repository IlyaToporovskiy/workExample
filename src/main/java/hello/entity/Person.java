package hello.entity;

import org.springframework.ldap.odm.annotations.*;

import javax.naming.Name;

@Entry(objectClasses = {"inetOrgPerson", "organizationalPerson", "person", "top"})
public class Person {
    @Id
    private Name dn;

    @Attribute(name = "cn")
    @DnAttribute(value = "cn", index = 2)
    private String fullName;

    @Attribute(name = "sn")
    private String lastName;

    @Attribute(name = "uid")
    private String uid;

    @Attribute(name = "description")
    private String description;

    @Transient
    @DnAttribute(value = "c", index = 0)
    private String country;

    @Transient
    @DnAttribute(value = "ou", index = 1)
    private String company;

    @Attribute(name = "telephoneNumber")
    private String phone;

    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
