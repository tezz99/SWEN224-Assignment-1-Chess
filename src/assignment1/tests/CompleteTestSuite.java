package assignment1.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        assignment1.tests.part1.Part1TestSuite.class,
        assignment1.tests.part2.Part2TestSuite.class,
        assignment1.tests.part3.Part3TestSuite.class
})
public class CompleteTestSuite {
}
