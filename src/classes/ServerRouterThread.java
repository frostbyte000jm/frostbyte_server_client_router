package classes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerRouterThread extends Thread {
    //declarations
    private Object [][] routingTable = new Object[10][2]; //routing Table
    private int index;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;
    private String outIpAddress;
    private Socket outSocket;


    //constructor
    public ServerRouterThread(Socket inSocket, int idx) throws IOException {
        index = idx;
        routingTable[index][0] = inSocket.getInetAddress().getHostAddress();
        routingTable[index][1] = inSocket;
        dataInputStream = new DataInputStream(inSocket.getInputStream());
        dataOutputStream = new DataOutputStream(inSocket.getOutputStream());
    }

    public void run(){
        //identify

        try {
            outIpAddress = dataInputStream.readUTF();
            System.out.println("Forwarding to :"+(String) outIpAddress);
            dataOutputStream.writeUTF("Connected to Router.");
        } catch(IOException ie){
            System.out.println("Thread interrupted");
        }

        //Pause while this updates with machine info
        try{
            Thread.currentThread().sleep(10000);
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
            }
        }

        // Communication Loop
        int length = 0;
        while (true) {
            try {
                if ((length = dataInputStream.readInt()) > 0)
                    break;
                else
                    break;
            } catch (IOException e) {
                System.out.println("Router unable to read file from Server / Router");
            }
        }
    }
}
