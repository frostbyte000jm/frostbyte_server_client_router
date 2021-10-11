import classes.ServerRouterThread;
import staticLoc.ServerNames;

import java.io.*;
import java.net.*;


public class TCPServerRouter {
    public static void main(String[] args) throws IOException {
        // Who am I
        InetAddress addr = InetAddress.getLocalHost();
        String localIPAddress = addr.getHostAddress(); // Machine's IP Address
        String localHostName = addr.getCanonicalHostName(); // Machine's Host Name
        System.out.println("Local Host Information\nHost Name: "+localHostName+"\nIP Address: "+localIPAddress);

        //declarations
        int portNum = ServerNames.getPortNum();
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        int index = 0;
        Boolean doRunning = true;


        // Accept Connections
        try {
            serverSocket = new ServerSocket(portNum);
            System.out.println("Router: "+localHostName+" is listening on port: "+portNum);
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+portNum+".");
            System.exit(1);
        }

        //Run
        while (doRunning){
            try{
                clientSocket = serverSocket.accept();
                ServerRouterThread serverRouterThread = new ServerRouterThread(clientSocket,index);
                serverRouterThread.start();
                index++;
                System.out.println("Router sent connection to Thread: "+clientSocket.getInetAddress().getHostAddress());
            } catch (IOException e){
                System.err.println("Client/Server failed to connect.");
                System.exit(1);
            }

        }

        //close connections
        clientSocket.close();
        serverSocket.close();
    }
}
