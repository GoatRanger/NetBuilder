package gui;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class PickTabs extends JPanel {
  
	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane componentTabs = new JTabbedPane();
  JPanel devicesPanel = new JPanel();
  FlowLayout devicesLayout = new FlowLayout(FlowLayout.LEFT);
  JPanel connectorsPanel = new JPanel();
  FlowLayout connectorsLayout = new FlowLayout(FlowLayout.LEFT);
  JPanel servicesPanel = new JPanel();
  FlowLayout servicesLayout = new FlowLayout(FlowLayout.LEFT);
  JPanel demandsPanel = new JPanel();
  FlowLayout demandsLayout = new FlowLayout(FlowLayout.LEFT);
  PickCell pc = new PickCell();
  BorderLayout borderLayout = new BorderLayout();
  JLabel headerLabel = new JLabel();
  TreeMap<Integer, JPanel> tabs = new TreeMap<Integer, JPanel>();

  public PickTabs() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    this.setLayout(borderLayout);
    //headerLabel.setBackground(new Color(128,64,128));
    headerLabel.setBackground(SystemColor.info);
    //headerLabel.setForeground(Color.white);
    headerLabel.setForeground(SystemColor.infoText);
    headerLabel.setOpaque(true);
    headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    headerLabel.setText("Component Palettes");
    //componentTabs.setBackground(new Color(230, 150, 255));
    componentTabs.setBackground(SystemColor.control);
    componentTabs.setOpaque(true);
    this.add(headerLabel, BorderLayout.NORTH);
    this.add(componentTabs, BorderLayout.CENTER);
//    componentTabs.addTab("Devices",devicesPanel);
//    devicesPanel.setLayout(devicesLayout);
//    componentTabs.addTab("Connectors",connectorsPanel);
//    connectorsPanel.setLayout(connectorsLayout);
//    componentTabs.addTab("Services",servicesPanel);
//    servicesPanel.setLayout(servicesLayout);
//    componentTabs.add("Demands",demandsPanel);
//    demandsPanel.setLayout(demandsLayout);
//    pc.setDescriptor(new Descriptor(Descriptor.DEVICE_TYPE,
//                                    Descriptor.DESKTOP_DEVICE,
//                                    "MaxiMicro2002",
//                                    1500.00));
//    pc.setSize(new Dimension(32,32));
//    pc.setPreferredSize(new Dimension(32,32));
//    pc.setMinimumSize(new Dimension(21,21));
//    devicesPanel.add(pc,null);
  }

  public void addDescriptor(Descriptor newDescriptor) {
    // Is this a new type, or do we already have some of these?
    Integer key = new Integer(newDescriptor.getMyTypeID());
    if (!tabs.containsKey(key)) {
      // create new panel and store it in the mapping
      JPanel newPanel = new JPanel();
      //newPanel.setBackground(new Color(230, 150, 255));
      newPanel.setBackground(SystemColor.desktop);
      FlowLayout newFlowLayout = new FlowLayout(FlowLayout.LEFT);
      newPanel.setLayout(newFlowLayout);
      tabs.put(key,newPanel);
      componentTabs.add(
        Descriptor.getNameForTypeID(newDescriptor.getMyTypeID()),
        newPanel);
    }
    JPanel oldPanel = (JPanel)tabs.get(key);
    PickCell newPickCell = new PickCell();
    newPickCell.setDescriptor(newDescriptor);
    oldPanel.add(newPickCell,null);
  }
}