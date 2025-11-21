package Graphing;

import Algebra.Polynomial;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.List;

public class TestPolyPlot {
    public static void main(String[] args) {
        Polynomial p = Polynomial.of(List.of(1, 2, 2));

        int samples = 400;
        double xMin = -10;
        double xMax = 10;
        double[] xData = new double[samples];
        double[] yData = new double[samples];

        double step = (xMax - xMin) / (samples - 1);

        for(int i = 0; i<samples;i++) {
            double x = xMin + i * step;
            xData[i] = x;
            yData[i] = p.evaluate(x);
        }

        XYChart chart = new XYChartBuilder()
                .width(800).height(700)
                .title("f(x) = 2x^2 + 2x + 1")
                .xAxisTitle("x")
                .yAxisTitle("f(x)")
                .build();

        chart.addSeries("f(x)", xData, yData);

        new SwingWrapper<>(chart).displayChart();
    }
}
