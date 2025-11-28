package app;

import MVC.Controller.CalculatorController;
import MVC.Model.EquationModel;
import MVC.View.DisplayView;
import MVC.View.GraphView;
import MVC.View.KeypadView;
import MVC.View.UnitView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EquationModel model = new EquationModel();
        DisplayView displayView = new DisplayView();
        GraphView graphView = new GraphView();
        KeypadView keypadView = new KeypadView();
        UnitView unitView = new UnitView();

        JFrame frame = new JFrame("Advanced Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(8, 8));

        JPanel topArea = new JPanel(new BorderLayout());

        JPanel center = new JPanel(new BorderLayout());
        center.add(topArea, BorderLayout.NORTH);
        center.add(keypadView.getComponent(), BorderLayout.CENTER);

        CalculatorController controller = new CalculatorController(
                model, keypadView, graphView, frame, displayView, unitView, topArea);

        keypadView.connect(controller);
        unitView.connect(model, controller);

        model.addListener(displayView);
        model.addListener(graphView);
        model.addListener(unitView);

        frame.add(displayView.getComponent(), BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
