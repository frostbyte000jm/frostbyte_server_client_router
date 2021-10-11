import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import staticLoc.ServerNames;

public class TCPClient {
    public static void main(String[] args) throws IOException{

        // Who am I
        InetAddress addr = InetAddress.getLocalHost();
        String localIPAddress = addr.getHostAddress(); // Machine's IP Address
        String localHostName = addr.getCanonicalHostName(); // Machine's Host Name
        System.out.println("Local Host Information\nHost Name: "+localHostName+"\nIP Address: "+localIPAddress);

        //declarations
        String routerName = ServerNames.getRouterName();
        int portNum = ServerNames.getPortNum();
        String serverAddress = ServerNames.getServerAddress();
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;

        //Connect to serverRouter
        try {
            Socket socket = new Socket(routerName, portNum); //socket to connect to serverRouter
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(localIPAddress);
            String confirmConnect = dataInputStream.readUTF();
            System.out.println("Router Says: "+confirmConnect);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about router: " + routerName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + routerName);
            System.exit(1);
        }

        // Send File
        try{
            File file = new File(ServerNames.getTextFile());
            FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());

            String fileName = file.getName(); // name of file
            byte[] fileNameBytes = fileName.getBytes(); // file in converted to bytes
            byte[] fileBytes = new byte[(int)file.length()]; // create array to fit the file
            fileInputStream.read(fileBytes); // convert file into bytes
            dataOutputStream.writeInt(fileNameBytes.length); // send length of file name to Router
            dataOutputStream.write(fileNameBytes); // send whole file name to server
            dataOutputStream.writeInt(fileBytes.length); // send length of file to Router
            dataOutputStream.write(fileBytes); //send whole file to Router
        } catch (IOException e) {
            System.out.println("Client unable to create and send file to Router.");
        }


    }
}
