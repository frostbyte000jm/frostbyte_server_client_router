package classes;

import staticLoc.ServerNames;

import java.io.*;
import java.net.Socket;

public class ServerRouterThread extends Thread {
    //declarations
    private Object [][] routingTable = new Object[10][2]; //routing Table
    private int index;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;
    private String outIpAddress;
    private Socket outSocket;
    private Boolean doRunning;


    //constructor
    public ServerRouterThread(Socket inSocket, int idx) throws IOException {
        index = idx;
        routingTable[index][0] = inSocket.getInetAddress().getHostAddress();
        routingTable[index][1] = inSocket;
        dataInputStream = new DataInputStream(inSocket.getInputStream());
        dataOutputStream = new DataOutputStream(inSocket.getOutputStream());
        doRunning = true;
    }

    public void run(){
        //identify
        try {
            outIpAddress = dataInputStream.readUTF();
            System.out.println("Connection From:"+(String) outIpAddress);
            dataOutputStream.writeUTF("Connected to Router.");
        } catch(IOException ie){
            System.out.println("Thread interrupted");
        }

        //Pause while this updates with machine info
        try{
            Thread.currentThread().sleep(5000);
        }
        catch(InterruptedException ie){
            System.out.println("Thread interrupted");
        }

        //loop through table to find connection
        for (int i = 0; i < routingTable.length; i++){
            if (outIpAddress.equals((String) routingTable[i][0])){
                outSocket = (Socket) routingTable[i][1];
                System.out.println("Found destination ["+outIpAddress+"] in Routing Table");
                try {
                    dataOutputStream = new DataOutputStream(outSocket.getOutputStream());
                } catch (IOException e) {
                    System.out.println("Unable to load desitnation ["+outIpAddress+"] to output.");
                }
                break;
            }
        }

        // Communication Loop
        try {
            System.out.println("letting Cilent Server know it is ready to transfer.");
            dataOutputStream.writeUTF("Ready to Receive File.");
            System.out.println("Server/Router is receiving file information.");
            int fileNameLength = dataInputStream.readInt();
            byte [] fileNameBytes = new byte[fileNameLength];
            dataInputStream.readFully(fileNameBytes,0,fileNameBytes.length);
            String fileName = new String(fileNameBytes);
            System.out.println("Server/Router received the File Name: "+fileName);
            System.out.println("Server/Router is receiving File Data.");
            int fileLength = dataInputStream.readInt();
            byte [] fileBytes = new byte[fileLength];
            dataInputStream.readFully(fileBytes,0,fileBytes.length);
            System.out.println("Server/Router Received File data for "+fileName);

            dataOutputStream.writeUTF("File Received.");

            System.out.println("Sending the bytes of File Name.");
            dataOutputStream.writeInt(fileNameBytes.length); // send length of file name to Router
            dataOutputStream.write(fileNameBytes); // send whole file name to server

            System.out.println("Sending the bytes of File.");
            dataOutputStream.writeInt(fileBytes.length); // send length of file to Router
            dataOutputStream.write(fileBytes); //send whole file to Router

            String confirmReceived = dataInputStream.readUTF();
            System.out.println("Client Says: "+ confirmReceived);

        } catch (IOException e) {
            System.out.println("Unable to read in file.");
        }

    }
}
