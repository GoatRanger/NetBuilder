package gui;

import javax.swing.JPanel;

public class Ground extends JPanel {


	private static final long serialVersionUID = 1L;
	NetBuilderFrame myNBFrame;

  public Ground() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public Ground(NetBuilderFrame NBFrame) {
    myNBFrame = NBFrame;
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    //this.setBackground(new Color(128, 223, 92));
    this.setLayout(null);
    //this.setSize(700,350);
  }

  public NetBuilderFrame getMyNBFrame() {
    return myNBFrame;
  }

  public void addCost(double newCost) {
    if (myNBFrame!=null) {
      myNBFrame.addCost(newCost);
    }
  }
  

}