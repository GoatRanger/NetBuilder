package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import network.Network;

import event.EventManager;

public class NetBuilderFrame extends JFrame {
 
	private static final long serialVersionUID = -3392450544906117269L;
	JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  MasterMenu masterMenu = new MasterMenu(this);
  BuilderPanel builderPanel = new BuilderPanel(this);
  ControlPanel controlPanel = new ControlPanel();
  SimControlPanel simControls = new SimControlPanel(this);
  StatusBar statusBar = new StatusBar();
  ScorePanel scorePanel = new ScorePanel();
  MessagePanel messagePanel = new MessagePanel();
  CostCounter costCounter = new CostCounter();
  EventManager eventManager;
  Network network;

  //Construct the frame
  public NetBuilderFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception  {
    setIconImage(Toolkit.getDefaultToolkit().createImage(NetBuilderFrame.class.getResource("crestmiddle.gif")));
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(1000, 700));
    this.setTitle("Network Simulator");
    statusBar.setText("Welcome to the network simulator");
    this.setJMenuBar(masterMenu);
    contentPane.add(builderPanel, BorderLayout.CENTER);
    contentPane.add(controlPanel, BorderLayout.EAST);
    contentPane.add(messagePanel, BorderLayout.SOUTH);
    //contentPane.add(scorePanel, BorderLayout.SOUTH);
    //contentPane.add(statusBar, BorderLayout.SOUTH);
    EventManager.getInstance().addListener(simControls);
    openScenario();
  }

  public void addCost(double newCost) {
    costCounter.addCost(newCost);
  }

  public void startUp() {
    builderPanel.startUp();
    scorePanel.addToScoringPanel(costCounter);
    masterMenu.showMenuConfiguration();
    controlPanel.add(simControls,BorderLayout.CENTER);
  }

  public void openScenario() {
    startUp();
  }
  
  public void reset() {
    builderPanel.reset();
    network.removeAll();
  }

  public void exitOnClose() {
    System.exit(0);
  }
  
  public void setNetwork(Network net) {
  	network = net;
    simControls.setNetwork(net);
  }
  
  public Network getNetwork() {
    System.err.println("************Getting network");
  	return network;
  }
  
  public BuilderPanel getBuilderPanel() {
    System.err.println("************Getting panel");
    return builderPanel;
  }
  
  public void setBuilderPanel(BuilderPanel panel) {
    contentPane.remove(builderPanel);
    builderPanel = panel;
    contentPane.add(builderPanel, BorderLayout.CENTER);
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      exitOnClose();
    }
  }

  public StatusBar getStatusBar() {
    return statusBar;
  }

}