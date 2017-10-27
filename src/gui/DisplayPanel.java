package gui;

import java.awt.*;
import javax.swing.*;

public class DisplayPanel extends JPanel {
  /**
	 * 
	 */
	private static final long serialVersionUID = -1946712980751848301L;
	BorderLayout borderLayout1 = new BorderLayout();
  JLabel displayHeader = new JLabel();
  JScrollPane displayScrollPane = new JScrollPane();
  JPanel scrollField = new JPanel();

  public DisplayPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    this.setBackground(SystemColor.desktop);
    this.setLayout(borderLayout1);
    displayHeader.setText("Environment");
    displayHeader.setOpaque(true);
    displayHeader.setHorizontalAlignment(SwingConstants.CENTER);
    displayHeader.setBackground(SystemColor.info);
    displayHeader.setForeground(SystemColor.infoText);
    this.add(displayHeader, BorderLayout.NORTH);
    scrollField.setBackground(SystemColor.window);
    scrollField.setLayout(null);
 //   scrollField.setLayout(new FlowLayout());
    displayScrollPane.setViewportView(scrollField);
    this.add(displayScrollPane, BorderLayout.CENTER);
  }

  public void addGround(Ground ground,int w, int h) {
    Dimension dim = new Dimension(w,h);
    scrollField.setPreferredSize(dim);
    scrollField.add(ground);
    ground.setSize(dim);
//    ground.setLocation(0,0);
//    displayScrollPane.getViewport().setBackground(Color.magenta);
  }

}

