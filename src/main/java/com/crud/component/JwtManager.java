package com.crud.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtManager {
    private final String SECRET_KEY = "liberate-tutume-ex-inferis-ad-astra-per-aspera";

    private Date getIssueDate(){
        var now = LocalDateTime.now();
        var systemTime = now.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(systemTime);
    }

    private Date getExpiredDate(){
        var expireAt = LocalDateTime.now().plusMinutes(60);
        var systemTime = expireAt.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(systemTime);
    }

    //2. Membuat token
    public String generateToken(String username, String subject, String audience, String secretKey){
        var builder = Jwts.builder();
        builder = builder
                .setSubject(subject)
                .setAudience(audience)
                .claim("username", username)
                .setIssuer("http://localhost:8100")
                .setIssuedAt(getIssueDate())
//                .setExpiration(getExpiredDate())
                .signWith(SignatureAlgorithm.HS256, secretKey);
        return builder.compact();
    }

    public Claims getClaims(String token){
        var parser = Jwts.parser().setSigningKey(SECRET_KEY);
        var signatureAndClaims = parser.parseClaimsJws(token);
        var claims = signatureAndClaims.getBody();
        return claims;
    }

    public String getUsername(String token){
        var claims = getClaims(token);
        return claims.get("username", String.class);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        var username = getUsername(token);
        var isUsernameMatched = username.equals(userDetails.getUsername());
        return isUsernameMatched;
    }


}
