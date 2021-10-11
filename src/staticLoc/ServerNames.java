package staticLoc;
/*
*   To get the information below run a Termanail (which ever you choose)
*   run ipconfig /all
*   Collect your *Host Name* and your *IPv4 address*.
*   Enter them in the valid fields below.
*
* */

public class ServerNames {
    private static String routerName = "FrostbytePro";
    private static String serverAddress = "192.168.0.219";
    private static String clientAddress = "192.168.0.219";
    private static String tempDir = "C:\\Temp";
    private static String textFile = "C:\\Temp\\file.txt";
    private static String vidFile = "C:\\Temp\\drop.avi";
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

    public static String getVidFile() {
        return vidFile;
    }

    public static int getPortNum() {
        return portNum;
    }
}
