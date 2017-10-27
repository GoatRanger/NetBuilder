package gui;

import java.awt.*;

public class BasicRenderer {

  double renderWidth;
  double renderHeight;
  double imageScale;
  int minX, minY;
  GradientPaint myGradientPaint = null;
  Color myColor = null;

  public BasicRenderer() {
    renderWidth = 1.0;
    renderHeight = 1.0;
  }

  public void render(Graphics g,
                     int minimumX, int minimumY,
                     int maximumX, int maximumY) {
    reScale(minimumX,minimumY,maximumX,maximumY);
    drawMe(g);
  }

  private void reScale(int minimumX, int minimumY,
                       int maximumX, int maximumY) {
    // determine width and height to use
    minX = minimumX;
    minY = minimumY;
    double canvasWidth = (double)(maximumX-minimumX);
    double canvasHeight = (double)(maximumY-minimumY);
    imageScale = Math.min(canvasWidth/renderWidth,
                                 canvasHeight/renderHeight);
  }

  protected void drawMe(Graphics g) {
    // draw within that outline
    g.setColor(Color.blue);
    g.fillRect(ax(0.0),ay(0.0),wx(1.0),wy(1.0));
    g.setColor(Color.red);
    g.fillRect(ax(0.2), ay(0.2), wx(0.6), wy(0.6) );
  }

  protected int ax(double x) {
    return minX + (int)(imageScale*x+0.5);
  }

  protected int ay(double y) {
    return minY + (int)(imageScale*y+0.5);
  }

  protected int wx(double x) {
    return (int)(imageScale*x-0.5);
  }

  protected int wy(double y) {
    return (int)(imageScale*y-0.5);
  }

}