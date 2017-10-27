package gui;

import java.awt.*;

import network.Network;

public class NetBuilder {
  boolean packFrame = false;

  NetBuilderFrame frame;
  public void construct() {
    frame = new NetBuilderFrame();
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }
  
  public void setNetworkModel(Network net) {
  	if (frame == null) {
  		return;
  	}
  	frame.setNetwork(net);
  }
}