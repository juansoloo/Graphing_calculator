package app;

import MVC.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EquationModel model = new EquationModel();
        DisplayView display = new DisplayView();
//        HistoryView history = new HistoryView();
        KeypadView keypad = new KeypadView();
        CalculatorController controller = new CalculatorController(model);

        keypad.connect(controller);

        model.addListener(display);

        JFrame frame = new JFrame("Advanced Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(8, 8));

        frame.add(display.getComponent(), BorderLayout.NORTH);
        frame.add(keypad.getComponent(),   BorderLayout.CENTER);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
