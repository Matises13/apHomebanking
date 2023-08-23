package com.ap.homebanking_ap.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/**").hasAuthority("USER");

        http.formLogin()
                .usernameParameter("name")
                .passwordParameter("pwd")
                .loginPage("/app/login");

        http.logout().logoutUrl("/app/logout");

        http.csrf().disable();

        httpSecurity.headers().frameOptions().disable();

        http.exceptionHandling().authenticationEntryPoint((req, res, exc)
                -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.formLogin().successHandler((req, res, auth)
                -> clearAuthenticationAttributes(req));
        http.formLogin().failureHandler((req, res, exc)
                -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }
}
