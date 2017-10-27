package gui;

import java.awt.*;

public class BoilerRenderer extends BasicRenderer {

  public BoilerRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    Color previousColor = g2.getColor();
    myGradientPaint = new GradientPaint(ax(0.0),ay(0.5),Color.lightGray,
                                        ax(1.0),ay(0.5),Color.white);
    g2.setPaint(myGradientPaint);
//    int[] ix = {ax(dx[0]), ax(dx[1]), ax(dx[2]), ax(dx[3]) };
//    int[] iy = {ay(dy[0]), ay(dy[1]), ay(dy[2]), ay(dy[3]) };
//    Polygon outline = new Polygon(ix,iy,4);
//    g2.fill(new Area(outline));
    g2.fillRoundRect(ax(0.10),ay(0.20),wx(0.80),wy(0.60),5,5);
    g2.setColor(previousColor);
  }

}


