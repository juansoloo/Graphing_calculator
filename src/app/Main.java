package app;

import MVC.Controller.CalculatorController;
import MVC.Model.EquationModel;
import MVC.View.DisplayView;
import MVC.View.GraphView;
import MVC.View.KeypadView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EquationModel model = new EquationModel();
        DisplayView display = new DisplayView();
        GraphView graphView = new GraphView();
        KeypadView keypad = new KeypadView();

        JFrame frame = new JFrame("Advanced Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(8, 8));

        CalculatorController controller = new CalculatorController(model, keypad, graphView, frame);
        keypad.connect(controller);

        model.addListener(display);
        model.addListener(graphView);


        frame.add(display.getComponent(), BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.add(graphView.getRoot(), BorderLayout.NORTH);
        center.add(keypad.getComponent(), BorderLayout.CENTER);

        graphView.getRoot().setVisible(false);

        frame.add(center, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
