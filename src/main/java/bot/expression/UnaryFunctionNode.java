package bot.expression;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Node that contains a unary function like sin and ln.
 */
class UnaryFunctionNode extends ExpressionNode {
    private final UnaryFunction func;
    private static final String color = "greenyellow";

    /**
     * Constructs UnaryFunctionNode with specified function.
     * @param func function of the node
     */
    UnaryFunctionNode(UnaryFunction func) {
        this.func = func;
    }

    /**
     * Applies unary function to this.left.
     * @param x input
     * @return calculated value
     */
    double calculate(double x) {
        return func.calculation.applyAsDouble(left.calculate(x));
    }

    void graphvizLog(FileWriter writer) throws IOException {
        writer.write(getGraphvizLabel("func: " + func.stringRepresentation, color));
    }
}
