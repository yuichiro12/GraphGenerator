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
    private JTextField graph_name = null;
    private JTextField node_size = null;
    private JTextField edge_size = null;

    // Label
    private JLabel label = null;

    // CheckBox
    private JCheckBox is_multiple_edge = null;
    private JCheckBox is_allow_loop = null;



    // bootstrap
    public static void bootstrap() {
        GraphUI frame = new GraphUI();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("GraphGenerator");
        frame.setBounds(150, 300, 600, 180);
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
        graph_name = new JTextField("", 10);
        node_size = new JTextField("", 3);
        edge_size = new JTextField("", 3);


        // make ComboBoxes
        graph_type = new JComboBox<>(graphType);
        draw_type = new JComboBox<>(drawType);

        // make Labels
        label = new JLabel();
        label.setPreferredSize(new Dimension(260, 20));
        label.setBorder(new BevelBorder(BevelBorder.LOWERED));
        label.setBackground(Color.black);
        label.setForeground(Color.green);
        label.setText("Choose the parameter");
        label.setOpaque(true);

        // make Checkboxes
        is_multiple_edge = new JCheckBox("multiple edges");
        is_allow_loop = new JCheckBox("loop");

        // make a button
        JButton button = new JButton("generate");

        // action listener
        button.addActionListener(this);

        // add comboBoxes, labels, and buttons
        panelBase.add(graph_name);
        panelBase.add(graph_type);
        panelBase.add(draw_type);
        panelBase.add(node_size);
        panelBase.add(edge_size);
        panelBase.add(is_multiple_edge);
        panelBase.add(is_allow_loop);
        panelBase.add(label);
        panelBase.add(button);

        // add panel
        getContentPane().add(panelBase);
    }


    static boolean isNum(String number) {
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
    public void actionPerformed(ActionEvent e) {
        Map<String, String> properties = new HashMap<>();
        String graphtype = (String) graph_type.getSelectedItem();
        String drawtype = (String) draw_type.getSelectedItem();
        String name = graph_name.getText();
        String nodes = node_size.getText();
        String edges = edge_size.getText();
        String multiple = is_multiple_edge.isSelected() ? "true" : "false";
        String loop = is_allow_loop.isSelected() ? "true" : "false";

         if (isNum(nodes) & isNum(edges)) {
             properties.put("name", name);
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
             label.setText("Input value is not number.");
         }
    }

    public void drawGraph(Map properties) {
        String name = "graph";
        boolean is_draw = ExternalCommand.draw(name, GenerateGraph.generateGraphSource(properties));
        if (is_draw) {
            ExternalCommand.open(name);
        }
    }
}

