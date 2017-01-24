/**
 * Created by mov on 2017/01/25.
 */

import java.io.*;
import java.util.Random;

public class GenerateGraph {

    public static File generateGraphSource(String filename){
        // TODO: get values from arguments
        String graphtype = "digraph";
        String graphshape = "sfdp";
        String arrowsize = "0.5";
        String splines = "true";
        String nodesshape = "circle";
        String name = filename;
        File file = new File(name + ".dot");

        Random rdm = new Random();
        // TODO: get values from arguments
        int n_nodes = rdm.nextInt(10) + 5;
        int n_edges = rdm.nextInt(n_nodes * (n_nodes - 1));

        n_nodes = 10;
        n_edges = 20;
        try {
            if (file.exists()) {
                file.delete();
            }
            if (!file.createNewFile()) {
                System.out.println("Unabled to create a new file.");
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileWriter filewriter = null;
        try {
            filewriter = new FileWriter(file);
            filewriter.write(graphtype + " {" + System.getProperty("line.separator"));
            filewriter.write("\t" + "graph [shape = " + graphshape + ", splines = " + splines + "];"+ System.getProperty("line.separator"));
            filewriter.write("\t" + "edge [arrowsize = " + arrowsize + "];"+ System.getProperty("line.separator"));
            filewriter.write("\t" + "node [shape = " + nodesshape + ", fixedsize = true,  width = 0.3" + "];"+ System.getProperty("line.separator"));

            // 孤立点を描き漏らさない為にノードを準備
            for (int i = 1; i < n_nodes; i++) {
                String idx = String.valueOf(i);
                filewriter.write(idx + " [label = " + idx + "]");
            }
            for (int i = 0; i < n_edges; i++) {
                String v1 = String.valueOf(rdm.nextInt(n_nodes) + 1);
                String v2 = String.valueOf(rdm.nextInt(n_nodes) + 1);
                int len = rdm.nextInt(15) + 1;
                filewriter.write("\t" + v1 + " -> " + v2
                        + " [label = " + len + ", weight = " + 1/len + "]"
                        + ";"
                        + System.getProperty("line.separator"));
            }
            filewriter.write("}" + System.getProperty("line.separator"));
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
