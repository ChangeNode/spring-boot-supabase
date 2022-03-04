package com.changenode.frisson.websecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SupabaseAuthService supabaseAuthService;
    private final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);
    @Value("${spring.profiles.active:}")
    private String activeProfile;

    public WebSecurityConfiguration(SupabaseAuthService supabaseAuthService) {
        this.supabaseAuthService = supabaseAuthService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Only allow frames if using h2 as the database
        if (activeProfile.contains("h2")) {
            logger.info("h2 -> frame options disabled.");
            http.headers().frameOptions().disable();
        } else {
            logger.info("h2 not found.");
        }

        http
                .addFilter(new JwtAuthorizeFilter(authenticationManager(), supabaseAuthService))
                .authorizeRequests()
                .antMatchers("/**/*.html", "/pinegrow.json").denyAll()
                .antMatchers("/assets/**", "/blocks.css", "/public/**", "/bootstrap/**", "/webjars/**", "/", "/h2-console/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/public/sign-in")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
        ;
        logger.info("Security configured.");
    }
}
