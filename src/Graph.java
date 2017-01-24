/**
 * Created by mov on 2017/01/24.
 */

public class Graph {
    public static void main(String args[]) {
        String name = "graph";
        boolean is_draw = ExternalCommand.draw(name, GenerateGraph.generateGraphSource(name));
        if (is_draw) {
            ExternalCommand.open(name);
        }
    }
}
