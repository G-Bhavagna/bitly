package com.url.security.jwt;

import com.url.service.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("$jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    // token is passed via Authorization header
    // Authorization -> Bearer <TOKEN> syntax
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateJwtToken(UserDetailsImpl userDetails) {
        String username=userDetails.getUsername();
        String roles=userDetails.getAuthorities().stream()
                .map(authorituy->authorituy.getAuthority())
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(username)
                .claim("roles",roles)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime()+jwtExpirationMs)))
                .signWith(key())
                .compact();
    }


    public String getuserNamefromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                   .verifyWith((SecretKey) key())
                   .build().parseSignedClaims(authToken);
            return true;
        } catch (JwtException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

}
