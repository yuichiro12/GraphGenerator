/**
 * Created by mov on 2017/01/25.
 */

import java.io.*;
import java.util.Random;

public class GenerateGraph {
	// TODO: get values from arguments
	private static String graphtype = "graph";
	private static String graphshape = "dot";
	private static String arrowsize = "0.5";
	private static String arrowhead = "vee";
	private static String splines = "true";
	private static String nodesshape = "circle";
	private static String name = "graph";
	private static Random rdm = new Random();
	private static int n_nodes = rdm.nextInt(10) + 5;
	private static int n_edges = rdm.nextInt(n_nodes * (n_nodes - 1));

    public static File generateGraphSource(String filename){
        // TODO: get values from arguments
        File file = new File(name + ".dot");
        Random rdm = new Random();
        name = filename;
        n_nodes = 5;

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
            filewriter.write("\t" + "edge [arrowsize = " + arrowsize + ", arrowhead = " + arrowhead + "];"+ System.getProperty("line.separator"));
            filewriter.write("\t" + "node [shape = " + nodesshape + ", fixedsize = true,  width = 0.3" + "];"+ System.getProperty("line.separator"));
			// draw graph according to input property
			filewriter.write(completeGraph());
            filewriter.write("}" + System.getProperty("line.separator"));
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

	public static String setNodes() {
		String query = "";

		for (int i = 1; i < n_nodes; i++) {
			String idx = String.valueOf(i);
			query = query + idx + " [label = " + idx + "];"
				+ System.getProperty("line.separator");
		}

		return query;
	}

	public static String defaultGraph() {
		// draw nodes
		String query = setNodes();
		// draw edges
		for (int i = 0; i < n_edges; i++) {
			String v1 = String.valueOf(rdm.nextInt(n_nodes) + 1);
			String v2 = String.valueOf(rdm.nextInt(n_nodes) + 1);
			int len = rdm.nextInt(15) + 1;
			query = query + "\t" + v1 + " -> " + v2
				+ " [label = " + len + ", weight = " + 1/len + "]"
				+ ";"
				+ System.getProperty("line.separator");
		}

		return query;
	}

	public static String nonMultipleEdgeGraph() {
		// draw nodes
		String query = setNodes();
		// draw edges
		for (int i = 0; i < n_nodes; i++) {
			for (int j = 0; j < n_nodes; j++) {
				if (i == j) {
					continue;
				}
				int len = rdm.nextInt(15) + 1;
				query = query + "\t" + i + " -> " + j + " ["
						+ "label = " + len + ", "
						+ "weight = " + 1 / len + "]"
						+ ";"
						+ System.getProperty("line.separator");
			}
		}

		return query;
	}

	// undirected complete graph
	public static String completeGraph() {
		// draw nodes
		String query = setNodes();
		// draw edges
		for (int i = 0; i < n_nodes; i++) {
			for (int j = 0; j < i; j++) {
				int len = rdm.nextInt(15) + 1;
				query = query + "\t" + i + " -- " + j + " ["
						+ "label = " + len + ", "
						+ "weight = " + 1 / len + "]"
						+ ";"
						+ System.getProperty("line.separator");
			}
		}

		return query;
	}

	public static String connectedGraph() {
		return "";
	}
}
