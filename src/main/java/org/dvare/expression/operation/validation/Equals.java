package org.dvare.expression.operation.validation;

import org.dvare.annotations.OperationType;

@org.dvare.annotations.Operation(type = OperationType.VALIDATION, symbols = {"eq", "="})
public class Equals extends EqualityOperationExpression {
    public Equals() {
        super("eq", "=");
    }

    public Equals copy() {
        return new Equals();
    }

}