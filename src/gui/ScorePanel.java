package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class ScorePanel extends JPanel {
  
	private static final long serialVersionUID = 1L;
	private BorderLayout borderLayout1 = new BorderLayout();
  private JLabel headerLabel = new JLabel();
  CostCounter myCostCounter = null;
  Box scoreBox;
  JPanel infoPanel = new JPanel();
  TitledBorder titledBorder1;
  JPanel scoringPanel = new JPanel();
  TitledBorder titledBorder2;

  public ScorePanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    scoreBox = Box.createHorizontalBox();
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151)),"Information");
    titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151)),"Scoring");
    headerLabel.setBackground(Color.blue);
    headerLabel.setForeground(Color.white);
    headerLabel.setOpaque(true);
    headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    headerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    headerLabel.setText("Scenario");
    this.setLayout(borderLayout1);
    infoPanel.setBackground(new Color(180, 180, 255));
    infoPanel.setBorder(titledBorder1);
    scoringPanel.setBackground(new Color(180, 180, 255));
    scoringPanel.setBorder(titledBorder2);
    this.add(headerLabel, BorderLayout.NORTH);
    this.add(scoreBox, BorderLayout.CENTER);
    scoreBox.add(infoPanel, null);
    scoreBox.add(scoringPanel, null);
  }

  public void addToScoringPanel(JComponent newComponent) {
    scoringPanel.add(newComponent);
  }

  public void addToInformationPanel(JComponent newComponent) {
    infoPanel.add(newComponent);
  }

  public void setupCostCounter() {
    myCostCounter = new CostCounter();
    infoPanel.add(myCostCounter);
  }
}