package gui;

import java.awt.*;

public class DataRenderer extends BasicRenderer {

  Color lightBlue = new Color(192,192,255);
  Color darkBlue = new Color(128,128,192);

  public DataRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    g.setColor(darkBlue);
    g.fillOval(ax(0.25),ay(0.65),wx(0.5),wy(0.20));
    g.setColor(lightBlue);
    g.drawOval(ax(0.25),ay(0.65),wx(0.5),wy(0.20));
    g.setColor(darkBlue);
    g.fillRect(ax(0.25),ay(0.25),wx(0.5),wy(0.5));
    g.setColor(lightBlue);
    g.drawLine(ax(0.25),ay(0.25),ax(0.25),wy(0.75));
    g.drawLine(ax(0.75),ay(0.25),ax(0.75),wy(0.75));
    g.setColor(darkBlue);
    g.fillOval(ax(0.25),ay(0.15),wx(0.5),wy(0.20));
    g.setColor(lightBlue);
    g.drawOval(ax(0.25),ay(0.15),wx(0.5),wy(0.20));
  }

}
