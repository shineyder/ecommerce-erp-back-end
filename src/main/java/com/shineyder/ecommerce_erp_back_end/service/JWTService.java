package com.shineyder.ecommerce_erp_back_end.service;

import com.shineyder.ecommerce_erp_back_end.model.JwtBlacklist;
import com.shineyder.ecommerce_erp_back_end.model.Users;
import com.shineyder.ecommerce_erp_back_end.repository.JwtBlacklistRepository;
import com.shineyder.ecommerce_erp_back_end.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
@SuppressWarnings("SpellCheckingInspection")
public class JWTService {
    private final String secretKey;
    private final UsersRepository repository;
    private final JwtBlacklistRepository jwtBlacklistRepository;

    public JWTService(UsersRepository repository, JwtBlacklistRepository jwtBlacklistRepository) throws RuntimeException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
            this.repository = repository;
            this.jwtBlacklistRepository = jwtBlacklistRepository;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*1000))
                .and()
                .signWith(getKey())
                .compact();
    }

    private @NotNull SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, @NotNull Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public boolean validateToken(String token, @NotNull UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Map<String, String> refreshToken(String token) {
        try {
            JwtBlacklist blacklist = jwtBlacklistRepository.findByTokenEquals(token);
            if(blacklist != null){
                return Collections.singletonMap("error", "Token não é mais válido");
            }

            final String email = extractUserName(token);
            Users user = repository.findByEmail(email);

            if(user != null){
                return Collections.singletonMap("error", "Token ainda válido");
            }else{
                return Collections.singletonMap("error", "Credenciais do token inválidas");
            }
        } catch (SignatureException e) {
            return Collections.singletonMap("error", "Token inválido");
        } catch (ExpiredJwtException expiredJwtException){
            String email = expiredJwtException.getClaims().getSubject();
            Users user = repository.findByEmail(email);
            return Collections.singletonMap("token", generateToken(user.getEmail()));
        }
    }

    public JwtBlacklist logout(String token){
        JwtBlacklist jwtBlacklist = new JwtBlacklist(null, token);

        return jwtBlacklistRepository.save(jwtBlacklist);
    }
}
