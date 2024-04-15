package bot.expression;

import java.util.function.DoubleBinaryOperator;

enum BinaryOperator {
    SUM(Double::sum, "+"),
    DIFF((left, right) -> (left - right), "-"),
    MULT((left, right) -> (left * right), "*"),
    DIV((left, right) -> (left / right), "/"),
    POW(Math::pow, "^");

    BinaryOperator(DoubleBinaryOperator op, String str) {
        calculation = op;
        stringRepresentation = str;
    }

    final DoubleBinaryOperator calculation;
    final String stringRepresentation;
}
