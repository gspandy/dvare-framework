/*The MIT License (MIT)

Copyright (c) 2016-2017 DVARE (Data Validation and Aggregation Rule Engine)

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
import org.dvare.binding.data.InstancesBinding;
import org.dvare.binding.expression.ExpressionBinding;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.Expression;
import org.dvare.expression.datatype.DataType;
import org.dvare.expression.datatype.FloatType;
import org.dvare.expression.datatype.IntegerType;
import org.dvare.expression.literal.LiteralType;
import org.dvare.expression.operation.AggregationOperationExpression;
import org.dvare.expression.operation.OperationType;
import org.dvare.expression.veriable.VariableExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Operation(type = OperationType.MEAN, dataTypes = {DataType.FloatType, DataType.IntegerType})
public class Mean extends AggregationOperationExpression {
    static Logger logger = LoggerFactory.getLogger(Mean.class);


    public Mean() {
        super(OperationType.MEAN);
    }


    @Override
    public Object interpret(ExpressionBinding expressionBinding, InstancesBinding instancesBinding) throws InterpretException {


        Expression right = this.leftOperand;
        if (right instanceof VariableExpression) {
            VariableExpression variableExpression = ((VariableExpression) right);
            String name = variableExpression.getName();
            DataType type = toDataType(variableExpression.getType());

            Object instance = instancesBinding.getInstance(variableExpression.getOperandType());
            List dataSet;
            if (instance instanceof List) {
                dataSet = (List) instance;
            } else {
                dataSet = new ArrayList<>();
                dataSet.add(instance);
            }


            switch (type) {

                case FloatType: {

                    List<Float> values = new ArrayList<>();

                    for (Object bindings : dataSet) {
                        Object value = getValue(bindings, name);
                        if (value instanceof Float) {
                            values.add((Float) value);
                        }
                    }

                    Collections.sort(values);
                    Float sum = 0f;
                    for (Float i : values) {
                        sum = sum + i;
                    }

                    Float result = sum / values.size();
                    leftExpression = LiteralType.getLiteralExpression(result, FloatType.class);


                    break;
                }
                case IntegerType: {
                    List<Integer> values = new ArrayList<>();

                    for (Object bindings : dataSet) {
                        Object value = getValue(bindings, name);
                        if (value instanceof Integer) {
                            values.add((Integer) value);
                        }

                    }

                    Collections.sort(values);
                    Integer sum = 0;
                    for (Integer i : values) {
                        sum = sum + i;
                    }

                    Integer result = sum / values.size();
                    leftExpression = LiteralType.getLiteralExpression(result, IntegerType.class);


                    break;
                }
            }
        }


        return leftExpression;
    }


}