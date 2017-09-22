package pl.beng.thesis.security;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pl.beng.thesis.model.Employee;
import pl.beng.thesis.repository.EmployeeRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private EmployeeRepository employeeRepository;
    private SecurityConstants securityConstants;

    JWTAuthorizationFilter(AuthenticationManager authManager) {

        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        initializeDependencies(request);
        String header = request.getHeader(securityConstants.getHeaderString());

        if (header == null || !header.startsWith(securityConstants.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(securityConstants.getHeaderString());
        if (token != null) {

            /* parse the token. */
            String user = Jwts.parser()
                    .setSigningKey(securityConstants.getSecret())
                    .parseClaimsJws(token.replace(securityConstants.getTokenPrefix(), ""))
                    .getBody()
                    .getIssuer();

            Employee employee = employeeRepository.findByUsername(user);
            if (employee != null) {
                return new UsernamePasswordAuthenticationToken(user, null, employee.getAuthorities());
            } else {
                logger.error("Authentication failed!");
                return null;
            }
        } else {
            logger.error("Given token is empty!");
            return null;
        }
    }


    /* Initialize spring components */
    private void initializeDependencies(HttpServletRequest request) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.
                getWebApplicationContext(request.getServletContext());

        employeeRepository = webApplicationContext.getBean(EmployeeRepository.class);
        securityConstants = webApplicationContext.getBean(SecurityConstants.class);
    }
}

