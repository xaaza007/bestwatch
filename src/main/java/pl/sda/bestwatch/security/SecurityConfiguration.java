package pl.sda.bestwatch.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import pl.sda.bestwatch.BestwatchController;

@EnableWebSecurity
public class SecurityConfiguration {

    @Configuration
    public static class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**").authorizeRequests()
                    .anyRequest().authenticated()
                    .and().httpBasic().and().csrf().disable();
        }
    }

    @Configuration
    @Order(1)
    public static class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/app/**")
                    .authorizeRequests()
                    .antMatchers("/app/login").permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/app/login").loginProcessingUrl("/app/login");
        }
    }
}
