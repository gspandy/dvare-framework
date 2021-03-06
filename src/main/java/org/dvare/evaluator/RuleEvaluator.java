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


package org.dvare.evaluator;


import org.dvare.binding.data.InstancesBinding;
import org.dvare.binding.expression.ExpressionBinding;
import org.dvare.binding.rule.RuleBinding;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.literal.LiteralExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class RuleEvaluator {
    static Logger logger = LoggerFactory.getLogger(RuleEvaluator.class);

    public Object evaluate(RuleBinding rule, Object object) throws InterpretException {

        return evaluate(rule, object, null);
    }

    public Object evaluate(RuleBinding rule, Object self, Object data) throws InterpretException {
        InstancesBinding instancesBinding = new InstancesBinding(new HashMap<>());
        instancesBinding.addInstance("self", self);
        instancesBinding.addInstance("data", data);

        return evaluate(rule, instancesBinding);
    }


    public Object evaluate(RuleBinding rule, InstancesBinding instancesBinding) throws InterpretException {

        return evaluate(rule, null, instancesBinding);
    }

    public Object evaluate(RuleBinding rule, ExpressionBinding expressionBinding, InstancesBinding instancesBinding) throws InterpretException {


        Object result = null;
        Object ruleRawResult = rule.getExpression().interpret(expressionBinding, instancesBinding);
        if (ruleRawResult instanceof LiteralExpression) {
            LiteralExpression literalExpression = (LiteralExpression) ruleRawResult;
            if (literalExpression.getValue() != null) {
                result = literalExpression.getValue();
            }
        } else {
            result = ruleRawResult;
        }
        return result;
    }


    public InstancesBinding aggregate(List<RuleBinding> rules, InstancesBinding instancesBinding) throws InterpretException {
        return aggregate(rules, null, instancesBinding);
    }


    public InstancesBinding aggregate(List<RuleBinding> rules, ExpressionBinding expressionBinding, InstancesBinding instancesBinding) throws InterpretException {


        for (RuleBinding rule : rules) {
            rule.getExpression().interpret(expressionBinding, instancesBinding);
        }

        return instancesBinding;
    }


    public Object aggregate(RuleBinding rule, InstancesBinding instancesBinding) throws InterpretException {
        return aggregate(rule, null, instancesBinding);
    }


    public Object aggregate(RuleBinding rule, ExpressionBinding expressionBinding, InstancesBinding instancesBinding) throws InterpretException {

        rule.getExpression().interpret(expressionBinding, instancesBinding);
        return instancesBinding;
    }

    public Object aggregate(List<RuleBinding> rules, Object aggregate, Object dataset) throws InterpretException {
        InstancesBinding instancesBinding = new InstancesBinding(new HashMap<>());
        instancesBinding.addInstance("self", aggregate);
        instancesBinding.addInstance("data", dataset);

        for (RuleBinding rule : rules) {
            rule.getExpression().interpret(null, instancesBinding);
        }

        return instancesBinding.getInstance("self");
    }


    public Object aggregate(RuleBinding rule, Object aggregate, Object dataset) throws InterpretException {
        InstancesBinding instancesBinding = new InstancesBinding(new HashMap<>());
        instancesBinding.addInstance("self", aggregate);
        instancesBinding.addInstance("data", dataset);
        rule.getExpression().interpret(null, instancesBinding);
        return instancesBinding.getInstance("self");
    }

}
