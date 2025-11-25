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
        KeypadView keypad = new KeypadView();
        UnitView unitView = new UnitView();

        JFrame frame = new JFrame("Advanced Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(8, 8));

        // Panel that the controller will use to show GRAPH or UNIT view
        JPanel topArea = new JPanel(new BorderLayout());

        // Center area holds topArea (graph/unit) above the keypad
        JPanel center = new JPanel(new BorderLayout());
        center.add(topArea, BorderLayout.NORTH);
        center.add(keypad.getComponent(), BorderLayout.CENTER);

        // Wire controller
        CalculatorController controller = new CalculatorController(
                model, keypad, graphView, frame, displayView, unitView, topArea);
        keypad.connect(controller);

        // Model listeners
        model.addListener(displayView);
        model.addListener(graphView);

        // Add main components to frame
        frame.add(displayView.getComponent(), BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);

        // Initial pack and show
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
