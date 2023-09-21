package com.example.atmdemo.security.JWT;

import com.example.atmdemo.models.User;
import com.example.atmdemo.repositories.UserRepository;
import com.example.atmdemo.security.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private final UserRepository userRepository;

    // general serv
    public User getUserFromRequest(HttpServletRequest request) {
        String token = getJwtFromCookies(request);
        if (token == null) {
            return null;
        }
        String email = getUserNameFromJwtToken(token);
        return userRepository.findByEmail(email).get();
    }

    // Cookie generating
    public ResponseCookie generateJwtCookie(Authentication userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getName());
        return generateCookie("token", jwt, "/", (int) (SecurityConstants.JWT_EXPIRATION_TIME / 1000));
    }
    public ResponseCookie generateJwtCookie(String username) {
        String jwt = generateTokenFromUsername(username);
        return generateCookie("token", jwt, "/", (int) (SecurityConstants.JWT_EXPIRATION_TIME / 1000));
    }
    public ResponseCookie generateRefreshJwtCookie(Authentication userPrincipal) {
        String jwt = generateRefreshTokenFromUsername(userPrincipal.getName());
        return generateCookie("refreshToken", jwt, "/", (int) (SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME/1000));
    }

    private ResponseCookie generateCookie(String name, String value, String path, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value).path(path).maxAge(maxAge).httpOnly(true).build();
        return cookie;
    }

    // token generation
    public String generateRefreshTokenFromUsername(String email) {
        Claims extraClaims = new DefaultClaims();
        UUID uuid = UUID.randomUUID();
        User user = userRepository.findByEmail(email).get();
        user.setUuid(uuid);
        extraClaims.put("sub", email);
        extraClaims.put("jti", uuid.toString());
        userRepository.save(user);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + SecurityConstants.JWT_EXPIRATION_TIME))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    // getting jwt from cookies

    public String getJwtFromCookies(HttpServletRequest request) {
        try {
            return getCookieValueByName(request, "token");
        } catch (Exception e) {
            return null;
        }
    }

    public String getRefreshJwtFromCookies(HttpServletRequest request) {
        try {
            return getCookieValueByName(request, "refreshToken");
        } catch (Exception e) {
            return null;
        }
    }

    // getting username from token
    public String getUserNameFromJwtToken(String token) {

        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserNameFromJwtRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody();
        String username = claims.get("sub", String.class);
        return username;
    }

    // generating new access token after validating refresh token
    public String getRefreshTokenValidateAndGenerateAccessToken(HttpServletRequest request){
        if(request.getCookies() == null || request.getCookies().length == 0){
            return null;
        }
        String refreshToken = getRefreshJwtFromCookies(request);
        if(validateJwtToken(refreshToken)){

            String username = getUserNameFromJwtRefreshToken(refreshToken);
            return username;
        }
        return null;
    }

    // validating
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    // clean cookies
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from("token", "CleanJwtCookie").path("/").build();
        return cookie;
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "CleanJwtRefreshCookie").path("/").build();
        return cookie;
    }

    // third level helper methods
    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }
    private Key key() {
        return Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET);
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
