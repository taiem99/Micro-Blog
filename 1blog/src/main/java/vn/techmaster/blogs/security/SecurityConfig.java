package vn.techmaster.blogs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import vn.techmaster.blogs.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserService userService;
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
  
        http.formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login_perform")
            .defaultSuccessUrl("/login_success", true)
            .failureUrl("/login_error")
            .failureHandler(new CustomAuthenFailureHandler())
            .and()
            .logout()
            .logoutUrl("/perform_logout")
            .logoutSuccessUrl("/logout_success")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler(logoutSuccessHandler())
            .and()
            .rememberMe().key("RememberMeOption").tokenValiditySeconds(7 * 24 * 60 * 60);
        http.authorizeRequests()
          .mvcMatchers("/admin").hasAuthority("ADMIN")
          .mvcMatchers("/post").hasAnyAuthority("ADMIN", "AUTHOR", "EDITOR")
          .mvcMatchers("/author").hasAnyAuthority("AUTHOR")
         
          .mvcMatchers("/editor").hasAnyAuthority("EDITOR")
          .mvcMatchers("/public").permitAll()
          .mvcMatchers("/search").permitAll()
          .mvcMatchers("/h2-console/**").permitAll()
          .and().csrf().ignoringAntMatchers("/h2-console/**") //https://jessitron.com/2020/06/15/spring-security-for-h2-console/
          .and().headers().frameOptions().sameOrigin();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder());
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }
}
