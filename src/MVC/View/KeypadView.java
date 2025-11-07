package MVC.View;

import MVC.Controller.CalculatorController;
import MVC.Model.EquationModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

public class KeypadView extends View {
    private JPanel root;

    private JButton unitConvButton;
    private JButton a7Button;
    private JButton a4Button;
    private JButton a1Button;
    private JButton plusNegButton;
    private JButton graphButton;
    private JButton a5Button;
    private JButton a8Button;
    private JButton a2Button;
    private JButton a0Button;
    private JButton equationButton;
    private JButton a9Button;
    private JButton a6Button;
    private JButton a3Button;
    private JButton powButton;
    private JButton divButton;
    private JButton minButton;
    private JButton multiButton;
    private JButton addButton;
    private JButton equalButton;
    private JButton cButton;
    private JButton delButton;

    public JComponent getComponent() {
        return  root;
    }

    public void connect(CalculatorController controller) {
        Map<JButton,String> m = new LinkedHashMap<>();
        m.put(a0Button,"0"); m.put(a1Button,"1"); m.put(a2Button,"2"); m.put(a3Button,"3"); m.put(a4Button,"4");
        m.put(a5Button,"5"); m.put(a6Button,"6"); m.put(a7Button,"7"); m.put(a8Button,"8"); m.put(a9Button,"9");
        m.put(addButton,"+"); m.put(minButton,"-"); m.put(multiButton,"*"); m.put(divButton,"/"); m.put(powButton,"^");
        m.put(equalButton,"="); m.put(cButton,"C"); m.put(delButton,"DEL"); m.put(plusNegButton,"NEG");
        m.put(equationButton,"MODE_EQ"); m.put(graphButton,"MODE_GRAPH"); m.put(unitConvButton,"MODE_UNIT");

        ActionListener l = e -> controller.actionPerformed(((JButton)e.getSource()).getActionCommand());

        m.forEach((btn, tok) -> {
            btn.setActionCommand(tok);
            btn.addActionListener(l);
        });
    }

    @Override
    public void update(EquationModel m) {
        // refresh keypad state if needed
    }

    @Override
    public void render() {
        // draw UI using Swing later
    }
}
