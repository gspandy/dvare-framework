/*The MIT License (MIT)

Copyright (c) 2016 Muhammad Hammad

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Sogiftware.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.*/

package org.dvare.expression.operation;

import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.Expression;
import org.dvare.expression.literal.LiteralExpression;
import org.dvare.expression.literal.NullLiteral;

import java.util.List;

public abstract class ArithmeticOperationExpression extends EqualityOperationExpression {


    public ArithmeticOperationExpression(OperationType operationType) {
        super(operationType);
    }

    @Override
    public Object interpret(Object dataRow) throws InterpretException {
        interpretOperand(dataRow, null);
        Expression leftExpression = leftValueOperand;
        if (leftExpression == null)
            return new NullLiteral();
        LiteralExpression<?> rightExpression = (LiteralExpression) rightValueOperand;
        return dataTypeExpression.evaluate(this, leftExpression, rightExpression);

    }

    @Override
    public Object interpret(Object selfRow, List<Object> dataset) throws InterpretException {
        interpretOperand(selfRow, dataset.get(0));
        Expression leftExpression = leftValueOperand;
        if (leftExpression == null)
            return new NullLiteral();
        LiteralExpression<?> rightExpression = (LiteralExpression) rightValueOperand;
        return dataTypeExpression.evaluate(this, leftExpression, rightExpression);
    }

    @Override
    public Object interpret(Object selfRow, Object dataRow) throws InterpretException {
        interpretOperand(selfRow, dataRow);
        Expression leftExpression = leftValueOperand;
        if (leftExpression == null)
            return new NullLiteral();
        LiteralExpression<?> rightExpression = (LiteralExpression) rightValueOperand;
        return dataTypeExpression.evaluate(this, leftExpression, rightExpression);

    }
}