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


package org.dvare.expression.operation.validation;

import org.dvare.annotations.OperationType;
import org.dvare.binding.model.TypeBinding;
import org.dvare.config.ConfigurationRegistry;
import org.dvare.exceptions.parser.ExpressionParseException;
import org.dvare.exceptions.parser.IllegalValueException;
import org.dvare.expression.Expression;
import org.dvare.expression.NamedExpression;
import org.dvare.expression.datatype.DataType;
import org.dvare.expression.literal.DateLiteral;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

@org.dvare.annotations.Operation(type = OperationType.VALIDATION, symbols = {"ToDate", "toDate"}, dataTypes = {DataType.DateType, DataType.DateTimeType})
public class ToDate extends OperationExpression {


    public ToDate() {
        super("ToDate", "toDate");
    }

    public ToDate copy() {
        return new ToDate();
    }

    public int parse(String[] tokens, int pos, Stack<Expression> stack, TypeBinding typeBinding) throws ExpressionParseException {

        int i = findNextExpression(tokens, pos + 1, stack, typeBinding);

        SimpleDateFormat dateFormat = null;
        Expression formatExpression = stack.pop();
        if (formatExpression instanceof NamedExpression) {
            NamedExpression namedExpression = (NamedExpression) formatExpression;
            dateFormat = new SimpleDateFormat(namedExpression.getName());
        }

        String value = null;
        Expression valueExpression = stack.pop();
        if (valueExpression instanceof NamedExpression) {
            NamedExpression namedExpression = (NamedExpression) valueExpression;
            value = namedExpression.getName();
        }


        try {
            if (dateFormat != null && value != null) {
                Date date = dateFormat.parse(value);
                DateLiteral<Date> literal = new DateLiteral<>(date);
                stack.push(literal);
            }
        } catch (ParseException e) {
            String message = String.format(" Unable to Parse literal %s to Date Time", value);
            logger.error(message);
            throw new IllegalValueException(message);
        }

        return i;
    }

    @Override
    public Integer findNextExpression(String[] tokens, int pos, Stack<Expression> stack, TypeBinding typeBinding) throws ExpressionParseException {
        ConfigurationRegistry configurationRegistry = ConfigurationRegistry.INSTANCE;

        for (int i = pos; i < tokens.length; i++) {
            String token = tokens[i];

            Operation op = configurationRegistry.getValidationOperation(token);
            if (op != null) {
                op = op.copy();
                // we found an operation

                if (op.getClass().equals(RightPriority.class)) {
                    return i;
                }

            } else if (!token.isEmpty() && !token.equals(",")) {
                NamedExpression namedExpression = new NamedExpression(token);
                stack.push(namedExpression);
            }
        }
        return null;
    }


}