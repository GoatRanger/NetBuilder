package gui;

import java.awt.*;

public class PersonRenderer extends BasicRenderer {

  Color myColor = Color.black;

  public PersonRenderer(Color theColor) {
    myColor = theColor;
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  protected void drawMe(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    g2.setColor(myColor);
    g2.fillOval(ax(0.4),ay(0.1),wx(0.2),wy(0.2));
    g2.setStroke(new BasicStroke(2.0f));
    g2.drawLine(ax(0.5),ay(0.3),ax(0.5),ay(0.6));
    g2.drawLine(ax(0.2),ay(0.3),ax(0.5),ay(0.4));
    g2.drawLine(ax(0.8),ay(0.3),ax(0.5),ay(0.4));
    g2.drawLine(ax(0.2),ay(0.9),ax(0.5),ay(0.6));
    g2.drawLine(ax(0.8),ay(0.9),ax(0.5),ay(0.6));
  }

}

