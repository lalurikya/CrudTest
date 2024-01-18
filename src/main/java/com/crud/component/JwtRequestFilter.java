package com.crud.component;

import com.crud.utility.CustomAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authorizationHeaderValue = request.getHeader("Authorization");//break point untuk debug ketika sudah dapat token, tapi token nya invalid
        String username = null;
        String token = null;

        try{
            if(authorizationHeaderValue != null){
                token = authorizationHeaderValue.replace("Bearer ", "");
                username = jwtManager.getUsername(token);//proses pembongkaran terjadi di JwtManager.java
            }
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if(username != null && authentication == null){//proses pengecekan
                var userDetails = userDetailsService.loadUserByUsername(username);
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,  null, userDetails.getAuthorities()//proses pemberian label
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception exception){
            failureHandler.onAuthenticationFailure(request, response, new CustomAuthenticationException("Token is invalid"));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
