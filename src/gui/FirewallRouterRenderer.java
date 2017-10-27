/*
 * Created on Sep 14, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gui;

import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * @author Karl
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FirewallRouterRenderer extends BasicRenderer {
  ImageIcon myImage;
  public FirewallRouterRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
    myImage = new ImageIcon(this.getClass().getResource("linksys-router-firewall.png"));
  }

  protected void drawMe(Graphics g) {
//    g.setColor(SystemColor.controlShadow);
//    g.fillRect(0,0,wx(1),wy(1));
    g.drawImage(myImage.getImage(),ax(0.1),ay(0.1),wx(0.8),wy(0.75),null);
  }
}
