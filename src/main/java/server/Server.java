package server;

import gson.GoogleJson;
import protocols.reply.LoginReply;
import protocols.reply.LogoutReply;
import protocols.reply.Reply;
import protocols.requisitions.RequisitionOp;
import server.controller.PDIManager;
import server.controller.SegmentManager;
import server.controller.UserManager;
import server.dataTransferObject.CreateUserDTO;
import server.exception.ServerReplyException;
import server.methods.*;
import server.routeController.RouteController;
import server.serverInterfaces.ConnectecIPs;
import server.serverInterfaces.PortInterface;
import server.serverInterfaces.ServerInterface;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Server extends Thread {
    private final Socket clientSocket;
    private RouteController routes = null;
    static ServerInterface serverInterface = new ServerInterface();

    public static void main(String[] args) throws IOException, ServerReplyException {
        UserManager.getInstance();
        PDIManager.getInstance();
        SegmentManager.getInstance();

        UserManager.getInstance().createUser(new CreateUserDTO("admin", "admin@admin", "admin", true));

        PortInterface PortInterface = new PortInterface(null);
        int port = PortInterface.getPort();
        InetAddress ipAddress = InetAddress.getByName("0.0.0.0");

        try(ServerSocket serverSocket = new ServerSocket(port, 0, ipAddress)) {
            System.out.println("Connection Socket Created");

            while (true) {
                try {
                    System.out.println("Waiting for Connection");
                    //ServerInterface serverInterface = new ServerInterface(null);
                    serverInterface.setVisible(true);
                    new Server(serverSocket.accept());

                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.err.printf("Could not listen on port: %d.%n", port);
            System.exit(1);
        }
    }

    private Server (Socket clientSoc) {
        clientSocket = clientSoc;
        if (routes == null) {
            routes = RouteController.builder()
                    .addRoute(RequisitionOp.LOGIN, new Login())
                    .addRoute(RequisitionOp.LOGOUT, new Logout())
                    .addRoute(RequisitionOp.ADMIN_CADASTRAR_USUARIO, new AdminCreateUser())
                    .addRoute(RequisitionOp.CADASTRAR_USUARIO, new CreateUser())
                    .addRoute(RequisitionOp.ADMIN_DELETAR_USUARIO, new AdminDeleteUser())
                    .addRoute(RequisitionOp.DELETAR_USUARIO, new DeleteUser())
                    .addRoute(RequisitionOp.BUSCAR_USUARIO, new FindUser())
                    .addRoute(RequisitionOp.ADMIN_BUSCAR_USUARIOS, new AdminFindUsers())
                    .addRoute(RequisitionOp.ADMIN_BUSCAR_USUARIO, new AdminFindUser())
                    .addRoute(RequisitionOp.ATUALIZAR_USUARIO, new UpdateUser())
                    .addRoute(RequisitionOp.ADMIN_ATUALIZAR_USUARIO, new AdminUpdateUser())
                    .addRoute(RequisitionOp.CADASTRAR_PDI, new AdminCreatePDI())
                    .addRoute(RequisitionOp.BUSCAR_PDIS, new AdminFindPDIs())
                    .addRoute(RequisitionOp.ATUALIZAR_PDI, new AdminUpdatePDI())
                    .addRoute(RequisitionOp.DELETAR_PDI, new AdminDeletePDI())
                    .addRoute(RequisitionOp.CADASTRAR_SEGMENTO, new AdminCreateSegment())
                    .addRoute(RequisitionOp.BUSCAR_SEGMENTOS, new AdminFindSegments())
                    .addRoute(RequisitionOp.ATUALIZAR_SEGMENTO, new AdminUpdateSegment())
                    .addRoute(RequisitionOp.DELETAR_SEGMENTO, new AdminDeleteSegment())
                    /*.addRoute(RequisitionOp.BUSCAR_ROTA, new AdminFindRoute())*/
                    .build();
        }
        start();
    }

    public void run() {
        System.out.println ("New Communication Thread Started");
        serverInterface.setConnectedUsers();
        System.out.println(serverInterface.connectedIPs.getConnectedIPs());
        serverInterface.repaint();
        try(clientSocket;

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
            ){
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from "+ clientSocket.getInetAddress() +": " + inputLine);
                Reply<?> reply;
                try {
                    reply = routes.serve(inputLine);
                } catch (ServerReplyException exception) {
                    reply = exception.intoResponse();
                }

                String jsonResponse = GoogleJson.codify(reply);
                System.out.println("Sent to "+ clientSocket.getInetAddress() +": " + jsonResponse);
                out.println(jsonResponse);
                if(!clientSocket.isConnected() || clientSocket.isClosed()) {
                    serverInterface.connectedIPs.removeIP(clientSocket.getInetAddress().toString());
                    serverInterface.setConnectedUsers();
                    serverInterface.repaint();
                    break;
                }
                if (reply instanceof LogoutReply) {
                    serverInterface.connectedIPs.removeIP(clientSocket.getInetAddress().toString());
                    serverInterface.connectedIPs.addIP(clientSocket.getInetAddress().toString());
                    serverInterface.setConnectedUsers();
                    serverInterface.repaint();
                    break;
                }
                if (reply instanceof LoginReply) {
                    serverInterface.connectedIPs.addIP(clientSocket.getInetAddress().toString());
                    serverInterface.setConnectedUsers();
                    serverInterface.repaint();
                }
                System.out.println(serverInterface.connectedIPs.getConnectedIPs());
            }
        }
        catch (IOException e) {
            System.err.println("Problem with Communication Server");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assert(clientSocket.isClosed());
    }
}
