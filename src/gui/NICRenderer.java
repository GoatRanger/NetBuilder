package gui;

import java.awt.*;

public class NICRenderer extends BasicRenderer {

  public NICRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    g.setColor(Color.green);
    g.fillRect(ax(0.1),ay(0.3),wx(0.8),wy(0.4));
    g.setColor(Color.yellow);
    g.fillRect(ax(0.1),ay(0.7),wx(0.5),wy(0.1));
  }

}
