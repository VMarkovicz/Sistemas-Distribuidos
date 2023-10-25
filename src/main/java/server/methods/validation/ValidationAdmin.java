package server.methods.validation;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jwt.JWTManager;
import protocols.reply.Reply;
import protocols.requisitions.Requisition;
import server.exception.ForbiddenAccessException;
import server.exception.ServerReplyException;

public class ValidationAdmin<T> {

    public static void validateAdmin(String token) throws ServerReplyException {
        try {
            DecodedJWT jwt = JWTManager.decodeToken(token);
            Claim isAdmin = jwt.getClaim("isAdmin");
            Claim userId = jwt.getClaim("userId");
            if (!isAdmin.asBoolean()) {
                if (!userId.isMissing()) {
                    throw new ForbiddenAccessException(userId.asString());
                }
                throw new ForbiddenAccessException();
            }
        } catch (JWTVerificationException e) {
            throw new ForbiddenAccessException();
        }
    }
}
