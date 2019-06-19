package hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan//(basePackages = {"hello.*"})
//@Profile("default")
//@EnableLdapRepositories(basePackages = "hello.**")
public class ConfigLdap {
    @Autowired
    Environment env;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();

        contextSource.setUrl(env.getRequiredProperty("ldap.urls"));
//        contextSource().setBase(env.getRequiredProperty("ldap.partitionSuffix"));
        contextSource.setBase(env.getRequiredProperty("ldap.base.dn"));
        contextSource.setUserDn(env.getRequiredProperty("ldap.username"));
        contextSource.setPassword(env.getRequiredProperty("ldap.password"));
        return contextSource;
    }


    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }
    /*@Bean
    public LdapClient ldapClient(){
        return new LdapClient();
    }*/
}
