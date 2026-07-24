import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite

@SelectClasses({

        CalculatorTest.class,

        EvenChecker.class

})

public class AllTests {

}