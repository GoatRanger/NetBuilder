package gui;

import javax.swing.*;
import java.awt.*;

public class BasePerson extends JComponent {

	private static final long serialVersionUID = 1L;
	BaseDevice myDevice;
  BasicRenderer myRenderer;
  Descriptor myDescriptor;

  public BasePerson() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public BasePerson(BaseDevice theDevice, Descriptor theDescriptor) {
    myDevice = theDevice;
    myDescriptor = theDescriptor;
    this.setToolTipText(myDescriptor.getShortDescription());
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setSize(10,10);
  }

  public void setMyDevice(BaseDevice theDevice) {
    myDevice = theDevice;
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (myDescriptor!=null) {
      myDescriptor.getMyRenderer().render(g,0,0,10,10);
    }
    else {
      //super.paintComponent(g);
    }
  }

  public Descriptor getMyDescriptor() {
    return myDescriptor;
  }

}