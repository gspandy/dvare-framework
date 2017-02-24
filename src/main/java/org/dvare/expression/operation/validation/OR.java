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
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.operation.ValidationOperationExpression;

@org.dvare.annotations.Operation(type = OperationType.VALIDATION, symbols = {"OR", "or", "||"})
public class OR extends ValidationOperationExpression {
    public OR() {
        super("OR", "or", "||");
    }

    public OR copy() {

        return new OR();
    }


    @Override
    public Object interpret(Object object) throws InterpretException {
        boolean left = (Boolean) leftOperand.interpret(object);
        boolean right = (Boolean) rightOperand.interpret(object);

        return left | right;
    }
}