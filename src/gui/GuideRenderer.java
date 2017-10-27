package gui;

import java.awt.*;

public class GuideRenderer extends BasicRenderer {

  public GuideRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    g.setColor(Color.gray);
    g.fillRect(ax(0.0),ay(0.0),wx(1.0),wy(1.0));
    g.setColor(Color.black);
    g.drawRect(ax(0.05),ay(0.05),wx(0.9),wy(0.9));
  }

}
