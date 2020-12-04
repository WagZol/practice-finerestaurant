package ITCExcercies.ITCRestaurant.Config;

import ITCExcercies.ITCRestaurant.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication()
//                .withUser("user1@gmail.com").password(passwordEncoder().encode("user1Pass")).roles("USER")
//                .and()
//                .withUser("user2@gmail.com").password(passwordEncoder().encode("user2Pass")).roles("USER")
//                .and()
//                .withUser("admin@gmail.com").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/home",
                        "/reservation_admin",
                        "/menu_admin",
                        "/reservation",
                        "/menu",
                        "/about").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login")
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID");
//                .and()
//                .headers().cacheControl().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

//    @Bean
//    public Filter shallowEtagHeaderFilter() {
//        return new ShallowEtagHeaderFilter();
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}
