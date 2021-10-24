package wisedatalabs.classtest;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class RegressionSandbox {

    public static void main(String[] args) {

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

        // Instantiate data
        double[] y = new double[] { -0.48812477, 0.33458213, -0.52754476,
                -0.79863471, -0.68544309, -0.12970239, 0.02355622, -0.31890850,
                0.34725819, 0.08108851 };
        double[][] x = new double[10][];
        // Note that I have converted so that each data point is made up of
        // Genotype A, Genotype B, Genotype A * Genotype B
        x[0] = new double[] { 1, 0, 0 };
        x[1] = new double[] { 0, 0, 0 };
        x[2] = new double[] { 1, 0, 0 };
        x[3] = new double[] { 2, 1, 2 };
        x[4] = new double[] { 0, 1, 0 };
        x[5] = new double[] { 0, 0, 0 };
        x[6] = new double[] { 1, 0, 0 };
        x[7] = new double[] { 0, 0, 0 };
        x[8] = new double[] { 1, 0, 0 };
        x[9] = new double[] { 0, 0, 0 };

        // Add the data to the regression model
        regression.newSampleData(y, x);

        // Get the regression parameters and R-square value and print it.
        double[] beta = regression.estimateRegressionParameters();
        double rSquared = regression.calculateRSquared();

        System.out.println("Regression parameters: ");
        for (int i = 0; i < beta.length; i++) {
            System.out.println(beta[i]);
        }

        System.out.println("rSquared: " + rSquared);
    }

}