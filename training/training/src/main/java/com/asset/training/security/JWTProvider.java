package com.asset.training.security;

import com.asset.training.constants.Defines;
import com.asset.training.constants.ErrorCodes;
import com.asset.training.exceptions.TrainingException;
import com.asset.training.models.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

public class JWTProvider {
    private final String securityKey = "Awesome5K";
    private double tokenValidity = 1;
    public String generateToken(UserModel user){
        return doGenerateToken(user);
    }

    private String doGenerateToken(UserModel user){
        try {
            Claims claims = addClaims(user);
            long accessTokenValidityMilli = (long) (tokenValidity * 60 * 60 * 1000);
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidityMilli))
                    .signWith(SignatureAlgorithm.HS256, securityKey.trim())
                    .compact();
            return token;
        } catch (Exception ex) {
            throw new TrainingException(ErrorCodes.ERROR.CANNOT_GENERATE_TOKEN, 1);
        }
    }
    private Claims addClaims(UserModel user){
        UUID uuid = UUID.randomUUID();

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put(Defines.PREFIX, Defines.PREFIX);
        claims.put(Defines.SESSION_ID, uuid.toString());

        claims.put(Defines.USERNAME, user.getUsername());
        claims.put(Defines.USER_ID, user.getUserId());
        claims.put(Defines.PROFILE_ID, user.getProfileId());

        return claims;
    }

    public UserModel getUserModelFromToken(String token) {
        String userEncryptedData = token.substring(7);
        Claims claims = getAllClaimsFromToken.apply(userEncryptedData);
        return new UserModel((Integer) claims.get("userId"), claims.get("username").toString());
    }

    private final Function<String, Claims> getAllClaimsFromToken = (token) -> Jwts.parser()
            .setSigningKey(securityKey)
            .parseClaimsJws(token)
            .getBody();

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken.apply(token);
        return claimsResolver.apply(claims);
    }

    private Predicate<String> tokenExpiration = (token) -> {
        Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());
    };

    public Predicate<String> validateToken = (token) -> {
        try {
            token = token.substring(7);
            if (tokenExpiration.test(token)) {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    };
}
