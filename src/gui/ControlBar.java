package gui;

import javax.swing.*;
import java.awt.event.*;

public class ControlBar extends JToolBar {
  /**
	 * 
	 */
	private static final long serialVersionUID = -5939598319377576148L;
	NetBuilderFrame myFrame;
  JButton jButton1 = new JButton();
  ImageIcon image1;
  
  // TODO Implement tabs
  //private JTabbedPane tabPane = new JTabbedPane();
  JToolBar workStationBar = new JToolBar();

  public ControlBar(NetBuilderFrame nbFrame) {
    myFrame = nbFrame;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    image1 = new ImageIcon(NetBuilderFrame.class.getResource("openFile.gif"));
    jButton1.setIcon(image1);
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton1.setToolTipText("Open File");
    this.add(jButton1);
//    this.add(tabPane, null);
//    tabPane.addTab("Test",new JButton("Button"));
  }

  void jButton1_actionPerformed(ActionEvent e) {
    myFrame.openScenario();
  }

}