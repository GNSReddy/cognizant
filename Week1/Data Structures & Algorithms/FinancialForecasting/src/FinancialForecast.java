public class FinancialForecast {

    // Recursive Method
    public static double forecast(double currentValue,
                                  double growthRate,
                                  int years) {

        // Base Case
        if (years == 0) {
            return currentValue;
        }

        // Recursive Case
        return (1 + growthRate)
                * forecast(currentValue,
                           growthRate,
                           years - 1);

    }

}