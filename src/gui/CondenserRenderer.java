package gui;

import java.awt.*;

public class CondenserRenderer extends BasicRenderer {

  public CondenserRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    Color previousColor = g2.getColor();
    myGradientPaint = new GradientPaint(ax(0.5),ay(0.0),Color.white,
                                        wx(0.5),wy(1.0),Color.blue);
    g2.setPaint(myGradientPaint);
    g2.fillOval(ax(0.0),ay(0.2),wx(1.0),wy(0.6));
    g2.setColor(previousColor);
  }

}