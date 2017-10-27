package gui;

import javax.swing.*;
import java.awt.*;

public class BaseService extends JComponent {

	private static final long serialVersionUID = 1L;
	BaseDevice myDevice;
  Descriptor myDescriptor;

  public BaseService() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public BaseService(BaseDevice theDevice, Descriptor theDescriptor) {
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

  public Descriptor getMyDescriptor() {
    return myDescriptor;
  }

  protected void paintComponent(Graphics g) {
    if (myDescriptor!=null) {
      myDescriptor.getMyRenderer().render(g,0,0,10,10);
    }
    else {
      super.paintComponent( g);
    }
  }


}