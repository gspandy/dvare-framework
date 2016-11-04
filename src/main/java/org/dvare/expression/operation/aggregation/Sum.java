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


package org.dvare.expression.operation.aggregation;

import org.dvare.annotations.Operation;
import org.dvare.exceptions.interpreter.IllegalOperationException;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.Expression;
import org.dvare.expression.datatype.DataType;
import org.dvare.expression.literal.LiteralType;
import org.dvare.expression.operation.AggregationOperationExpression;
import org.dvare.expression.operation.OperationType;
import org.dvare.expression.veriable.VariableExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Operation(type = OperationType.SUM, dataTypes = {DataType.FloatType, DataType.IntegerType})
public class Sum extends AggregationOperationExpression {
    static Logger logger = LoggerFactory.getLogger(Sum.class);


    public Sum() {
        super(OperationType.SUM);
    }

    public Sum copy() {
        return new Sum();
    }

    @Override
    public Object interpret(Object aggregation, List<Object> dataSet) throws InterpretException {


        Expression right = this.rightOperand;

        if (right instanceof VariableExpression) {
            VariableExpression variableExpression = ((VariableExpression) right);
            String name = variableExpression.getName();
            DataType type = variableExpression.getType().getDataType();

            switch (type) {

                case FloatType: {
                    leftOperand = LiteralType.getLiteralExpression(Float.valueOf(0f), variableExpression.getType());
                    break;
                }
                case IntegerType: {
                    leftOperand = LiteralType.getLiteralExpression(Integer.valueOf(0), variableExpression.getType());
                    break;
                }
                case StringType: {
                    leftOperand = LiteralType.getLiteralExpression("", variableExpression.getType());
                    break;
                }
                default: {
                    throw new IllegalOperationException("Sum OperationExpression Not Allowed");
                }

            }
        }


        return super.interpret(aggregation, dataSet);
    }

}