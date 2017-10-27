package gui;

import javax.swing.*;

import event.Event;
import event.MoveOnLinkEvent;

import network.NetworkEventListener;
import network.Sink;
import network.Source;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseLink extends JComponent implements NetworkEventListener {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Start point of the link
  BaseConnector linkEndOne = null;
  Point endOnePoint = new Point(0,0);
//  int X1 = 0;
//  int Y1 = 0;

  // End point of the link
  BaseConnector linkEndTwo = null;
  Point endTwoPoint = new Point(0,0);
//  int X2 = 0;
//  int Y2 = 0;

  // The upper-left (origin) point of the drawing area
  int VOX = 0;
  int VOY = 0;

  // The boundaries
  int Xmin = 0;
  int Xmax = 0;
  int Ymin = 0;
  int Ymax = 0;

  int LinkSize = 0;
  transient List<Event> activeEvents = new ArrayList<Event>();

  Color myColor = Color.white;

  final static BasicStroke stroke = new BasicStroke(2.0f);

  int connectionType;

  public BaseLink() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public BaseLink(int FirstX, int FirstY, int SecondX, int SecondY,Color newColor) {
  	endOnePoint = new Point(FirstX, FirstY);
//    X1 = FirstX;
//    Y1 = FirstY;
    endTwoPoint = new Point(SecondX, SecondY);
//    X2 = SecondX;
//    Y2 = SecondY;
    myColor = newColor;
    recalculateBounds();
  }

  public BaseLink(BaseConnector First, BaseConnector Second) {
    if (First.myDevice.getNetDevice() instanceof Sink) {
      setEnds(Second,First);
    } else {
        setEnds(First,Second);
    }
  }

  private void jbInit() throws Exception {
  }

  public void networkEvent(Event e) {
    synchronized (activeEvents) {
      if (!activeEvents.contains(e)) {
        activeEvents.add(e);
      }
    }
    linkEndOne.myDevice.getMyFoundation().getMyGround().repaint();
  }
  
  public void eventComplete(Event e) {
    if (activeEvents.contains(e)) {
      activeEvents.remove(e);
    }
    linkEndOne.myDevice.getMyFoundation().getMyGround().repaint();
  }
  
  public void setConnectionType(int cType) {
    connectionType = cType;
  }

  public synchronized void updateFromEnds() {
//    X1 = linkEndOne.getCenterX();
//    Y1 = linkEndOne.getCenterY();
    endOnePoint.setLocation(linkEndOne.getCenterX(), linkEndOne.getCenterY());
	endTwoPoint.setLocation(linkEndTwo.getCenterX(), linkEndTwo.getCenterY());
//    X2 = linkEndTwo.getCenterX();
//    Y2 = linkEndTwo.getCenterY();
    recalculateBounds();
  }

  public void updateFromEndPoints(int NewX1, int NewY1, int NewX2, int NewY2) {
    endOnePoint.setLocation(NewX1, NewY1);
	endTwoPoint.setLocation(NewX2, NewY2);
//  	X1 = NewX1;
//    Y1 = NewY1;
//    X2 = NewX2;
//    Y2 = NewY2;
    recalculateBounds();
  }

  void recalculateBounds() {
    // Determine the drawing boundaries of the link icon
	Xmin = (int)Math.min(endOnePoint.getX(),endTwoPoint.getX());
	Xmax = (int)Math.max(endOnePoint.getX(),endTwoPoint.getX());
	Ymin = (int)Math.min(endOnePoint.getY(),endTwoPoint.getY());
	Ymax = (int)Math.max(endOnePoint.getY(),endTwoPoint.getY());
//    Xmin = Math.min(X1,X2);
//    Ymin = Math.min(Y1,Y2);
//    Xmax = Math.max(X1,X2);
//    Ymax = Math.max(Y1,Y2);
    // Establish the virtual origin just outside the icon and set the
    // icon bounds to be several pixels wider all around - prevents clipping
    // of the packet icons when the link is flat or straight up
    VOX = Xmin-6;
    VOY = Ymin-6;
    setBounds(VOX,VOY,Math.abs(Xmax-Xmin)+12,Math.abs(Ymax-Ymin)+12);
    this.repaint();
  }

  protected synchronized void drawMe(Graphics g) {
    // Only draw the link if it hasn't been told by a LinkGroup to hide.
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(myColor);
    g2.setStroke(stroke);

    
    g2.drawLine((int)endOnePoint.getX()-VOX,(int)endOnePoint.getY()-VOY,
		(int)endTwoPoint.getX()-VOX,(int)endTwoPoint.getY()-VOY);
    double slope = 0;
    
    Point p1 = new Point((int)endOnePoint.getX()-VOX,(int)endOnePoint.getY()-VOY);
    Point p2 = new Point((int)endTwoPoint.getX()-VOX, (int)endTwoPoint.getY()-VOY);  
    double x1 = p1.getX();
    double y1 = p1.getY();
    double x2 = p2.getX();
    double y2 = p2.getY();
    double step=1;
    boolean isVertical = false;
    if (x2-x1 == 0) {
      isVertical = true;
      // handle vertical line
    } else {
      slope = (double)(y2-y1)/(x2-x1);
      }
    synchronized(activeEvents) {
      Iterator<Event> i=activeEvents.iterator();
      while (i.hasNext()) {
        Event evt = (Event)i.next();
        if (evt instanceof MoveOnLinkEvent) {
          MoveOnLinkEvent moveOnLink = (MoveOnLinkEvent)evt;
          double packetStep = MoveOnLinkEvent.STEPS_ON_LINK-moveOnLink.getStepsRemaining();
          double newX;
          double newY;
          // Now make sure we're moving the right way on the link
          if (moveOnLink.getPacket().getNextStop().equals(linkEndTwo.myDevice.getNetDevice())) {
            step = (y2-y1)/MoveOnLinkEvent.STEPS_ON_LINK;
            newY = y1 + packetStep * step;
            if (isVertical) {
              newX = x1;
            } else {
              if (Math.abs(step)<2) {
                newX = x1 + packetStep*(x2-x1)/MoveOnLinkEvent.STEPS_ON_LINK;
              } else {
                newX = newY/slope;
                // Reverse Y-Axis may require a change to the normal computation
                if (newX < 0) newX += Math.max(x1,x2);
              }
            }
          } else {
            step = (y1-y2)/MoveOnLinkEvent.STEPS_ON_LINK;
            newY = y2 + packetStep * step;
            if (isVertical) {
              newX = x1;
            } else {
              if (Math.abs(step)<2) {
                newX = x2 + packetStep*(x1-x2)/MoveOnLinkEvent.STEPS_ON_LINK;
              } else {
                newX = newY/slope;
                // Reverse Y-Axis may require a change to the normal computation
                if (newX < 0) newX += Math.max(x1,x2);
              }
            }
          }
            
//          System.out.println(p1.toString());
//          System.out.println(p2.toString());
//          System.out.println("New: (" + newX + ", " + newY + ")" );
//          g2.setColor(Color.RED);
          int index = moveOnLink.getPacket().getSource().getComponentID();
          // This will support 256 nodes before ... ? color index < 2^24
          g2.setColor(new Color(index * 30000));
          g2.fillOval((int)newX, (int)newY, 7, 7);
        }
      }
    }
  }

  public void SetLinkSize(int Size) {
    LinkSize = Size-1;
  }

  public void setEnds(BaseConnector e1, BaseConnector e2) {
    if (e1.myDevice.getNetDevice() instanceof Sink ||
        e2.myDevice.getNetDevice() instanceof Source) {
      linkEndOne = e2;
      linkEndTwo = e1;
    } else {
      linkEndOne = e1;
      linkEndTwo = e2;
    }
    linkEndOne.addLink(this);
    linkEndTwo.addLink(this);
    updateFromEnds();
  }

  public void notifyConnectorMoved(BaseConnector movedConnector) {
    this.updateFromEnds();
  }

  public void notifyConnectorDeleted(BaseConnector deletedConnector) {
    if (linkEndOne.equals(deletedConnector)) {
      linkEndTwo.notifyLinkDropped(this);
    }
    else if (linkEndTwo.equals(deletedConnector)) {
      linkEndOne.notifyLinkDropped(this);
    }
    linkEndOne = null;
    linkEndTwo = null;
    this.getParent().remove(this);
    
  }
  
  public BaseConnector getEndOne() {
  	return linkEndOne;
  }
  
  public BaseConnector getEndTwo() {
	return linkEndTwo;
  }
  
  public Color getMyColor() {
  	return myColor;
  }

  public Point getEndOnePoint() {
    return endOnePoint;
  }

  public Point getEndTwoPoint() {
    return endTwoPoint;
  }
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
//    g.setColor(Color.red);
//    g.drawLine(X1-Xmin,Y1-Ymin,X2-Xmin,Y2-Ymin);
    drawMe(g);
  }
  public boolean contains(int x, int y) {
    //return super.contains( x,  y);
    return false;
  }

}
