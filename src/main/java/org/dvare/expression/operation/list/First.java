package org.dvare.expression.operation.list;

import org.dvare.annotations.Operation;
import org.dvare.binding.data.InstancesBinding;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.Expression;
import org.dvare.expression.literal.ListLiteral;
import org.dvare.expression.literal.LiteralExpression;
import org.dvare.expression.literal.LiteralType;
import org.dvare.expression.literal.NullLiteral;
import org.dvare.expression.operation.AggregationOperationExpression;
import org.dvare.expression.operation.OperationExpression;
import org.dvare.expression.operation.OperationType;
import org.dvare.expression.veriable.VariableExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Operation(type = OperationType.FIRST)
public class First extends AggregationOperationExpression {
    static Logger logger = LoggerFactory.getLogger(First.class);


    public First() {
        super(OperationType.FIRST);
    }

    @Override
    public Object interpret(InstancesBinding instancesBinding) throws InterpretException {

        Expression right = leftOperand;
        if (right instanceof Values) {
            OperationExpression valuesOperation = (OperationExpression) right;
            Object valuesResult = valuesOperation.interpret(instancesBinding);
            if (valuesResult instanceof ListLiteral) {
                ListLiteral listLiteral = (ListLiteral) valuesResult;
                List values = listLiteral.getValue();
                if (!values.isEmpty()) {
                    return LiteralType.getLiteralExpression(values.get(0), listLiteral.getType());
                }
            }
        } else if (right instanceof VariableExpression) {
            VariableExpression variableExpression = (VariableExpression) right;

            Object instance = instancesBinding.getInstance(variableExpression.getOperandType());
            List dataSet;
            if (instance instanceof List) {
                dataSet = (List) instance;
            } else {
                dataSet = new ArrayList<>();
                dataSet.add(instance);
            }

            List<Object> values = new ArrayList<>();
            for (Object object : dataSet) {
                Object value = getValue(object, variableExpression.getName());
                LiteralExpression literalExpression = LiteralType.getLiteralExpression(value, variableExpression.getType());
                values.add(literalExpression.getValue());
            }
            if (!values.isEmpty()) {
                return LiteralType.getLiteralExpression(values.get(0), variableExpression.getType());
            }
        }

        return new NullLiteral();
    }


}