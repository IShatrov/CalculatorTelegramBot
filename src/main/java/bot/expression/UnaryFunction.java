package bot.expression;

import java.util.function.DoubleUnaryOperator;

public enum UnaryFunction {
    SIN(Math::sin, "sin"),
    COS(Math::cos, "cos"),
    TAN(Math::tan, "tan"),
    LN(Math::log, "ln"),
    EXP(Math::exp, "exp"),
    SQRT(Math::sqrt, "sqrt");

    UnaryFunction(DoubleUnaryOperator func, String str) {
        calculation = func;
        stringRepresentation = str;
    }

    final DoubleUnaryOperator calculation;
    final String stringRepresentation;
}
