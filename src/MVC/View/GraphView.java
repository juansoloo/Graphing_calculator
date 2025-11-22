package MVC.View;

import Algebra.Polynomial;
import MVC.Model.EquationModel;
import Parser.EquationParser;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.awt.*;

public class GraphView extends View{
    private JPanel rootGraph;
    private EquationModel model;

    private final XYChart chart;
    private final XChartPanel<XYChart> chartPanel;

    public GraphView() {
        this.chart = new XYChartBuilder()
                .width(600).height(400)
                .title("f(x)")
                .xAxisTitle("x")
                .yAxisTitle("f(x)")
                .build();
        this.chartPanel = new XChartPanel<>(chart);

        rootGraph.setLayout(new BorderLayout());
        rootGraph.add(chartPanel, BorderLayout.CENTER);
    }

    @Override
    public void modelChanged(EquationModel m) {
        this.model = m;
        render();
    }

    @Override
    public void render() {
        if (model == null) return;

        String expr = model.getInput();
        if (expr == null || expr.isBlank()) {
            return; // nothing to graph
        }

        Polynomial p;
        try {
            // Use the same strategies as the model
            EquationParser parser = new EquationParser(
                    expr,
                    model.getAddOp(),
                    model.getSubOp(),
                    model.getMulOp(),
                    model.getDivOp(),
                    model.getNegOp(),
                    model.getPowOp(),
                    model.getRootOp()
            );
            p = parser.parse();
        } catch (Exception e) {
            // You could also show an error somewhere; for graph view, just skip
            return;
        }

        graph(p, -10, 10, 400);
    }

    public void graph(Polynomial p, double xMin, double xMax, int samples) {
        double[] xData = new double[samples];
        double[] yData = new double[samples];

        double step = (xMax - xMin) / (samples - 1);

        for (int i = 0; i < samples; i++) {
            double x = xMin + i * step;
            xData[i] = x;
            yData[i] = p.evaluate(x);
        }

        if (chart.getSeriesMap().containsKey("f(x)")) {
            chart.updateXYSeries("f(x)", xData, yData,null);
        } else {
            chart.addSeries("f(x)", xData, yData);
        }

        chartPanel.revalidate();
        chartPanel.repaint();
    }

    public JPanel getRoot() {
        return rootGraph;
    }
}
