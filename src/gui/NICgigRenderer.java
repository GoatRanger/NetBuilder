package gui;

import java.awt.*;

public class NICgigRenderer extends BasicRenderer {

  Color myColor = Color.red;

  public NICgigRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    g.setColor(myColor);
    g.fillRect(ax(0.1),ay(0.3),wx(0.8),wy(0.4));
    g.setColor(Color.yellow);
    g.fillRect(ax(0.1),ay(0.7),wx(0.5),wy(0.1));
    g.drawLine(ax(0.2),ay(0.35),ax(0.2),ay(0.65));
    g.drawOval(ax(0.3),ay(0.35),wx(0.1),wy(0.3));
    g.drawOval(ax(0.5),ay(0.35),wx(0.1),wy(0.3));
    g.drawOval(ax(0.7),ay(0.35),wx(0.1),wy(0.3));
  }

}
