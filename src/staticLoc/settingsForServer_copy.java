package staticLoc;

public class settingsForServer_copy {
    //static values
    private static int portNum = 5555;
    private static String tempFolder = "C:\\ServerTemp"; //if this ever moves, update ServerThread to mkdirs

    //gets
    public static int getPortNum() { return portNum; }
    public static String getTempFolder() { return tempFolder; }
}
