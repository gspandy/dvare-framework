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


package org.dvare.expression.operation.chain;

import org.dvare.annotations.Operation;
import org.dvare.binding.data.InstancesBinding;
import org.dvare.binding.expression.ExpressionBinding;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.literal.LiteralExpression;
import org.dvare.expression.literal.LiteralType;
import org.dvare.expression.literal.NullLiteral;
import org.dvare.expression.operation.ChainOperationExpression;
import org.dvare.expression.operation.OperationType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Operation(type = OperationType.ADD_YEARS)
public class addYears extends ChainOperationExpression {


    public addYears() {
        super(OperationType.ADD_YEARS);
    }


    @Override
    public Object interpret(ExpressionBinding expressionBinding, InstancesBinding instancesBinding) throws InterpretException {
        leftValueOperand = super.interpretOperand(this.leftOperand, expressionBinding, instancesBinding);
        LiteralExpression literalExpression = toLiteralExpression(leftValueOperand);
        if ((literalExpression != null && !(literalExpression instanceof NullLiteral)) && (rightOperand != null && rightOperand.size() == 1)) {


            if (literalExpression.getValue() == null) {
                return new NullLiteral();
            }

            LiteralExpression yearsExpression = (LiteralExpression) rightOperand.get(0);
            Object yearValue = yearsExpression.getValue();
            Object value = literalExpression.getValue();
            dataTypeExpression = literalExpression.getType();
            switch (toDataType(dataTypeExpression)) {
                case DateType: {
                    if (value instanceof LocalDate && yearValue instanceof Integer) {

                        LocalDate localDate = (LocalDate) value;
                        Integer year = (Integer) yearValue;
                        localDate = localDate.plusYears(year);

                        return LiteralType.getLiteralExpression(localDate, dataTypeExpression);

                    }
                    break;
                }

                case DateTimeType: {
                    if (value instanceof LocalDateTime && yearValue instanceof Integer) {

                        LocalDateTime localDateTime = (LocalDateTime) value;
                        Integer year = (Integer) yearValue;
                        localDateTime = localDateTime.plusYears(year);
                        return LiteralType.getLiteralExpression(localDateTime, dataTypeExpression);

                    }
                    break;
                }

                case SimpleDateType: {
                    if (value instanceof Date && yearValue instanceof Integer) {

                        Date date = (Date) value;
                        Integer year = (Integer) yearValue;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.YEAR, year);
                        return LiteralType.getLiteralExpression(calendar.getTime(), dataTypeExpression);
                    }
                    break;
                }


            }


        }

        return new NullLiteral<>();
    }

}