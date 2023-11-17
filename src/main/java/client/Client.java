package client;

import client.clientInterfaces.OperationSelect;
import client.clientInterfaces.SocketConnection;
import client.clientInterfaces.operationInterfaces.*;
import com.google.gson.JsonSyntaxException;
import gson.Error;
import gson.GoogleJson;
import gson.ValidationGson;
import jwt.JWTManager;
import protocols.Optional;
import protocols.reply.*;
import protocols.requisitions.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
//        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//        System.out.print("IP: ");
//        String IPServer = stdIn.readLine();
//        System.out.print("Port: ");
//        int port = Integer.parseInt(stdIn.readLine());
        SocketConnection SocketConnInterface = new SocketConnection(null);
        String IPServer = SocketConnInterface.getIpServer();
        int port = SocketConnInterface.getPort();

        try (Socket echoSocket = new Socket(IPServer, port);
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
             BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))
        ) {
            replyMaker(out, in, stdin);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + IPServer);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + IPServer);
            System.exit(1);
        }
    }
    private static void replyMaker(PrintWriter out, BufferedReader in, BufferedReader stdin) {
        String token = null;
        try {
            while (true) {
                Requisition<?> requisition = requisitionFactory(stdin, token);
                String jsonRequisition = GoogleJson.codify(requisition);
                out.println(jsonRequisition);
                System.out.println();
                System.out.println("Send object created: " + requisition);
                System.out.println("Sent: " + jsonRequisition);
                System.out.println();

                String jsonReply = in.readLine();
                if (jsonReply == null) {
                    System.err.println("Error receiving data");
                    break;
                }
                System.out.println("Received: " + jsonReply);
                Reply<?> reply = handleReply(jsonReply, requisition);
                System.out.println("Object created: " + reply);

                if (reply == null) {
                    continue;
                }

                if (reply instanceof LoginReply) {
                    token = ((LoginReply) reply).payload().token();
                    System.out.println("Token was set");
                }

                if (reply instanceof LogoutReply) {
                    break;
                }

                System.out.println();

            }
        } catch (IOException e) {
            System.out.println("error reading stdin");
        }
    }

    private static Requisition<?> requisitionFactory(BufferedReader stdin, String token) throws IOException {

        String method;
        while (true) {
            System.out.print("Enter Operation: ");
            OperationSelect operationSelectInterface = new OperationSelect(null);
//            if(token != null){
//                operationSelectInterface.Information.setText(JWTManager.getRegistro(token).toString());
//            }
            method = operationSelectInterface.getOperation();
            if (method == null) {
                throw new IOException();
            }

            switch (method) {
                case RequisitionOp.LOGIN -> {
                    LoginInterface LoginInterface = new LoginInterface(null);
                    return new LoginReq(LoginInterface.getEmail(), LoginInterface.getPassword());
                }
                case RequisitionOp.LOGOUT -> {
                    return new LogoutReq(token);
                }
                case RequisitionOp.ADMIN_CADASTRAR_USUARIO -> {
                    AdminCreateUserInterface AdminCreateUserInterface = new AdminCreateUserInterface(null);
                    return new AdminCreateUserReq(token, AdminCreateUserInterface.getName(), AdminCreateUserInterface.getEmail(), AdminCreateUserInterface.getPassword());
                }
                case RequisitionOp.CADASTRAR_USUARIO -> {
                    CreateUserInterface CreateUserInterface = new CreateUserInterface(null);
                    return new CreateUserReq(token, CreateUserInterface.getName(), CreateUserInterface.getEmail(), CreateUserInterface.getPassword());
                }
                case RequisitionOp.ADMIN_DELETAR_USUARIO -> {
                    AdminDeleteUserInterface AdminDeleteUserInterface = new AdminDeleteUserInterface(null, token);
                    return new AdminDeleteUserReq(token, AdminDeleteUserInterface.getIdToDelete());
                }
                case RequisitionOp.DELETAR_USUARIO -> {
                    DeleteUserInterface DeleteUserInterface = new DeleteUserInterface(null);
                    return new DeleteUserReq(token, DeleteUserInterface.getEmail(), DeleteUserInterface.getPassword());
                }
                case RequisitionOp.BUSCAR_USUARIO -> {
                    return new FindUserReq(token);
                }
                case RequisitionOp.ADMIN_BUSCAR_USUARIOS -> {
                    return new AdminFindUsersReq(token);
                }
                case RequisitionOp.ADMIN_BUSCAR_USUARIO -> {
                    AdminFindUserInterface AdminFindUserInterface = new AdminFindUserInterface(null);
                    return new AdminFindUserReq(token, AdminFindUserInterface.getIdToFind());
                }
                case RequisitionOp.ADMIN_ATUALIZAR_USUARIO -> {
                    AdminUpdateUserInterface AdminUpdateUserInterface = new AdminUpdateUserInterface(null, token);
                    return new AdminUpdateUserReq(token,    AdminUpdateUserInterface.getIdToUpdate(),
                                                            AdminUpdateUserInterface.getName(),
                                                            AdminUpdateUserInterface.getEmail(),
                                                            AdminUpdateUserInterface.getPassword(),
                                                            AdminUpdateUserInterface.getIsAdmin());
                }
                case RequisitionOp.ATUALIZAR_USUARIO -> {
                    UpdateUserInterface UpdateUserInterface = new UpdateUserInterface(null);
                    return new UpdateUserReq(token, UpdateUserInterface.getName(), UpdateUserInterface.getEmail(), UpdateUserInterface.getPassword());
                }
            }
        }
    }


    private static <T> T makeReq(BufferedReader stdin, String token, Class<T> objectClass) throws IOException {
        for (Constructor<?> constructor : objectClass.getConstructors()) {
            Parameter[] parameters = constructor.getParameters();
            boolean shouldSkip = false;

            for (Parameter parameter : parameters) {
                if (parameter.getType() == Header.class) {
                    shouldSkip = true;
                    break;
                }
            }
            if (shouldSkip) {
                // it is the default constructor
                continue;
            }

            Object[] constructorArguments = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].getName().toLowerCase().contains("token")) {
                    constructorArguments[i] = token;
                    continue;
                }
                System.out.print(parameters[i].getName());
                if (parameters[i].isAnnotationPresent(Optional.class)) {
                    System.out.print(" (optional)");
                }
                System.out.print(": ");
                String line = stdin.readLine();
                if (line.isBlank() || line.isEmpty()) {
                    constructorArguments[i] = null;
                } else if (parameters[i].getType() == Long.class) {
                    constructorArguments[i] = Long.parseLong(line);
                } else if (parameters[i].getType() == Boolean.class) {

                    constructorArguments[i] = Boolean.parseBoolean(line);
                } else {
                    constructorArguments[i] = line;
                }
            }

            try {
                return (T) constructor.newInstance(constructorArguments);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException("Unable to create a new instance of " + objectClass.getName());
    }

    private static Reply<?> handleReply(String json, Requisition<?> requisition) {
        Reply<?> reply = null;
        try {
            Class<?> objectClass = requisition.getClass();
            if (objectClass == LoginReq.class) {
                reply = GoogleJson.decode(json, LoginReply.class);
            }
            if (objectClass == LogoutReq.class) {
                reply = GoogleJson.decode(json, LogoutReply.class);
            }
            if (objectClass == AdminCreateUserReq.class) {
                reply = GoogleJson.decode(json, AdminCreateUserReply.class);
            }
            if (objectClass == CreateUserReq.class) {
                reply = GoogleJson.decode(json, CreateUserReply.class);
            }
            if (objectClass == AdminDeleteUserReq.class) {
                reply = GoogleJson.decode(json, AdminDeleteUserReply.class);
            }
            if (objectClass == DeleteUserReq.class) {
                reply = GoogleJson.decode(json, DeleteUserReply.class);
            }
            if (objectClass == FindUserReq.class) {
                reply = GoogleJson.decode(json, FindUserReply.class);
            }
            if (objectClass == AdminFindUsersReq.class) {
                reply = GoogleJson.decode(json, AdminFindUsersReply.class);
            }
            if (objectClass == AdminFindUserReq.class) {
                reply = GoogleJson.decode(json, FindUserReply.class);
            }
            if (objectClass == AdminUpdateUserReq.class) {
                reply = GoogleJson.decode(json, AdminUpdateUserReply.class);
            }
            if (objectClass == UpdateUserReq.class) {
                reply = GoogleJson.decode(json, UpdateUserReply.class);
            }
            if (reply == null || reply.payload() == null) {
                reply = GoogleJson.decode(json, ErrorReply.class);
            }
            ValidationGson.validate(reply);
            return reply;
        } catch (Error e) {
            System.err.println("Unable to validate response\n" + e.getMessage());
            return reply;
        } catch (JsonSyntaxException e) {
            System.err.println("Json has an error");
        }
        return null;
    }

}



