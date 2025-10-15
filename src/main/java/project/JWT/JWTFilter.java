package project.JWT;

import io.jsonwebtoken.Claims;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    Claims claims = null;
    private String userName = null;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // token validation here
        if (httpServletRequest
                .getServletPath()
                .matches("/user/login|/user/signup")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String token = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                userName = jwtUtil.extractUserName(token);
                claims = jwtUtil.extractAllClaims(token);
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customerUsersDetailsService.loadUserByUsername(userName);
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }


    public boolean isAdmin() {
        if (claims == null) {
            logger.warn("Claims is null - treating as non-admin");
            return false;
        }

        Object roleObj = claims.get("role");
        if (roleObj == null) {
            logger.warn("Role claim is null");
            return false;
        }

        String role = roleObj.toString();
        logger.info("Checking admin - role: " + role);
        return "admin".equalsIgnoreCase(role);
    }

    public boolean isUser() {
        if (claims == null) {
            logger.warn("Claims is null - treating as non-user");
            return false;  // Or true to block access
        }

        Object roleObj = claims.get("role");
        if (roleObj == null) {
            logger.warn("Role claim is null");
            return true;  // Safer to block if role is missing
        }

        String role = roleObj.toString();
        logger.info("Checking user - role: " + role);
        return "user".equalsIgnoreCase(role);
    }
    public String getCurrentUser() {
        return userName;
    }
}
