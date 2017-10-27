package gui;

import java.awt.*;
import javax.swing.*;

public class CostCounter extends JPanel {
 
	private static final long serialVersionUID = 1L;
	private BorderLayout borderLayout1 = new BorderLayout();
  private JLabel costLabel = new JLabel();
  private JTextField jTextField1 = new JTextField();
  double myCost = 0.0;

  public CostCounter() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    costLabel.setBackground(SystemColor.control);
    costLabel.setText("Cost:  $$");
    this.setLayout(borderLayout1);
    jTextField1.setMinimumSize(new Dimension(120, 21));
    jTextField1.setEditable(false);
    jTextField1.setText("0.00");
    jTextField1.setHorizontalAlignment(SwingConstants.RIGHT);
    this.add(costLabel, BorderLayout.WEST);
    this.add(jTextField1, BorderLayout.CENTER);
  }

  public void addCost(double newCost) {
    myCost = myCost + newCost;
    jTextField1.setText(Double.toString(myCost));
    this.updateUI();
  }


}