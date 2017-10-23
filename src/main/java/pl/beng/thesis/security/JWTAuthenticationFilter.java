package pl.beng.thesis.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pl.beng.thesis.model.Employee;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AuthenticationManager authenticationManager;
    private SecurityConstants securityConstants;

    JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        initializeDependencies(request);
        try {
            Employee employee = new ObjectMapper().readValue(request.getInputStream(), Employee.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(employee.getUsername(), employee.getPassword())
            );
        } catch (IOException ex) {
            logger.error("Authentication failed! " + ex);
            response.setStatus(400);
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        Employee employee = ((Employee) auth.getPrincipal());

        String token = Jwts.builder()
                .setIssuer(employee.getUsername())
                .setSubject(employee.getUsername() + "token")
                .claim("scope", employee.getRoles())
                .setExpiration(new Date(System.currentTimeMillis() + securityConstants.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, securityConstants.getSecret())
                .compact();
        response.addHeader(securityConstants.getHeaderString(), securityConstants.getTokenPrefix() + token);
        response.setContentType("application/json");
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.getOutputStream().write("{}".getBytes());
    }

    /* Initialize spring components */
    private void initializeDependencies(HttpServletRequest request) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.
                getWebApplicationContext(request.getServletContext());

        securityConstants = webApplicationContext.getBean(SecurityConstants.class);
    }
}
