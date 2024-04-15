package bot.expression;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Node that contains an immediate value.
 */
class ImmedNode extends ExpressionNode {
    private final double val;
    private static final String color = "aquamarine";

    /**
     * Constructs ImmedNode with specified value.
     * @param val value of the node
     */
    ImmedNode(double val) {
        this.val = val;
    }

    /**
     * Returns val.
     * @param x input
     * @return this.val
     */
    double calculate(double x) {
        return val;
    }

    void graphvizLog(FileWriter writer) throws IOException {
        writer.write(getGraphvizLabel("val: " + val, color));
    }
}
