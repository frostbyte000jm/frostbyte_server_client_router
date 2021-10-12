package staticLoc;
/*
*   To get the information below run a Termanail (which ever you choose)
*   run ipconfig /all
*   Collect your *Host Name* and your *IPv4 address*.
*   Enter them in the valid fields below.
*
* */

public class ServerNames {
    private static String routerName = "DESKTOP-DEOBPOO";
    private static String serverAddress = "192.168.1.120";
    private static String clientAddress = "192.168.1.120";
    private static String tempDir = "C:\\Temp";
    private static String textFile = "C:\\Temp\\file.txt";
    private static String textFileServer = "C:\\Temp\\file_server.txt";
    private static String textFileClient = "C:\\Temp\\file_client.txt";
    private static String vidFile = "C:\\Temp\\video3.avi";   //drop.avi";
    private static String vidFileServer = "C:\\Temp\\video_server3.avi";    //drop_server.avi";
    private static int portNum = 5555;


    public static String getRouterName() {
        return routerName;
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    public static String getClientAddress() {
        return clientAddress;
    }

    public static String getTempDir() {
        return tempDir;
    }

    public static String getTextFile() {
        return textFile;
    }

    public static String getTextFileServer() {
        return textFileServer;
    }
    public static String getTextFileClient() {
        return textFileClient;
    }

    public static String getVidFile() { return vidFile; }

    public static String getVidFileServer() { return vidFileServer; }

    public static int getPortNum() {
        return portNum;
    }
}
