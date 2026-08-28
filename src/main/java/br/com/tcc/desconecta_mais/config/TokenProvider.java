package br.com.tcc.desconecta_mais.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenProvider {

    //Repsonsavel por gerar e validar um token

    @Value("${spring.jwt.expiration}")
    private long expirationTime;

    @Value("${spring.jwt.key}")
    private String key;;

    //Gerar um token
    public String gerarToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return buildToken(user.getUsername());
    }

    private String buildToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username) //infos do user que queremos guardar dentro do token
                .issuedAt(now) //data de lancamento do token
                .expiration(expiration) // prazo
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(key.getBytes());//serve para gerar uma secret key
    }

    //validar token
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        }catch (Exception e) {
            return false;
        }

    }

    private Claims getClaims(String token) {
        //validar assinatura do token
        //validar expiracao
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    //extrair infos do token (subject)
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

}
