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

import org.dvare.annotations.Operation;
import org.dvare.binding.model.TypeBinding;
import org.dvare.config.ConfigurationRegistry;
import org.dvare.exceptions.parser.ExpressionParseException;
import org.dvare.exceptions.parser.IllegalValueException;
import org.dvare.expression.Expression;
import org.dvare.expression.NamedExpression;
import org.dvare.expression.datatype.DataType;
import org.dvare.expression.literal.DateLiteral;
import org.dvare.expression.literal.LiteralType;
import org.dvare.expression.operation.OperationExpression;
import org.dvare.expression.operation.OperationType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Stack;

@Operation(type = OperationType.DATE, dataTypes = {DataType.DateType})
public class Date extends OperationExpression {


    public Date() {
        super(OperationType.DATE);
    }

    public Date copy() {
        return new Date();
    }


    @Override
    public Integer parse(String[] tokens, int pos, Stack<Expression> stack, TypeBinding selfTypes, TypeBinding dataTypes) throws ExpressionParseException {

        if (dataTypes == null) {
            pos = findNextExpression(tokens, pos + 1, stack, selfTypes, null);
        } else {
            pos = findNextExpression(tokens, pos + 1, stack, selfTypes, dataTypes);
        }

        SimpleDateFormat dateFormat = null;
        String value = null;

        Expression expression = stack.pop();

        if (!stack.isEmpty() && stack.peek() instanceof NamedExpression) {

            if (expression instanceof NamedExpression) {
                NamedExpression namedExpression = (NamedExpression) expression;
                dateFormat = new SimpleDateFormat(namedExpression.getName());
            }


            Expression valueExpression = stack.pop();
            if (valueExpression instanceof NamedExpression) {
                NamedExpression namedExpression = (NamedExpression) valueExpression;
                value = namedExpression.getName();
            }


        } else if (expression instanceof NamedExpression) {

            dateFormat = LiteralType.dateFormat;

            NamedExpression namedExpression = (NamedExpression) expression;
            value = namedExpression.getName();


        }


        try {
            if (dateFormat != null && value != null) {
                java.util.Date date = dateFormat.parse(value);
                DateLiteral<java.util.Date> literal = new DateLiteral<>(date);
                stack.push(literal);
            }
        } catch (ParseException e) {
            String message = String.format(" Unable to Parse literal %s to Date Time", value);
            logger.error(message);
            throw new IllegalValueException(message);
        }

        return pos;
    }


    @Override
    public Integer findNextExpression(String[] tokens, int pos, Stack<Expression> stack, TypeBinding selfTypes, TypeBinding dataTypes) throws ExpressionParseException {
        ConfigurationRegistry configurationRegistry = ConfigurationRegistry.INSTANCE;

        for (int i = pos; i < tokens.length; i++) {
            String token = tokens[i];

            OperationExpression op = configurationRegistry.getOperation(token);
            if (op != null) {
                op = op.copy();

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