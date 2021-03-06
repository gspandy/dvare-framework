package org.dvare;

import org.dvare.test.aggregation.*;
import org.dvare.test.list.LengthTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
     /*   FunctionTestExclude.class,*/
        SumTest.class,
        MinTest.class,
        MaxTest.class,
        MeanTest.class,
        ModeTest.class,
        MedianTest.class,
        SemicolonTest.class,
        ConditionTest.class,
        LengthTest.class,
        ConditionChainTest.class,
        DistanceTest.class})
public class AggregationTest {

}
