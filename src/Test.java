import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        String userDir = System.getProperty("user.home");
        System.out.println("userDir:"+userDir);

        File file = new File(userDir+"/Documents/Temp2/test.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);

        }

    }
}
