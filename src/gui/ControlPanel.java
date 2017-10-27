package gui;

import java.awt.*;
import javax.swing.*;

public class ControlPanel extends JPanel {
  /**
	 * 
	 */
	private static final long serialVersionUID = 5903623065233098143L;
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel headerLabel = new JLabel();

  public ControlPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    headerLabel.setBackground(new Color(192,64,64));
    headerLabel.setForeground(Color.white);
    headerLabel.setOpaque(true);
    headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    headerLabel.setText("Controls");
    this.setLayout(borderLayout1);
    this.setBackground(new Color(255,180,160));
    this.add(headerLabel, BorderLayout.NORTH);
    this.setSize(230,700);
    this.setPreferredSize(new Dimension(230,700));
  }
}