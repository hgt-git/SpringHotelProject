package jProject;



import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;




import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import java.security.AuthProvider;
import java.util.Arrays;




@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .antMatchers("/reservation/**").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/checkouts/**").hasAnyAuthority("USER","ADMIN")
        ;
        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint);
    }


    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    public DataSource mydataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/spdb");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }



    @AliasFor("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {


            auth.jdbcAuthentication().dataSource(mydataSource()).passwordEncoder(passwordEncoder).usersByUsernameQuery("SELECT email, password, enabled FROM user_data WHERE email = ?").authoritiesByUsernameQuery("SELECT email, authority FROM user_data WHERE email = ?");

    }


}
