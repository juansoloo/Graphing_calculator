package MVC.View;

import Algebra.Polynomial;
import MVC.Model.EquationModel;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * GraphView is the actual display that plots the input
 * expression on a XYChart using the XChart library. Listens
 * to the EquationModel and renders when ever the model changes
 */
public class GraphView extends View {
    // root panel for chart
    private JPanel rootGraph;
    private EquationModel model;

    // XChart objects
    private final XYChart chart;
    private final XChartPanel<XYChart> chartPanel;

    /**
     *  Constructor for graph view, creates the XYChart and embeds
     *  in rootGraph.
      */
    public GraphView() {
        this.chart = new XYChartBuilder()
                .width(600).height(400)
                .title("f(x)")
                .xAxisTitle("x")
                .yAxisTitle("f(x)")
                .build();
        this.chartPanel = new XChartPanel<>(chart);

        // Places chart panel inside rootGraph
        rootGraph.setLayout(new BorderLayout());
        rootGraph.add(chartPanel, BorderLayout.CENTER);
    }

    /**
     * Called by the model whenever the state changes, updates the
     * model and renders the graph based on the input expression.
     * @param m EquationModel
     */
    @Override
    public void modelChanged(EquationModel m) {
        this.model = m;
        render();
    }

    /**
     * Renders the graph using the current Polynomial stored in the model,
     */
    public void render() {
        Polynomial p = model.getGraph();

        if (p == null) {
            return;
        }

        graph(p, -10, 10, 400);
    }

    /**
     * Samples the polynomial p over the intervals xMin and xMax. Updates
     * XChart with the computed points.
     * @param p Polynomial to plot
     * @param xMin left boundary of x-axis
     * @param xMax right boundary of y-axis
     * @param samples the number of x values to sample
     */
    public void graph(Polynomial p, double xMin, double xMax, int samples) {
        double[] xData = new double[samples];
        double[] yData = new double[samples];

        // distance between x sample points
        double step = (xMax - xMin) / (samples - 1);

        // loops over samples, and computes x and fx
        for (int i = 0; i < samples; i++) {
            double x = xMin + i * step;
            xData[i] = x;
            yData[i] = p.evaluate(x);
        }

        // updates chart or creates it
        if (chart.getSeriesMap().containsKey("f(x)")) {
            chart.updateXYSeries("f(x)", xData, yData,null);
        } else {
            chart.addSeries("f(x)", xData, yData);
        }

        // paints swing component
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    /**
     * Returns the root swing component that displays the graph
     * @return rootGraph
     */
    public JPanel getRoot() {
        return rootGraph;
    }
}
