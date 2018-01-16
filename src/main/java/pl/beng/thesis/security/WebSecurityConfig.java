package pl.beng.thesis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

            /* Address section */
            .antMatchers(HttpMethod.GET, "/addresses").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.GET, "/addresses/*").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.PUT, "/addresses/*").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.POST, "/addresses/address").hasAnyRole("ADMIN", "DEV", "HR")

            /* Developer section */
            .antMatchers(HttpMethod.GET, "/developers/developer/*/cv").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.GET, "/developers").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.GET, "/developers/*").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.POST, "/developers/developer").hasAnyRole("ADMIN", "HR")
            .antMatchers(HttpMethod.POST, "/developers/developer/*/educations/education").hasAnyRole("ADMIN", "HR", "DEV")
            .antMatchers(HttpMethod.POST, "/developers/developer/*/projects/project").hasAnyRole("ADMIN", "HR", "DEV")
            .antMatchers(HttpMethod.POST, "/developers/developer/*/skills").hasAnyRole("ADMIN", "HR", "DEV")

            /* Education section */
            .antMatchers(HttpMethod.GET, "/educations").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.GET, "/educations/*").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.PUT, "/educations/*").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.POST, "/educations/education").hasAnyRole("ADMIN", "DEV", "HR")

            /* Employee section */
            .antMatchers(HttpMethod.GET, "/employees").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.GET, "/employees/*").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.PUT, "/employees/*").hasAnyRole("ADMIN", "HR", "DEV")
            .antMatchers(HttpMethod.PUT, "/employees/*/{username}/password").hasAnyRole("ADMIN", "HR", "DEV")
            .antMatchers(HttpMethod.GET, "/employees/employee").permitAll()
            .antMatchers(HttpMethod.POST, "/employees/employee").hasAnyRole("ADMIN", "HR")
            .antMatchers(HttpMethod.POST, "/employees/employee/{username}").hasAnyRole("ADMIN", "HR", "DEV")

            /* Project section */
            .antMatchers(HttpMethod.GET, "/projects").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.GET, "/projects/*").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.PUT, "/projects/*").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.POST, "/projects/project").hasAnyRole("ADMIN", "DEV", "HR")

            /* Skill section */
            .antMatchers(HttpMethod.GET, "/skills").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.GET, "/skills/*").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.PUT, "/skills/*").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.POST, "/skills/skill").hasAnyRole("ADMIN", "DEV", "HR")

            /* Outlook */
            .antMatchers(HttpMethod.GET, "/outlook/mails").hasAnyRole("ADMIN", "DEV", "HR")
            .antMatchers(HttpMethod.POST, "/outlook/mail").hasAnyRole("ADMIN", "DEV", "HR")

            /* Other */
            .antMatchers(HttpMethod.POST, "/login").permitAll();
//            .anyRequest().authenticated();

        http.cors().and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));

        http.csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().headers().frameOptions().disable()
            .and().anonymous().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return authProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedHeader("Access-Control-Allow-Origin");
        configuration.addAllowedHeader("Access-Control-Allow-Methods");
        configuration.addAllowedHeader("Access-Control-Allow-Headers");
        configuration.addAllowedHeader("Access-Control-Max-Age");
//        configuration.setAllowedOrigins(Collections.singletonList("https://microsoft.com"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
