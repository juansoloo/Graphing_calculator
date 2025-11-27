package MVC.View;

import MVC.Controller.CalculatorController;
import MVC.Model.EquationModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * KeypadView is the actual calculator keypad, each
 * JButton corresponds to a token that the controller will
 * use. (digits, operators, modes, parentheses, etc.)
 */
public class KeypadView extends View {
    private JPanel root;

    private GraphView graphView;

    // digits
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a0Button;

    // operators
    private JButton plusNegButton;
    private JButton powButton;
    private JButton divButton;
    private JButton minButton;
    private JButton multiButton;
    private JButton sqrtButton;
    private JButton addButton;

    // parentheses, variable x, symbolic "="
    private JButton LeftParen;
    private JButton RightParen;
    private JButton xVariable;
    private JButton equalSign;

    // functionality buttons, clear, delete, = or solve
    private JButton cButton;
    private JButton delButton;
    private JButton equalButton;

    // modes
    private JButton unitConvButton;
    private JButton graphButton;
    private JButton equationButton;
    private JButton basicButton;

    /**
     * Returns the root swing component embed in the JFrame layout
     * @return JComponent root
     */
    public JComponent getComponent() {
        return  root;
    }

    /**
     * Connects all buttons to the controller and assigns them an
     * ActionCommand token. When the button is pressed, the controller
     * will gets the mapped string.
     * @param controller CalculatorController handles user input
     */
    public void connect(CalculatorController controller) {
        // initiates Map that will map button to string
        Map<JButton,String> m = new HashMap<>();

        // digits
        m.put(a0Button,"0");
        m.put(a1Button,"1");
        m.put(a2Button,"2");
        m.put(a3Button,"3");
        m.put(a4Button,"4");
        m.put(a5Button,"5");
        m.put(a6Button,"6");
        m.put(a7Button,"7");
        m.put(a8Button,"8");
        m.put(a9Button,"9");

        // operators
        m.put(addButton,"+");
        m.put(minButton,"-");
        m.put(multiButton,"*");
        m.put(divButton,"/");
        m.put(powButton,"^");
        m.put(sqrtButton, "√");
        m.put(plusNegButton,"-");

        // functionality clear, delete, solve or "="
        m.put(cButton,"C");
        m.put(delButton,"DEL");
        m.put(equalButton,"=");

        // parentheses and variable x
        m.put(LeftParen, "(");
        m.put(RightParen, ")");
        m.put(xVariable, "x");

        // symbolic "=" for equationMode
        m.put(equalSign, "=");

        // modes
        m.put(unitConvButton,"MODE_UNIT");
        m.put(graphButton,"MODE_GRAPH");
        m.put(equationButton,"MODE_EQ");
        m.put(basicButton, "MODE_BASIC");

        // Creates listener allows controller to listen to buttons
        ActionListener l = e -> controller.handleKey(((JButton)e.getSource()).getActionCommand());

        // maps action command to button
        m.forEach((btn, tok) -> {
            btn.setActionCommand(tok);
            btn.addActionListener(l);
        });

        // default keypad mode
        setBasicMode();
    }

    /**
     * Sets the keypad for BASIC calculator mode, hides
     * symbolic buttons (ie x and symbolic "=")
     */
    public void setBasicMode() {
        // hides x and symbolic "="
        xVariable.setVisible(false);
        equalSign.setVisible(false);

        sqrtButton.setVisible(true);
        LeftParen.setVisible(true);
        RightParen.setVisible(true);
        addButton.setVisible(true);
        minButton.setVisible(true);
        multiButton.setVisible(true);
        divButton.setVisible(true);
        powButton.setVisible(true);
        sqrtButton.setVisible(true);
        plusNegButton.setVisible(true);


        // equal buttons solves
        equalButton.setText("=");
        equalButton.setActionCommand("=");
    }

    /**
     * Sets the keypad for GRAPHING mode, makes x visible
     * and symbolic "=" not visible.
     */
    public void setGraphMode() {
        xVariable.setVisible(true);
        equalSign.setVisible(false);

        sqrtButton.setVisible(false);
        LeftParen.setVisible(true);
        RightParen.setVisible(true);
        addButton.setVisible(true);
        minButton.setVisible(true);
        multiButton.setVisible(true);
        divButton.setVisible(true);
        powButton.setVisible(true);
        plusNegButton.setVisible(true);

        equalButton.setText("GRAPH");
        equalButton.setActionCommand("=");
    }

    public void setUnitMode() {
        xVariable.setVisible(false);
        equalSign.setVisible(false);

        sqrtButton.setVisible(false);
        LeftParen.setVisible(false);
        RightParen.setVisible(false);
        addButton.setVisible(false);
        minButton.setVisible(false);
        multiButton.setVisible(false);
        divButton.setVisible(false);
        powButton.setVisible(false);
        plusNegButton.setVisible(false);
    }

    /**
     * Sets keypad for EQUATION mode. Enables x and symbolic "="
     * so users can input quadratics
     */
    public void setEquationMode() {
        xVariable.setVisible(true);
        equalSign.setVisible(true);

        sqrtButton.setVisible(false);
        LeftParen.setVisible(true);
        RightParen.setVisible(true);
        addButton.setVisible(true);
        minButton.setVisible(true);
        multiButton.setVisible(true);
        divButton.setVisible(true);
        powButton.setVisible(true);
        plusNegButton.setVisible(true);

        equalButton.setText("Solve");
        equalButton.setActionCommand("SOLVE_EQ");
    }

    /**
     * Model notifies KeypadView of changes
     */
    @Override
    public void modelChanged(EquationModel m) {}
}
