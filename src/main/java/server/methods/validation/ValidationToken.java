package server.methods.validation;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jwt.JWTManager;
import protocols.requisitions.Requisition;
import server.controller.UserManager;
import server.exception.NotFoundException;
import server.exception.ServerReplyException;
import server.exception.UnauthorizedAccessException;

public class ValidationToken {

    public static void validateToken(String token) throws ServerReplyException {
        if (token == null) {
            throw new UnauthorizedAccessException();
        }
        DecodedJWT jwt;
        try {
            jwt = JWTManager.verifyToken(token);
        } catch (JWTVerificationException e) {
            throw new UnauthorizedAccessException();
        }

        Claim userId = jwt.getClaim("userId");
        Claim isAdmin = jwt.getClaim("isAdmin");
        if (userId.isMissing() || userId.isNull() || isAdmin.isMissing() || isAdmin.isNull()) {
            throw new UnauthorizedAccessException();
        }
        try {
            UserManager.getInstance().findUser(JWTManager.getRegistro(token));
        }
        catch (NotFoundException e){
            throw new NotFoundException("User does not exists");
        }
    }

}
