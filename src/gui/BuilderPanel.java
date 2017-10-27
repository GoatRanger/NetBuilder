package gui;

import java.awt.*;
import javax.swing.*;

import network.NetworkComponent;

public class BuilderPanel extends JPanel {
  /**
	 * 
	 */
	private static final long serialVersionUID = 4599301734971877610L;
	BorderLayout borderLayout1 = new BorderLayout();
  PickTabs pickTabs = new PickTabs();
  DisplayPanel displayPanel = new DisplayPanel();
  NetBuilderFrame myNetBuilderFrame = null;
  Foundation foundation;
  Ground ground;

  public BuilderPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public BuilderPanel(NetBuilderFrame nbf) {
    myNetBuilderFrame = nbf;
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(pickTabs, BorderLayout.NORTH);
    this.add(displayPanel, BorderLayout.CENTER);
  }
  
  public void reset() {
    Component[] thingList = foundation.getComponents();
    for (int i=0; i<thingList.length;i++) {
      if (thingList[i] instanceof BaseDevice) {
        ((BaseDevice)thingList[i]).selfDelete();
      }
    }
    NetworkComponent.resetIDs();
  }

  
  public void startUp() {
    Ground ground = new Ground(myNetBuilderFrame);
    displayPanel.addGround(ground,1350,790);
    displayPanel.updateUI();
    this.ground = ground;
    this.foundation = new Foundation(ground);
    //this.foundation = foundationOne;
    ground.add(foundation);
    foundation.setSize(1330,770);
    foundation.setLocation(10,10);

//    Foundation foundationTwo = new Foundation(ground);
//    ground.add(foundationTwo);
//    foundationTwo.setSize(250,300);
//    foundationTwo.setLocation(320, 25);

    pickTabs.addDescriptor(new Descriptor(Descriptor.DEVICE_TYPE,
                                          Descriptor.DESKTOP_DEVICE,
                                          "Desktop",
                                          1500.00));
    pickTabs.addDescriptor(new Descriptor(Descriptor.DEVICE_TYPE,
                                          Descriptor.SERVER_DEVICE,
                                          "Server",
                                          5000.00));
    pickTabs.addDescriptor(new Descriptor(Descriptor.DEVICE_TYPE,
                                          Descriptor.ROUTER_DEVICE,
                                          "Router",
                                          500.00));
    pickTabs.addDescriptor(new Descriptor(Descriptor.DEVICE_TYPE,
                                          Descriptor.FIREWALL_DEVICE,
                                          "FirewalledRouter", 800.00));
    pickTabs.addDescriptor(new Descriptor(Descriptor.DEVICE_TYPE,
                                          Descriptor.HUB_DEVICE,
                                          "Hub", 12.00));
    pickTabs.addDescriptor(new Descriptor(Descriptor.DEMAND_TYPE,
                                      Descriptor.ADMIN_DEMAND,
                                      "Average",
                                      100.00));
    pickTabs.addDescriptor(new Descriptor(Descriptor.DEMAND_TYPE,
                                    Descriptor.GRAPHIC_DEMAND,
                                    "Professional",
                                    50.00));
    pickTabs.addDescriptor(new Descriptor(Descriptor.DEMAND_TYPE,
                                    Descriptor.PROGRAM_DEMAND,
                                    "Average",
                                    75.00));
    pickTabs.addDescriptor(new Descriptor(Descriptor.DEMAND_TYPE,
                                    Descriptor.NEWUSER_DEMAND,
                                    "Word Processing",
                                    20.00));
//    pickTabs.addDescriptor(new Descriptor(Descriptor.SERVICE_TYPE,
//                                      Descriptor.MAIL_SERVICE,
//                                      "PushyPost",
//                                      1400.00));
//    pickTabs.addDescriptor(new Descriptor(Descriptor.SERVICE_TYPE,
//                                    Descriptor.WEB_SERVICE,
//                                    "Arachne Server",
//                                    25.00));
//    pickTabs.addDescriptor(new Descriptor(Descriptor.SERVICE_TYPE,
//                                    Descriptor.DATA_SERVICE,
//                                    "Monocle",
//                                    125.00));
//    pickTabs.addDescriptor(new Descriptor(Descriptor.SERVICE_TYPE,
//                                    Descriptor.FILE_SERVICE,
//                                    "Cabinet Filer",
//                                    40.00));
//    pickTabs.addDescriptor(new Descriptor(Descriptor.DEVICE_TYPE,
//                                    Descriptor.GUIDE_DEVICE,
//                                    "Connector Guide",
//                                    5.00));
//    pickTabs.addDescriptor(new Descriptor(Descriptor.CONNECTOR_TYPE,
//                                    Descriptor.NICTEN_CONNECTOR,
//                                    "10Port10MBps",
//                                    15.00));
    pickTabs.addDescriptor(new Descriptor(Descriptor.CONNECTOR_TYPE,
                                    Descriptor.NICHUNDRED_CONNECTOR,
                                    "10Port100MBps",
                                    25.00));
//    pickTabs.addDescriptor(new Descriptor(Descriptor.CONNECTOR_TYPE,
//                                          Descriptor.NICGIG_CONNECTOR,
//                                          "10Port1GBps",
//                                          50.00));
  }

  /**
   * @return Returns the foundation.
   */
  public Foundation getFoundation() {
    return foundation;
  }

  /**
   * @param foundation The foundation to set.
   */
  public void setFoundation(Foundation foundation) {
    ground.remove(this.foundation);
    this.foundation = foundation;
    ground.add(this.foundation);
  }

  /**
   * @return Returns the ground.
   */
  public Ground getGround() {
    return ground;
  }

  /**
   * @param ground The ground to set.
   */
  public void setGround(Ground ground) {
    ground.add(foundation);
    displayPanel.remove(this.ground);
    this.ground = ground;    
    displayPanel.addGround(ground,1350,790);
    displayPanel.updateUI();
    
  }

}