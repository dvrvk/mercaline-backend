package com.mercaline.security.jwt;

import com.mercaline.error.exceptions.InvalidTokenException;
import com.mercaline.users.Model.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;

import java.util.Date;

@Component
@Log
public class JwtTokenProvider {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";

    @Value("${jwt.secret}")
    private String jwtSecreto;

    @Value("${jwt.token-expiration}")
    private int jwtDurationSeg;

    public String generarToken(Authentication auth) {
        UserEntity user = (UserEntity) auth.getPrincipal();

        Date tokenExpirationDate = new Date(System.currentTimeMillis() + (jwtDurationSeg * 1000));

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecreto.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", TOKEN_TYPE)
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(tokenExpirationDate)
                .claim("username", user.getUsername())
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecreto.getBytes()))
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());

    }

    public boolean validateToken(String authToken) {

        try {
            Jwts.parser().setSigningKey(jwtSecreto.getBytes()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.info("Error en la firma del token JWT: " + ex.getMessage());
            throw new InvalidTokenException("Error en la firma del token de sesión, vuelve a iniciar sesión.");
        } catch (MalformedJwtException ex) {
            log.info("Token malformado: " + ex.getMessage());
            throw new InvalidTokenException("Token malformado, vuelve a iniciar sesión.");
        } catch (ExpiredJwtException ex) {
            log.info("El token ha expirado: " + ex.getMessage());
            throw new InvalidTokenException("El token a expirado, vuelve a iniciar sesión.");
        } catch (UnsupportedJwtException ex) {
            log.info("Token JWT no soportado: " + ex.getMessage());
            throw new InvalidTokenException("Token de sesión no soportado, vuelve a iniciar sesión.");
        } catch (IllegalArgumentException ex) {
            log.info("JWT claims vacío");
            throw new InvalidTokenException("JWT claims vacío, vuelve a iniciar sesión.");
        }
    }




}
