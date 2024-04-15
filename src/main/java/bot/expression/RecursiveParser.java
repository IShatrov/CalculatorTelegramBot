package bot.expression;

import java.io.IOException;

public class RecursiveParser {
    private static final String GRAPHVIZ_FILENAME = "graphviz.txt";

    public static ExpressionTree parse(String str)
            throws IllegalArgumentException, IOException {
        StringBuilder strNoWhitespaces = new StringBuilder(str.replaceAll(" ", ""));

        ExpressionTree ans = new ExpressionTree(getExpr(strNoWhitespaces));

        ans.graphvizLog(GRAPHVIZ_FILENAME);

        if (!strNoWhitespaces.isEmpty()) {
            throw new IllegalArgumentException("Ending string not empty");
        }

        return ans;
    }

    private static ExpressionNode getImmed(StringBuilder str) throws NumberFormatException {
        int i = 0;

        if (str.charAt(i) == '-') {
            i++;
        }

        if (!Character.isDigit(str.charAt(i))) {
            throw new NumberFormatException("Double missing integral part");
        }

        while (i < str.length() && Character.isDigit(str.charAt(i))) {
            i++;
        }

        if (i < str.length() && str.charAt(i) == '.') {
            i++;

            if (i >= str.length() || !Character.isDigit(str.charAt(i))) {
                throw new NumberFormatException("Double missing fractional part");
            }

            while (i < str.length() && Character.isDigit(str.charAt(i))) {
                i++;
            }
        }

        ImmedNode ans = new ImmedNode(Double.parseDouble(str.substring(0, i)));

        str.delete(0, i);

        return ans;
    }

    private static ExpressionNode getExpr(StringBuilder str) throws IllegalArgumentException {
        ExpressionNode left = getTerm(str);

        if (!str.isEmpty() && (str.charAt(0) == '+' || str.charAt(0) == '-')) {
            while (!str.isEmpty() && (str.charAt(0) == '+' || str.charAt(0) == '-')) {
                char op = str.charAt(0);

                str.deleteCharAt(0);

                ExpressionNode tmp;

                if (op == '+') {
                    tmp = new BinaryOperatorNode(BinaryOperator.SUM);
                } else {
                    tmp = new BinaryOperatorNode(BinaryOperator.DIFF);
                }

                tmp.setLeft(left);
                tmp.setRight(getTerm(str));

                left = tmp;
            }
        }

        return left;
    }

    private static ExpressionNode getTerm(StringBuilder str) throws IllegalArgumentException {
        ExpressionNode left = getPow(str);

        if (!str.isEmpty() && (str.charAt(0) == '*' || str.charAt(0) == '/')) {
            char op = str.charAt(0);

            str.deleteCharAt(0);

            ExpressionNode right = getTerm(str);

            ExpressionNode ans;

            if (op == '*') {
                ans = new BinaryOperatorNode(BinaryOperator.MULT);
            } else {
                ans = new BinaryOperatorNode(BinaryOperator.DIV);
            }

            ans.setLeft(left);
            ans.setRight(right);

            return ans;
        }

        return left;
    }

    private static ExpressionNode getPow(StringBuilder str) throws IllegalArgumentException {
        ExpressionNode left = getPrim(str);

        if (!str.isEmpty() && str.charAt(0) == '^') {
            str.deleteCharAt(0);

            ExpressionNode right = getPrim(str);

            ExpressionNode ans = new BinaryOperatorNode(BinaryOperator.POW);

            ans.setLeft(left);
            ans.setRight(right);

            return ans;
        }

        return left;
    }

    private static ExpressionNode getPrim(StringBuilder str) throws IllegalArgumentException {
        if (str.charAt(0) == '(') {
            str.deleteCharAt(0);

            ExpressionNode ans = getExpr(str);

            if (str.charAt(0) != ')') {
                throw new IllegalArgumentException("Missing )");
            }

            str.deleteCharAt(0);

            return ans;
        }

        return getUnary(str);
    }

    private static ExpressionNode getUnary(StringBuilder str) throws IllegalArgumentException {
        int i = 0;

        while (i < str.length() && Character.isAlphabetic(str.charAt(i))) {
            i++;
        }

        if (i == 0) {
            return getImmed(str);
        }

        String unary = str.substring(0, i);

        if (unary.equals("x")) {
            str.deleteCharAt(0);

            return new XNode();
        }

        for (UnaryFunction func : UnaryFunction.values()) {
            if (unary.equals(func.stringRepresentation)) {
                str.delete(0, i);

                ExpressionNode ans = new UnaryFunctionNode(func);

                ans.setLeft(getArg(str));

                return ans;
            }
        }

        throw new IllegalArgumentException("Unresolved function " + unary);
    }

    private static ExpressionNode getArg(StringBuilder str) throws IllegalArgumentException {
        if (str.charAt(0) != '(') {
            throw new IllegalArgumentException("Missing ( in argument");
        }

        str.deleteCharAt(0);

        ExpressionNode ans = getExpr(str);

        if (str.charAt(0) != ')') {
            throw new IllegalArgumentException("Missing ) in argument");
        }

        str.deleteCharAt(0);

        return ans;
    }
}
