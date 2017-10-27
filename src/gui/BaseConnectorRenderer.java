package gui;

import java.awt.*;

public class BaseConnectorRenderer extends BasicRenderer {

  public BaseConnectorRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    g.setColor(Color.yellow);
    g.fillOval(ax(0.1),ay(0.1),wx(0.8),wy(0.8));
    g.setColor(Color.black);
    g.drawOval(ax(0.2),ay(0.2),wx(0.6),wy(0.6));
  }

}
