package bot.expression;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Node that contains a binary operator such as + and *.
 */
class BinaryOperatorNode extends ExpressionNode {
    private final BinaryOperator op;
    private static final String color = "cornsilk2";

    /**
     * Constructs BinaryOperatorNode with specified operator.
     * @param op operator of the node
     */
    BinaryOperatorNode(BinaryOperator op) {
        this.op = op;
    }

    /**
     * Applies binary operator to this.left and this.right.
     * @param x input
     * @return calculated value
     */
    double calculate(double x) {
        return op.calculation.applyAsDouble(left.calculate(x), right.calculate(x));
    }

    void graphvizLog(FileWriter writer) throws IOException {
        writer.write(getGraphvizLabel("op: " + op.stringRepresentation, color));
    }
}
