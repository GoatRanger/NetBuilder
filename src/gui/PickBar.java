package gui;

import javax.swing.*;


public class PickBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	JButton jButton1 = new JButton();
  JLabel jLabel1 = new JLabel();

  public PickBar() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    jButton1.setText("jButton1");
    this.setFloatable(false);
    jLabel1.setText("jLabel1");
    this.add(jButton1, null);
    this.add(jLabel1, null);
  }
}