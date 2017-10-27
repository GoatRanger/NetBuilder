package gui;

import java.awt.*;

public class MailRenderer extends BasicRenderer {

  Color offWhite = new Color(245,245,245);
  Color brown = new Color(180,125,0);

  public MailRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    g.setColor(offWhite);
    g.fillRect(ax(0.1),ay(0.3),wx(0.8),wy(0.4));
    g.setColor(brown);
    g.drawRect(ax(0.1),ay(0.3),wx(0.8),wy(0.4));
    g.drawLine(ax(0.1),ay(0.3),ax(0.9),wy(0.7));
    g.drawLine(ax(0.9),ay(0.3),ax(0.1),wy(0.7));
  }

}
