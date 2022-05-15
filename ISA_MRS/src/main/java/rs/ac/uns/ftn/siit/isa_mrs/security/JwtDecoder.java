package rs.ac.uns.ftn.siit.isa_mrs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtConfig jwtConfig;

    public DecodedToken decodeToken(String authorizationHeader) {
        String tokenPrefix = jwtConfig.getTokenPrefix();
        String token = authorizationHeader.substring(tokenPrefix.length());
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        return new DecodedToken(email, roles);
    }

    @Data
    @AllArgsConstructor
    public class DecodedToken {
        private String email;
        private String[] roles;
    }
}
