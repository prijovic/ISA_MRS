package rs.ac.uns.ftn.siit.isa_mrs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtGenerator {
    private final JwtConfig jwtConfig;

    public enum TokenPeriod {
        OneDay,
        TwoDays
    }

    public String generateJwt(String email, String issuer, List<String> authorityList, TokenPeriod period) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes());
        java.sql.Date expiration;
        if (period.equals(TokenPeriod.OneDay)) {
            expiration = java.sql.Date.valueOf(LocalDate.now().plusDays(1));
        }
        else {
            expiration = java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays()));
        }
        return JWT.create()
                .withExpiresAt(expiration)
                .withSubject(email)
                .withIssuer(issuer)
                .withClaim("roles", authorityList)
                .sign(algorithm);
    }
}
