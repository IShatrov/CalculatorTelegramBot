package bot.expression;

import java.io.FileWriter;
import java.io.IOException;

/**
 *  This abstract class defines a basic element of an abstract syntax tree for math expressions.
 */
abstract class ExpressionNode {
    ExpressionNode left, right;

    /**
     * A setter method for left child.
     * @param target node to be set as left child
     */
    void setLeft(ExpressionNode target) {
        left = target;
    }

    /**
     * A setter method for right child.
     * @param target node to be set as right child
     */
    void setRight(ExpressionNode target) {
        right = target;
    }

    /**
     * Calculates f(x) where f is a math expression represented by a subtree with this node as the root.
     * @param x input
     * @return output
     */
    abstract double calculate(double x);

    /**
     * Writes node`s children in graphviz log, and then writes the node itself.
     * @param writer FileWriter to log in
     * @throws IOException when writing fails
     */
    void graphvizWriteChildren(FileWriter writer) throws IOException {
        if (left != null) {
            writer.write("\t\"" + this + "\"->\"" + left + "\";\n");
            left.graphvizWriteChildren(writer);
        }

        if (right != null) {
            writer.write("\t\"" + this + "\"->\"" + right + "\";\n");
            right.graphvizWriteChildren(writer);
        }

        this.graphvizLog(writer);
    }

    /**
     * Generates a label of this node for graphviz log.
     * @param label text to add to the label
     * @param color color of the node
     * @see <a href="https://graphviz.org/doc/info/colors.html">graphviz documentation</a>
     * @return graphviz label
     */
    String getGraphvizLabel(String label, String color) {
        return "\t\"" + this + "\"[label = \"{" + this + " |" + label + "}\", fillcolor = " + color + "];\n\n";
    }

    /**
     * Writes information about this node to graphviz log.
     * @param writer FileWriter to log in
     * @throws IOException when writing fails
     */
    abstract void graphvizLog(FileWriter writer) throws IOException;
}
