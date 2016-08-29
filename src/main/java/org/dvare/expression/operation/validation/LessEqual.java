package org.dvare.expression.operation.validation;

import org.dvare.annotations.OperationType;
import org.dvare.expression.datatype.DataType;

@org.dvare.annotations.Operation(type = OperationType.VALIDATION, symbols = {"le", "<="}, dataTypes = {DataType.FloatType, DataType.IntegerType, DataType.StringType})
public class LessEqual extends EqualityOperationExpression {
    public LessEqual() {
        super("le", "<=");
    }

    public LessEqual copy() {
        return new LessEqual();
    }


}