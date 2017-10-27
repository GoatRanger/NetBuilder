package gui;

import java.awt.*;
import java.awt.geom.*;

public class TurbineRenderer extends BasicRenderer {

  double[] dx = {0.0, 1.00, 1.00, 0.00};
  double[] dy = {0.10, 0.30, 0.70, 0.90};

  public TurbineRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    Color previousColor = g2.getColor();
    myGradientPaint = new GradientPaint(0,0,Color.red,
                                        wx(1.0),wy(1.0),Color.white);
    g2.setPaint(myGradientPaint);
    int[] ix = {ax(dx[0]), ax(dx[1]), ax(dx[2]), ax(dx[3]) };
    int[] iy = {ay(dy[0]), ay(dy[1]), ay(dy[2]), ay(dy[3]) };
    Polygon outline = new Polygon(ix,iy,4);
    g2.fill(new Area(outline));
    g2.setColor(previousColor);
  }

}