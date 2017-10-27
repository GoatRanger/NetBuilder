package gui;

import java.awt.*;

import javax.swing.ImageIcon;

public class NIChundredRenderer extends BasicRenderer {

  Color myColor = Color.orange;
  ImageIcon myImage;

  public NIChundredRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
    myImage = new ImageIcon(this.getClass().getResource("rj45.png"));

  }

  protected void drawMe(Graphics g) {
    g.drawImage(myImage.getImage(),ax(0.1),ay(0.1),wx(0.8),wy(0.8),null);
  }

}
