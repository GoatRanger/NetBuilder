package gui;

import java.awt.*;

public class WebRenderer extends BasicRenderer {

  public WebRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    g.setColor(Color.white);
//    g.fillRect(ax(0.1),ay(0.1),wx(0.8),wy(0.8));
//    g.setColor(Color.gray);
    g.drawLine(ax(0.5),ay(0.2),ax(0.5),wy(0.8));
    g.drawLine(ax(0.8),ay(0.3),ax(0.2),wy(0.7));
    g.drawLine(ax(0.2),ay(0.3),ax(0.8),wy(0.7));

    g.drawLine(ax(0.5),ay(0.25),ax(0.75),wy(0.35));
    g.drawLine(ax(0.75),ay(0.35),ax(0.75),wy(0.65));
    g.drawLine(ax(0.75),ay(0.65),ax(0.5),wy(0.75));
    g.drawLine(ax(0.5),ay(0.75),ax(0.25),wy(0.65));
    g.drawLine(ax(0.25),ay(0.65),ax(0.25),wy(0.35));
    g.drawLine(ax(0.25),ay(0.35),ax(0.5),wy(0.25));

    g.drawLine(ax(0.5),ay(0.35),ax(0.65),wy(0.45));
    g.drawLine(ax(0.65),ay(0.45),ax(0.65),wy(0.55));
    g.drawLine(ax(0.65),ay(0.55),ax(0.5),wy(0.65));
    g.drawLine(ax(0.5),ay(0.65),ax(0.35),wy(0.55));
    g.drawLine(ax(0.35),ay(0.55),ax(0.35),wy(0.45));
    g.drawLine(ax(0.35),ay(0.45),ax(0.5),wy(0.35));
  }

}
