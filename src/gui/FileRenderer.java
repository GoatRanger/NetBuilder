package gui;

import java.awt.*;

public class FileRenderer extends BasicRenderer {

  Color manila = new Color(255,255,160);
  Color brown = new Color(180,125,0);

  public FileRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    g.setColor(manila);
    g.fillRect(ax(0.1),ay(0.3),wx(0.8),wy(0.5));
    g.setColor(brown);
    g.drawRect(ax(0.1),ay(0.3),wx(0.8),wy(0.5));
    g.setColor(manila);
    g.fillRect(ax(0.2),ay(0.2),wx(0.3),wy(0.2));
    g.setColor(brown);
    g.drawLine(ax(0.2),ay(0.3),ax(0.2),wy(0.2));
    g.drawLine(ax(0.2),ay(0.2),ax(0.5),wy(0.2));
    g.drawLine(ax(0.5),ay(0.2),ax(0.5),wy(0.3));
  }

}
