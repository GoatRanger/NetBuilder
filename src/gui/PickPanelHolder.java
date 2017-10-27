package gui;

import javax.swing.*;
import java.awt.*;

public class PickPanelHolder extends JPanel {

	private static final long serialVersionUID = 1L;
	Box pickHolder = Box.createVerticalBox();
  private BorderLayout borderLayout1 = new BorderLayout();
  JLabel paletteLabel = new JLabel();

  public PickPanelHolder() {
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    paletteLabel.setText("Palettes");
    paletteLabel.setOpaque(true);
    paletteLabel.setHorizontalAlignment(SwingConstants.CENTER);
    paletteLabel.setBackground(new Color(64,128,64));
    paletteLabel.setForeground(Color.white);
    this.add(paletteLabel, BorderLayout.NORTH);
    this.add(pickHolder, BorderLayout.CENTER);
  }

  public PickPanel createPickPanel() {
    PickPanel newPickPanel = new PickPanel();
    pickHolder.add(newPickPanel);
    this.updateUI();
    return newPickPanel;
  }

}