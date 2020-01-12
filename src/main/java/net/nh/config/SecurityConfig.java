package net.nh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import net.nh.domain.User;
import net.nh.repository.UserRepository;
import net.nh.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Create a default user - password is 'password'
        userRepository.save(new User("nabil", "Nabil Hassan", "password"));

        // Setup HTTP security
        http.authorizeRequests()
                // secure reading pages but leave other pages including (/login) unsecured
                .antMatchers("/reading/*").access("hasAuthority('READER')")
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/**").permitAll()
                //
                .and()
                // enable form login, and use default login form
                .formLogin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Use the no-op password encoder as this is testing - should use a stronger encoder in reality
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
