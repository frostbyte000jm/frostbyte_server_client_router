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
            System.out.println("Setting up Socket ");
            Socket socket = new Socket(routerName, portNum); //socket to connect to serverRouter
            System.out.println("Create Output Stream");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Send local IP Addy");
            dataOutputStream.writeUTF(localIPAddress);
            System.out.println("Reading input Stream");
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
            System.out.println("Wait for Router to request file.");
            String routerRequest = dataInputStream.readUTF();
            System.out.println("Router said: "+ routerRequest);

            System.out.println("Create File to send and converting it into Bytes");
            File file = new File(ServerNames.getTextFile());
            FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
            byte[] fileBytes = new byte[(int)file.length()]; // create array to fit the file

            System.out.println("Converting File Name into Bytes");
            String fileName = file.getName(); // name of file
            byte[] fileNameBytes = fileName.getBytes(); // file in converted to bytes
            fileInputStream.read(fileBytes); // convert file into bytes

            System.out.println("Sending the bytes of File Name.");
            dataOutputStream.writeInt(fileNameBytes.length); // send length of file name to Router
            dataOutputStream.write(fileNameBytes); // send whole file name to server

            System.out.println("Sending the bytes of File.");
            dataOutputStream.writeInt(fileBytes.length); // send length of file to Router
            dataOutputStream.write(fileBytes); //send whole file to Router

            String confirmReceived = dataInputStream.readUTF();
            System.out.println("Router Says: "+ confirmReceived);

            System.out.println("Client is receiving file information.");
            int fileNameLengthReceived = dataInputStream.readInt();
            byte [] fileNameBytesReceived = new byte[fileNameLengthReceived];
            dataInputStream.readFully(fileNameBytesReceived,0,fileNameBytesReceived.length);
            String fileNameReceived = new String(fileNameBytesReceived);
            System.out.println("Client received the File Name: "+fileNameReceived);
            System.out.println("Client is receiving File Data.");
            int fileLengthReceived = dataInputStream.readInt();
            byte [] fileBytesReceived = new byte[fileLengthReceived];
            dataInputStream.readFully(fileBytesReceived,0,fileBytesReceived.length);
            System.out.println("Client Received File data for "+fileNameReceived);

            System.out.println("Client is Creating File");
            File fileReceived = new File(ServerNames.getTextFileClient());
            FileOutputStream fileOutputStream = new FileOutputStream(fileReceived );
            fileOutputStream.write(fileBytesReceived);
            fileOutputStream.close();
            System.out.println("Client created File"+fileName+ ". Reporting to Server/Router");

            dataOutputStream.writeUTF("Client Received File.");

        } catch (IOException e) {
            System.out.println("Client unable to create and send file to Router.");
        }

        //close connections
        dataInputStream.close();
        dataOutputStream.close();
    }
}
