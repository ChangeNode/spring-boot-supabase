package com.changenode.frisson.websecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthorizeFilter extends BasicAuthenticationFilter {

    private final SupabaseAuthService supabaseAuthService;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthorizeFilter.class);

    public JwtAuthorizeFilter(AuthenticationManager authManager, SupabaseAuthService supabaseAuthService) {
        super(authManager);
        this.supabaseAuthService = supabaseAuthService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        if (req == null)
            return;

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        if (request == null)
            return null;
        if (request.getCookies() == null)
            return null;
        for (Cookie cookie : request.getCookies())
            if ("access-token".compareToIgnoreCase(cookie.getName()) == 0) {
                SupabaseUser user = supabaseAuthService.user(cookie.getValue());
                if (user == null)
                    return null;
                return new UsernamePasswordAuthenticationToken(user, user.getPassword(), new ArrayList<>());
            }

        return null;
    }
}
