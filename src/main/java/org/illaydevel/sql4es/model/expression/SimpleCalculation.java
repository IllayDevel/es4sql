package org.illaydevel.sql4es.model.expression;

import com.facebook.presto.sql.tree.ArithmeticBinaryExpression.Operator;
import com.facebook.presto.sql.tree.ArithmeticUnaryExpression.Sign;

import org.illaydevel.sql4es.ESResultSet;

public class SimpleCalculation implements ICalculation {

    private ICalculation left;
    private ICalculation right;
    private Operator type;
    private Number result = null;
    private Sign sign = Sign.PLUS;

    public SimpleCalculation(ICalculation left, ICalculation right, Operator type) {
        this.left = left;
        this.right = right;
        this.type = type;
    }

    @Override
    public SimpleCalculation setSign(Sign sign) {
        this.sign = sign;
        return this;
    }

    @Override
    public Number evaluate(ESResultSet rs, int rowNr) {
        Number l = left.evaluate(rs, rowNr);
        Number r = right.evaluate(rs, rowNr);
        switch (type) {
            case ADD:
                result = l.doubleValue() + r.doubleValue();
                break;
            case DIVIDE:
                result = l.doubleValue() / r.doubleValue();
                break;
            case MODULUS:
                result = l.doubleValue() % r.doubleValue();
                break;
            case MULTIPLY:
                result = l.doubleValue() * r.doubleValue();
                break;
            default:
                result = l.doubleValue() - r.doubleValue();
        }
        if (this.sign == Sign.MINUS) return -1 * result.doubleValue();
        return result;
    }

    public ICalculation left() {
        return left;
    }

    public ICalculation right() {
        return right;
    }

    public Operator getType() {
        return type;
    }

    public Sign getSign() {
        return sign;
    }

    public String toString() {
        return (sign == Sign.MINUS ? " -" : " ") + "( " + left + " " + type + " " + right + ") ";
    }
}
