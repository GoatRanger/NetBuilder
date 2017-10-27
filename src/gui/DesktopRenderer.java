package gui;

import java.awt.*;

import javax.swing.ImageIcon;

public class DesktopRenderer extends BasicRenderer {

  ImageIcon myImage;
  
  public DesktopRenderer() {
    super();
    myImage = new ImageIcon(this.getClass().getResource("computer.png"));
  }

  protected void drawMe(Graphics g) {
     g.drawImage(myImage.getImage(),ax(0),ay(0),wx(0.9),wy(0.9),null);
  }

}