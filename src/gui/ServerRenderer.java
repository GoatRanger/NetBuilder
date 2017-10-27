package gui;

import java.awt.*;

import javax.swing.ImageIcon;

public class ServerRenderer extends BasicRenderer {

  ImageIcon myImage;

  public ServerRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
    myImage = new ImageIcon(this.getClass().getResource("server.png"));
  }

  protected void drawMe(Graphics g) {
      g.drawImage(myImage.getImage(),0,0,wx(1),wy(1), null);
  }

}
