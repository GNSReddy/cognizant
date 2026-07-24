package exercise1;

import org.springframework.stereotype.Service;

@Service("calculatorServiceEx1")
public class CalculatorService {
    public int add(int a, int b) {
        return a + b;
    }
}
