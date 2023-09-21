package com.example.atmdemo.security.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@NoArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = jwtUtils.getJwtFromCookies(request);
        String refreshToken = jwtUtils.getRefreshJwtFromCookies(request);
        if (token == null && refreshToken != null && jwtUtils.validateJwtToken(refreshToken)) {
            // generate new access token from refresh token add it to response
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(jwtUtils.getRefreshTokenValidateAndGenerateAccessToken(request));
            response.addHeader("Set-Cookie", jwtCookie.toString());

            // new content might be buggy
        }
        if (token != null && jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUserNameFromJwtToken(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
