package gui;

import java.awt.*;

import javax.swing.ImageIcon;

public class RouterRenderer extends BasicRenderer {
  ImageIcon myImage;
  public RouterRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
    myImage = new ImageIcon(this.getClass().getResource("linksys-router.png"));

  }

  protected void drawMe(Graphics g) {
    g.drawImage(myImage.getImage(),0,0,wx(0.9),wy(0.9),null);
  }

}

