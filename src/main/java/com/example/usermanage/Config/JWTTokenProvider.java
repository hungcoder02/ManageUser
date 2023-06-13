package com.example.usermanage.Config;

import com.example.usermanage.Model.MyUserDetails;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProvider {
    private final String JWT_SECRET = "hung";
    private final long JWT_EXP = 1800000000;

    public String generateJwtToken(Authentication authentication){
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date exDate = new Date(now.getTime() + JWT_EXP);
        System.out.println(exDate);
        return Jwts.builder()
                .setSubject(myUserDetails.getUser().getName())
                .setIssuedAt(now)
                .setExpiration(exDate)
                .signWith(SignatureAlgorithm.ES512, JWT_SECRET)
                .compact();
    }

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String authToken){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        }catch (MalformedJwtException ex){
            System.out.println("JWT k đúng định dạng");
        }catch (ExpiredJwtException ex ){
            System.out.println("JWT het han");
        }catch (UnsupportedJwtException ex){
            System.out.println("JWT k dc hỗ trợ");
        }catch (IllegalArgumentException ex){
            System.out.println("đổi số bất hợp phaáp");
        }
        return false;
    }
}
