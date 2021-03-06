/**
 * Created by mov on 2017/01/25.
 */
import java.io.*;

public class ExternalCommand {
    public static boolean draw (String name, String drawtype, File file) {
        ProcessBuilder dot = new ProcessBuilder(drawtype, file.getAbsolutePath(), "-Tpng", "-o", name + ".png");
        try {
            Process p1 = dot.start();
            p1.waitFor();
            InputStream is = p1.getInputStream();
            InputStream es = p1.getErrorStream();
            printInputStream(is);
            printInputStream(es);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }


    public static void printInputStream (InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            for (;;) {
                String line = br.readLine();
                if (line == null) break;
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            br = null;
            e.printStackTrace();
        }
    }
}
