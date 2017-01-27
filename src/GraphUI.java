/**
 * Created by mov on 2017/01/25.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.BevelBorder;


public class GraphUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    // ComboBox Model
    private DefaultComboBoxModel<String> graphType = null;
    private DefaultComboBoxModel<String> drawType = null;

    // ComboBox
    private JComboBox<String> graph_type = null;
    private JComboBox<String> draw_type = null;

    // TextField
    private JTextField node_size = null;
    private JTextField edge_size = null;

    // Label
    private JLabel label = null;
    private JLabel nodelabel = null;
    private JLabel edgelabel = null;

    // CheckBox
    private JCheckBox is_multiple_edge = null;
    private JCheckBox is_allow_loop = null;

    // bound of nodes and edges
    private int nodesBound = 300;
    private int edgesBound = 900;



    // bootstrap
    public static void bootstrap() {
        GraphUI frame = new GraphUI();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("GraphGenerator");
        frame.setBounds(150, 300, 700, 180);
        frame.setVisible(true);
    }



    // Constractor
    public GraphUI() {
        // Make a Panel
        JPanel panelBase = new JPanel();

        // ComboBoxModel
        graphType = new DefaultComboBoxModel<>();
        graphType.addElement("directed");
        graphType.addElement("undirected");
        graphType.addElement("complete");
        graphType.addElement("spanning tree");

        drawType = new DefaultComboBoxModel<>();
        drawType.addElement("dot");
        drawType.addElement("circo");
        drawType.addElement("neato");
        drawType.addElement("sfdp");
        drawType.addElement("osage");
        drawType.addElement("twopi");

        // make TextFields
        node_size = new JTextField("", 3);
        edge_size = new JTextField("", 3);

        // make ComboBoxes
        graph_type = new JComboBox<>(graphType);
        draw_type = new JComboBox<>(drawType);

        // make Labels
        nodelabel = new JLabel("nodes");
        edgelabel = new JLabel("edges");
        label = new JLabel();
        label.setPreferredSize(new Dimension(500, 20));
        label.setBorder(new BevelBorder(BevelBorder.LOWERED));
        label.setBackground(Color.black);
        label.setForeground(Color.green);
        label.setFont(new Font("Courier New", Font.BOLD, 14));
        label.setText("Choose the parameter");
        label.setOpaque(true);

        // make Checkboxes
        is_multiple_edge = new JCheckBox("multiple edges");
        is_allow_loop = new JCheckBox("loop");

        // make a button
        JButton button = new JButton("generate");

        // action listener
        button.addActionListener(this);

        // item listener
        graph_type.addItemListener(this::itemStateChanged);

        // add comboBoxes, labels, and buttons
        panelBase.add(graph_type);
        panelBase.add(draw_type);
        panelBase.add(nodelabel);
        panelBase.add(node_size);
        panelBase.add(edgelabel);
        panelBase.add(edge_size);
        panelBase.add(is_multiple_edge);
        panelBase.add(is_allow_loop);
        panelBase.add(label);
        panelBase.add(button);

        // add panel
        getContentPane().add(panelBase);
    }


    private static boolean isNum(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * event
     *
     * @param e	: event
     */
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String selected = String.valueOf(graph_type.getSelectedItem());
            boolean is_enabled = !("complete".equals(selected) || "spanning tree".equals(selected));
            edgelabel.setEnabled(is_enabled);
            edge_size.setEnabled(is_enabled);
            is_multiple_edge.setEnabled(is_enabled);
            is_allow_loop.setEnabled(is_enabled);

            String nodes = node_size.getText();
            switch (selected) {
                case "complete":
                    if (isNum(nodes)) {
                        int n = Integer.valueOf(nodes);
                        edge_size.setText("");
                    }
                    is_multiple_edge.setSelected(false);
                    is_allow_loop.setSelected(false);
                    break;
                case "spanning tree":
                    if (isNum(nodes)) {
                        int n = Integer.valueOf(nodes);
                        edge_size.setText("");
                    }
                    is_multiple_edge.setSelected(false);
                    is_allow_loop.setSelected(false);
                    break;
                default:
                    break;
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        Map<String, String> properties = new HashMap<>();
        String graphtype = (String) graph_type.getSelectedItem();
        String drawtype = (String) draw_type.getSelectedItem();
        String nodes = node_size.getText();
        String edges = edge_size.getText();

        // convert boolean to string for Map<String, String>
        String multiple = is_multiple_edge.isSelected() ? "true" : "false";
        String loop = is_allow_loop.isSelected() ? "true" : "false";

         if (isNum(nodes)) {
             int n_nodes = Integer.valueOf(nodes);
             switch (graphtype) {
                 case "complete":
                     edge_size.setText(String.valueOf(n_nodes * (n_nodes-1)));
                     edges = edge_size.getText();
                 case "spanning tree":
                     edge_size.setText(String.valueOf(n_nodes -= 1));
                     edges = edge_size.getText();
                 default:
                     if (isNum(edges)) {
                         int n_edges = Integer.valueOf(edges);
                         if ( n_edges <= edgesBound & n_edges >= 0 & n_nodes <= nodesBound & n_nodes > 0) {
                             properties.put("graphtype", graphtype);
                             properties.put("drawtype", drawtype);
                             properties.put("nodes", nodes);
                             properties.put("edges", edges);
                             properties.put("multiple", multiple);
                             properties.put("loop", loop);

                             drawGraph(properties);
                             label.setForeground(Color.green);
                             label.setText("Generated");
                         } else {
                             label.setForeground(Color.red);
                             label.setText("Out of bounds. 1 <= nodes <= " + nodesBound + ", " + " 0 <= edges <= " + edgesBound);
                         }
                     } else {
                         label.setForeground(Color.red);
                         label.setText("Input value is not a number.");
                     }
             }
         } else {
             label.setForeground(Color.red);
             label.setText("Input value is not number.");
         }
    }

    public void drawGraph(Map<String, String> properties) {
        String name = "graph";
        boolean is_draw = ExternalCommand.draw(name, GenerateGraph.generateGraphSource(properties));
        if (is_draw) {
            ExternalCommand.open(name);
        }
    }
}

