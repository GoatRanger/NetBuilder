package gui;

import javax.swing.JLabel;
import java.awt.Color;

public class StatusBar extends JLabel {

	private static final long serialVersionUID = 1L;

	public StatusBar() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setBackground(Color.black);
    this.setOpaque(true);
    this.setForeground(Color.yellow);
  }

}