package jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTManager {

    public static String codifyJWT(boolean isAdmin, Long userId){
        Algorithm algorithm = Algorithm.HMAC256("bomdia");

        return JWT.create().withIssuer("auth0").withClaim("isAdmin", isAdmin).withClaim("userId", userId).sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("bomdia")).build();
        return verifier.verify(token);
    }

    public static DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

    public static Long getRegistro(String token) throws JWTVerificationException {
        DecodedJWT jwt = JWT.decode(token);
        Claim userId = jwt.getClaim("userId");
        return userId.asLong();
    }

    public static boolean getTipo(String token) {
        DecodedJWT jwt = JWT.decode(token);
        Claim isAdmin = jwt.getClaim("isAdmin");
        return isAdmin.asBoolean();
    }
}
