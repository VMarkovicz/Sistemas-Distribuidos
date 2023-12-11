package server;

import gson.GoogleJson;
import protocols.reply.LoginReply;
import protocols.reply.LogoutReply;
import protocols.reply.Reply;
import protocols.requisitions.RequisitionOp;
import server.controller.PDIManager;
import server.controller.SegmentManager;
import server.controller.UserManager;
import server.dataTransferObject.CreatePDIDTO;
import server.dataTransferObject.CreateSegmentDTO;
import server.dataTransferObject.CreateUserDTO;
import server.dataTransferObject.Utils.Posicao;
import server.exception.ServerReplyException;
import server.methods.*;
import server.routeController.RouteController;
import server.serverInterfaces.PortInterface;
import server.serverInterfaces.ServerInterface;

import java.net.*;
import java.io.*;

public class Server extends Thread {
    private final Socket clientSocket;
    private RouteController routes = null;
    static ServerInterface serverInterface = new ServerInterface();

    public static void main(String[] args) throws IOException, ServerReplyException {
        UserManager.getInstance();
        PDIManager.getInstance();
        SegmentManager.getInstance();

        UserManager.getInstance().createUser(new CreateUserDTO("admin", "admin@admin", "admin", true));

        createMap();

        PortInterface PortInterface = new PortInterface(null);
        int port = PortInterface.getPort();
        InetAddress ipAddress = InetAddress.getByName("0.0.0.0");

        try(ServerSocket serverSocket = new ServerSocket(port, 0, ipAddress)) {
            System.out.println("Connection Socket Created");

            while (true) {
                try {
                    System.out.println("Waiting for Connection");
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
                    .addRoute(RequisitionOp.BUSCAR_ROTA, new FindRoute())
                    .build();
        }
        start();
    }

    public void run() {
        System.out.println ("New Communication Thread Started");
        String myIP = clientSocket.getInetAddress().toString();
        serverInterface.setConnectedUsers();
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
                    serverInterface.connectedIPs.removeIP(myIP);
                    serverInterface.setConnectedUsers();
                    serverInterface.repaint();
                    break;
                }
                if (reply instanceof LogoutReply) {
                    serverInterface.connectedIPs.removeIP(clientSocket.getInetAddress().toString());
                    serverInterface.setConnectedUsers();
                    serverInterface.repaint();
                    break;
                }
                if (reply instanceof LoginReply) {
                    serverInterface.connectedIPs.addIP(myIP);
                    serverInterface.setConnectedUsers();
                    serverInterface.repaint();
                }
            }
        }
        catch (IOException e) {
            System.err.println("Problem with Communication Server");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            serverInterface.connectedIPs.removeIP(myIP);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        serverInterface.setConnectedUsers();
        serverInterface.repaint();
        assert(clientSocket.isClosed());
    }

    private static void createMap() throws ServerReplyException {
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Portaria", new Posicao(0.0, 0.0), "", true)); //1
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Escada Baixo Portaria-Capela", new Posicao(0.0, 100.0), "", true)); //2
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Capela", new Posicao(0.0, 105.0), "", true)); //3
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Auditorio", new Posicao(-20.0, 105.0), "", true)); //4
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Escada Inicio 1.o andar", new Posicao(20.0, 105.0), "", true)); //5
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Escada Baixo-Meio 1.o andar", new Posicao(20.0, 115.0), "", true)); //6
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Lab. 6", new Posicao(22.0, 105.0), "", true)); //7
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Lab. 7", new Posicao(22.0, 125.0), "", true)); //8
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Lab. 8", new Posicao(22.0, 135.0), "", true)); //9
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Escada Bebedouro", new Posicao(22.0, 140.0), "", true)); //10
        PDIManager.getInstance().createPDI(new CreatePDIDTO("LaCa", new Posicao(20.0, 140.0), "", true)); //11
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Rampa", new Posicao(-20.0, 140.0), "", true)); //12
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Escada Inicio Audiotorio 1.o andar", new Posicao(-20.0, 103.0), "", true)); //13
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Escada Meio Audiotorio 1.o andar", new Posicao(-20.0, 93.0), "", true)); //14
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Escada Cima Audiotorio 1.o andar", new Posicao(-20.0, 105.0), "", true)); //15
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Lab. 4", new Posicao(10.0, 105.0), "", true)); //16
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Lab. 2", new Posicao(15.0, 105.0), "", true)); //17
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Escada Cima 1.o andar", new Posicao(18.0, 105.0), "", true)); //18
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Escada Cima-Meio 1.o andar", new Posicao(18.0, 115.0), "", true)); //19
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Lab. 1", new Posicao(20.0, 105.0), "", true)); //20
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Lab. 3", new Posicao(22.0, 105.0), "", true)); //21
        PDIManager.getInstance().createPDI(new CreatePDIDTO("Lab. 5", new Posicao(22.0, 115.0), "", true)); //22
        PDIManager.getInstance().createPDI(new CreatePDIDTO("DAINF", new Posicao(22.0, 130.0), "", true)); //23

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("1"),
                Long.parseLong("2"),
                Distance.calculateDistance(0.0, 0.0, 0.0, 100.0),
                "",
                true
        )); //1

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("2"),
                Long.parseLong("3"),
                Distance.calculateDistance(0.0, 100.0, 0.0, 105.0),
                "Subindo Escada",
                true
        )); //2

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("3"),
                Long.parseLong("4"),
                Distance.calculateDistance(0.0, 105.0, -20.0, 105.0),
                "",
                true
        )); //3

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("3"),
                Long.parseLong("5"),
                Distance.calculateDistance(0.0, 105.0, 20.0, 105.0),
                "",
                true
        )); //4

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("5"),
                Long.parseLong("6"),
                Distance.calculateDistance(20.0, 105.0, 20.0, 115.0),
                "Subindo Escada",
                true
        )); //5

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("5"),
                Long.parseLong("7"),
                Distance.calculateDistance(20.0, 105.0, 22.0, 105.0),
                "",
                true
        )); //6

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("7"),
                Long.parseLong("8"),
                Distance.calculateDistance(22.0, 105.0, 22.0, 125.0),
                "",
                true
        )); //7

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("8"),
                Long.parseLong("9"),
                Distance.calculateDistance(22.0, 125.0, 22.0, 135.0),
                "",
                true
        )); //8

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("9"),
                Long.parseLong("10"),
                Distance.calculateDistance(22.0, 135.0, 22.0, 140.0),
                "",
                true
        )); //9

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("10"),
                Long.parseLong("11"),
                Distance.calculateDistance(22.0, 140.0, 20.0, 140.0),
                "Subindo Escada",
                true
        )); //10

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("11"),
                Long.parseLong("12"),
                Distance.calculateDistance(20.0, 140.0, -20.0, 140.0),
                "",
                true
        )); //11

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("12"),
                Long.parseLong("4"),
                Distance.calculateDistance(-20.0, 140.0, -20.0, 105.0),
                "",
                true
        )); //12

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("4"),
                Long.parseLong("13"),
                Distance.calculateDistance(-20.0, 105.0, -20.0, 103.0),
                "",
                true
        )); //13

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("13"),
                Long.parseLong("14"),
                Distance.calculateDistance(-20.0, 103.0, -20.0, 93.0),
                "Subindo Escada",
                true
        )); //14

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("14"),
                Long.parseLong("15"),
                Distance.calculateDistance(-20.0, 93.0, -20.0, 105.0),
                "Subindo Escada",
                true
        )); //15

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("15"),
                Long.parseLong("16"),
                Distance.calculateDistance(-20.0, 105.0, 10.0, 105.0),
                "",
                true
        )); //16

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("16"),
                Long.parseLong("17"),
                Distance.calculateDistance(10.0, 105.0, 15.0, 105.0),
                "",
                true
        )); //17

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("17"),
                Long.parseLong("18"),
                Distance.calculateDistance(15.0, 105.0, 18.0, 105.0),
                "",
                true
        )); //18

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("18"),
                Long.parseLong("19"),
                Distance.calculateDistance(18.0, 105.0, 18.0, 95.0),
                "",
                true
        )); //19

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("6"),
                Long.parseLong("19"),
                Distance.calculateDistance(18.0, 115.0, 20.0, 115.0),
                "Subindo Escada",
                true
        )); //20

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("18"),
                Long.parseLong("20"),
                Distance.calculateDistance(18.0, 105.0, 20.0, 105.0),
                "",
                true
        )); //21

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("20"),
                Long.parseLong("21"),
                Distance.calculateDistance(20.0, 105.0, 22.0, 105.0),
                "",
                true
        )); //22

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("21"),
                Long.parseLong("22"),
                Distance.calculateDistance(22.0, 105.0, 22.0, 115.0),
                "",
                true
        )); //23

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("22"),
                Long.parseLong("23"),
                Distance.calculateDistance(22.0, 115.0, 22.0, 130.0),
                "",
                true
        )); //24

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("2"),
                Long.parseLong("1"),
                Distance.calculateDistance(0.0, 0.0, 0.0, 100.0),
                "",
                true
        )); //1

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("3"),
                Long.parseLong("2"),
                Distance.calculateDistance(0.0, 100.0, 0.0, 105.0),
                "Descendo Escada",
                true
        )); //2

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("4"),
                Long.parseLong("3"),
                Distance.calculateDistance(0.0, 105.0, -20.0, 105.0),
                "",
                true
        )); //3

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("5"),
                Long.parseLong("3"),
                Distance.calculateDistance(0.0, 105.0, 20.0, 105.0),
                "",
                true
        )); //4

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("6"),
                Long.parseLong("5"),
                Distance.calculateDistance(20.0, 105.0, 20.0, 115.0),
                "Descendo Escada",
                true
        )); //5

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("7"),
                Long.parseLong("5"),
                Distance.calculateDistance(20.0, 105.0, 22.0, 105.0),
                "",
                true
        )); //6

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("8"),
                Long.parseLong("7"),
                Distance.calculateDistance(22.0, 105.0, 22.0, 125.0),
                "",
                true
        )); //7

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("9"),
                Long.parseLong("8"),
                Distance.calculateDistance(22.0, 125.0, 22.0, 135.0),
                "",
                true
        )); //8

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("10"),
                Long.parseLong("9"),
                Distance.calculateDistance(22.0, 135.0, 22.0, 140.0),
                "",
                true
        )); //9

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("11"),
                Long.parseLong("10"),
                Distance.calculateDistance(22.0, 140.0, 20.0, 140.0),
                "Descendo Escada",
                true
        )); //10

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("12"),
                Long.parseLong("11"),
                Distance.calculateDistance(20.0, 140.0, -20.0, 140.0),
                "",
                true
        )); //11

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("4"),
                Long.parseLong("12"),
                Distance.calculateDistance(-20.0, 140.0, -20.0, 105.0),
                "",
                true
        )); //12

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("13"),
                Long.parseLong("4"),
                Distance.calculateDistance(-20.0, 105.0, -20.0, 103.0),
                "",
                true
        )); //13

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("14"),
                Long.parseLong("13"),
                Distance.calculateDistance(-20.0, 103.0, -20.0, 93.0),
                "Descendo Escada",
                true
        )); //14

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("15"),
                Long.parseLong("14"),
                Distance.calculateDistance(-20.0, 93.0, -20.0, 105.0),
                "Descendo Escada",
                true
        )); //15

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("16"),
                Long.parseLong("15"),
                Distance.calculateDistance(-20.0, 105.0, 10.0, 105.0),
                "",
                true
        )); //16

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("17"),
                Long.parseLong("16"),
                Distance.calculateDistance(10.0, 105.0, 15.0, 105.0),
                "",
                true
        )); //17

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("18"),
                Long.parseLong("17"),
                Distance.calculateDistance(15.0, 105.0, 18.0, 105.0),
                "",
                true
        )); //18

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("19"),
                Long.parseLong("18"),
                Distance.calculateDistance(18.0, 105.0, 18.0, 95.0),
                "",
                true
        )); //19

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("19"),
                Long.parseLong("6"),
                Distance.calculateDistance(18.0, 115.0, 20.0, 115.0),
                "Descendo Escada",
                true
        )); //20

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("20"),
                Long.parseLong("18"),
                Distance.calculateDistance(18.0, 105.0, 20.0, 105.0),
                "",
                true
        )); //21

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("21"),
                Long.parseLong("20"),
                Distance.calculateDistance(20.0, 105.0, 22.0, 105.0),
                "",
                true
        )); //22

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("22"),
                Long.parseLong("21"),
                Distance.calculateDistance(22.0, 105.0, 22.0, 115.0),
                "",
                true
        )); //23

        SegmentManager.getInstance().createSegment(new CreateSegmentDTO(
                Long.parseLong("23"),
                Long.parseLong("22"),
                Distance.calculateDistance(22.0, 115.0, 22.0, 130.0),
                "",
                true
        )); //24
    }
}
