package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import utils.FileManager;

import network.Network;

import event.EventManager;

public class MasterMenu extends JMenuBar {
 
	private static final long serialVersionUID = 1L;
	NetBuilderFrame myFrame;
  JMenu menuFile = new JMenu();
  JMenuItem menuFileSave = new JMenuItem();
  JMenuItem menuFileOpen = new JMenuItem();
  JMenuItem menuFileOpenScenario = new JMenuItem();
  JMenuItem menuFileExit = new JMenuItem();
  JMenu helpMenu = new JMenu();
  JMenuItem helpMenuAbout = new JMenuItem();
  JMenu menuConfiguration = new JMenu();
  JMenuItem menuConfigurationOpen = new JMenuItem();
  JMenuItem menuConfigurationSave = new JMenuItem();
  JMenu menuSimulation = new JMenu();
  JMenuItem menuSimulationParameters = new JMenuItem();
  JMenuItem menuSimulationReset = new JMenuItem();
  JMenuItem menuSimulationControls = new JMenuItem();
  JMenuItem menuFileNew = new JMenuItem();
  JCheckBoxMenuItem menuConfigurationVerbose = new JCheckBoxMenuItem();

  public MasterMenu(NetBuilderFrame nbFrame) {
    myFrame = nbFrame;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setBackground(SystemColor.control);
    menuSimulation.setBackground(SystemColor.control);
    menuSimulationControls.setBackground(SystemColor.control);
    menuSimulationParameters.setBackground(SystemColor.control);
    menuSimulationReset.setBackground(SystemColor.control);
    this.add(menuFile);
    menuFile.setBackground(SystemColor.control);
    menuFile.setText("File");
    menuFileNew.setText("New");
//    menuFileNew.setBackground(SystemColor.control);
    menuFileNew.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menuFileNew_actionPerformed(e);
      }
    });
    menuFile.add(menuFileNew);
	menuFileOpen.setText("Open");
	menuFileOpen.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			//FileManager.saveNetwork(myFrame.network);
			System.out.println(myFrame.builderPanel.foundation.toString());
			
			// Open a saved NetworkGUI. If there isn't a network saved, the FileManager returns null
			NetBuilderFrame newFrame = FileManager.openNetworkGUI();
			if (newFrame != null) {
				myFrame = newFrame;
			} else {
				JOptionPane.showMessageDialog(null,
						"You don't have a saved network.");
			}
			myFrame.builderPanel.validate();
			System.out.println(myFrame.builderPanel.foundation.toString());
			
			myFrame.repaint();
		}
	});
    
	menuFile.add(menuFileOpen);
  menuFileSave.setText("Save");
  menuFileSave.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			//FileManager.saveNetwork(myFrame.network);	
			FileManager.saveNetworkGUI(myFrame);
		}
  });
    
    menuFile.add(menuFileSave);
//    menuFile.add(menuFileOpenScenario);
//    menuFileOpenScenario.setText("Open Scenario");
//    menuFileOpenScenario.setBackground(SystemColor.control);
//    menuFileOpenScenario.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        menuFileOpenScenario_actionPerformed(e);
//      }
//    });
    menuFile.add(menuFileExit);
    menuFileExit.setText("Exit");
//    menuFileExit.setBackground(SystemColor.control);
    menuFileExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menuFileExit_actionPerformed(e);
      }
    });
    this.add(menuConfiguration);
    menuConfiguration.setVisible(false);
    menuConfiguration.setBackground(SystemColor.control);
    menuConfiguration.setText("Configuration");
//    menuConfiguration.add(menuConfigurationOpen);
    menuConfigurationOpen.setText("Open");
//    menuConfigurationOpen.setBackground(SystemColor.control);
    menuConfigurationOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menuConfigurationOpen_actionPerformed(e);
      }
    });
//    menuConfiguration.add(menuConfigurationSave);
    menuConfigurationSave.setText("Save");
//    menuConfigurationSave.setBackground(SystemColor.control);
    menuConfigurationSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menuConfigurationSave_actionPerformed(e);
      }
    });
//    menuConfigurationVerbose.setBackground(SystemColor.control);
    menuConfigurationVerbose.setText("Verbose");
    menuConfigurationVerbose.setSelected(false);
    menuConfigurationVerbose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        EventManager.setVerbose(menuConfigurationVerbose.isSelected());
      }
    });
    menuConfiguration.add(menuConfigurationVerbose);
//    this.add(menuSimulation);
    menuSimulation.setVisible(false);
    menuSimulation.setText("Simulation");
    menuSimulation.add(menuSimulationControls);
    menuSimulationControls.setText("Controls");
    menuSimulation.add(menuSimulationParameters);
    menuSimulationParameters.setText("Parameters");
    menuSimulation.add(menuSimulationReset);
    menuSimulationReset.setText("Reset");
    this.add(helpMenu);
    helpMenu.setText("Help");
    helpMenu.add(helpMenuAbout);
    helpMenu.setBackground(SystemColor.control);
//    helpMenuAbout.setBackground(SystemColor.control);
    helpMenuAbout.setText("About");
    helpMenuAbout.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        helpMenuAbout_actionPerformed(e);
      }
    });
  }

  void menuFileOpenScenario_actionPerformed(ActionEvent e) {
    myFrame.openScenario();
  }

  void menuFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }
  void menuFileNew_actionPerformed(ActionEvent e) {
    myFrame.reset();
    myFrame.repaint();
  }
  void helpMenuAbout_actionPerformed(ActionEvent e) {
    NetBuilderFrame_AboutBox dlg = new NetBuilderFrame_AboutBox(myFrame);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = myFrame.getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y + 40);
    dlg.setModal(true);
    dlg.setVisible(true);
  }

  public void showMenuConfiguration() {
    menuConfiguration.setVisible(true);
    menuSimulation.setVisible(true);
  }

  void menuConfigurationOpen_actionPerformed(ActionEvent e) {
    try {
      XMLDecoder decoder = new XMLDecoder(
        new FileInputStream("save.txt"));
       
      HashMap<?, ?> map = (HashMap<?, ?>)decoder.readObject();
      Network net = (Network)map.get("network");
      Foundation fnd = (Foundation)map.get("foundation");
      decoder.close();
      myFrame.network = net;
      myFrame.builderPanel.foundation = fnd;
      myFrame.repaint();
    } catch (FileNotFoundException fnfe) {}
  }

  void menuConfigurationSave_actionPerformed(ActionEvent e) {
    try {
          String filename = "save.txt";
          XMLEncoder encoder = 
       new XMLEncoder(new  FileOutputStream(filename));
       HashMap<String, Object> map = new HashMap<String, Object>();
       map.put("network",myFrame.network);
       map.put("foundation",myFrame.builderPanel.foundation);
       encoder.writeObject(map);
       encoder.close();
    } catch (FileNotFoundException fe) {}
  }

}