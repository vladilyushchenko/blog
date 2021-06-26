package com.leverx.blog.config.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Log
public class JwtProvider {
    private final String jwtSecret = "javamaster";

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token, ServletResponse servletResponse) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            processInvalidToken(servletResponse);
            log.severe("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            processInvalidToken(servletResponse);
            log.severe("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            processInvalidToken(servletResponse);
            log.severe("Malformed jwt");
        } catch (SignatureException sEx) {
            processInvalidToken(servletResponse);
            log.severe("Invalid signature");
        } catch (Exception e) {
            processInvalidToken(servletResponse);
            log.severe("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    private void processInvalidToken(ServletResponse response) {
        response.setContentLength(0);
    }
}