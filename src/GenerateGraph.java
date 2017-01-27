/**
 * Created by mov on 2017/01/25.
 */

import java.io.*;
import java.util.*;

public class GenerateGraph {
	private static Random rdm = new Random();
	private static String graphtype = "directed";
	private static String direction = "digraph";
	private static String drawtype = "dot";
	private static String arrowsize = "0.5";
	private static String arrowhead = "vee";
	private static String splines = "true";
	private static String nodesshape = "circle";
	private static String name = "graph";
	private static int n_nodes = rdm.nextInt(10) + 5;
	private static int n_edges = rdm.nextInt(n_nodes * (n_nodes - 1));
	private static boolean is_multiple = false;
	private static boolean is_loop = false;

    public static File generateGraphSource(Map<String, String> properties){
        // TODO: get values from arguments
        File file = new File(name + ".dot");
        Random rdm = new Random();
        name = "graph";
        graphtype = properties.get("graphtype");
		drawtype = properties.get("drawtype");
		n_nodes = Integer.parseInt(properties.get("nodes"));
		n_edges = Integer.parseInt(properties.get("edges"));
		is_multiple = Boolean.valueOf(properties.get("multiple"));
		is_loop = Boolean.valueOf(properties.get("loop"));



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

		String query = "";
		switch (graphtype) {
			case "directed":
				direction = "digraph";
				query = defaultGraph(" -> ", is_multiple, is_loop);
				break;
			case "undirected":
				direction = "graph";
				query = defaultGraph(" -- ", is_multiple, is_loop);
				break;
			case "complete":
				direction = "graph";
				query = completeGraph();
				break;
			case "spanning tree":
				direction = "graph";
				query = spanningTree();
				break;
		}

        FileWriter filewriter = null;
        try {
            filewriter = new FileWriter(file);
            filewriter.write(direction + " {" + System.getProperty("line.separator"));
            filewriter.write("\t" + "graph [shape = " + drawtype + ", splines = " + splines + "];"+ System.getProperty("line.separator"));
            filewriter.write("\t" + "edge [arrowsize = " + arrowsize + ", arrowhead = " + arrowhead + "];"+ System.getProperty("line.separator"));
            filewriter.write("\t" + "node [shape = " + nodesshape + ", fixedsize = true,  width = 0.3" + "];"+ System.getProperty("line.separator"));
			// draw graph according to input property
			filewriter.write(query);
            filewriter.write("}" + System.getProperty("line.separator"));
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    // draw all nodes
	public static String setNodes() {
		String query = "";

		for (int i = 1; i <= n_nodes; i++) {
			String idx = String.valueOf(i);
			query = query + "\t" + idx + " [label = " + idx + "];"
				+ System.getProperty("line.separator");
		}

		return query;
	}

	public static String defaultGraph(String edgeshape, boolean is_multiple, boolean is_loop) {
		// draw nodes
		String query = setNodes();
		List<Integer> nodes = new ArrayList<>();

		// create node list
		for (int i = 1; i <= n_nodes; i++) {
			nodes.add(i);
		}
		// draw edges
		for (int i = 0; i < n_edges; i++) {
			Collections.shuffle(nodes);
			String v1 = String.valueOf(nodes.get(0));

			// if loop is allowed, choose randomly. Else, v1 != v2.
			String v2 = String.valueOf(nodes.get(is_loop ? rdm.nextInt(n_nodes) : 1));
			int len = rdm.nextInt(15) + 1;
			query = query + "\t" + v1 + edgeshape + v2
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
				query = query + "\t" + i + " -- " + j + " ["
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
		for (int i = 1; i <= n_nodes; i++) {
			for (int j = 1; j < i; j++) {
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

	// TODO
	public static String spanningTree() {
		// draw nodes
		String query = setNodes();

		// create node list
		List<Integer> nodes = new ArrayList<>();
		List<Integer> covered_nodes = new ArrayList<>();

		for (int i = 1; i <= n_nodes; i++) {
			nodes.add(i);
		}

		// draw edges
		covered_nodes.add(nodes.remove(0));
		for (int i = 0; i < n_nodes - 1; i++) {
			Collections.shuffle(nodes);
			Collections.shuffle(covered_nodes);
			System.out.println(nodes);
			System.out.println(covered_nodes);
			int len = rdm.nextInt(15) + 1;
			int v1 = covered_nodes.get(0);
			int v2 = nodes.remove(0);
			covered_nodes.add(v2);
			query = query + "\t" + v1 + " -- " + v2 + " ["
					+ "label = " + len + ", "
					+ "weight = " + 1 / len + "]"
					+ ";"
					+ System.getProperty("line.separator");
		}

		return query;
	}

	// TODO connected
	public static String connectedGraph() {
    	String query = setNodes();
		return query;
	}

	// TODO Dijkstra algorithm
}
