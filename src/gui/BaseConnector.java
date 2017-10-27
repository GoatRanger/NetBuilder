package gui;

import network.Link;
import network.NetworkComponent;
import network.Router;
import network.Sink;
import network.Source;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

import javax.swing.*;


/**
 * DOCUMENT ME!
 *
 * @author Karl Gossett
 * @version 1.0
 */
/**
 * @author Karl
 *
 */
public class BaseConnector extends JComponent {
  /** For serialization */
	private static final long serialVersionUID = 4579530951377058390L;
	
	private static boolean verbose = false;

	/** the device this connector is connected to */
  BaseDevice myDevice = null;

  /** DOCUMENT ME! */
  transient BaseLink tempLink;

  /** DOCUMENT ME! */
  Descriptor myDescriptor = null;

  /** DOCUMENT ME! */
  Foundation myFoundation = null;

  /** DOCUMENT ME! */
  Vector<BaseLink> links = new Vector<BaseLink>();

  /** DOCUMENT ME! */
  transient boolean attemptingLink = false;

  /** DOCUMENT ME! */
  int maxLinks = 10;

  /** DOCUMENT ME! */
  transient int startX = 0;

  /** DOCUMENT ME! */
  transient int startY = 0;

  /** DOCUMENT ME! */
  transient int stopX = 0;

  /** DOCUMENT ME! */
  transient int stopY = 0;
  
  static int staticScale = 3;
  int lastScale = 3;
  int sizeX = 8;
  int sizeY = 8;

  /**
   * Creates a new BaseConnector object.
   */
  public BaseConnector() {
    System.err.println("new bc");
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a new BaseConnector object.
   *
   * @param newDevice the {@link Device} that this connector is attached to
   * @param newDescriptor DOCUMENT ME!
   */
  public BaseConnector(BaseDevice newDevice, Descriptor newDescriptor) {
    if (BaseConnector.isVerbose()) {
    	System.err.println("new bc full");
    }
    myDevice = newDevice;
    myDescriptor = newDescriptor;
    this.setToolTipText(myDescriptor.getShortDescription());
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

	/**
	 * Sets whether or not this component should provide verbose logging
	 * @param v
	 */
	public static void setVerbose(boolean v) {
		verbose = v;
	}
	
	/**
	 * Gets whether or not this component is providing verbose logging
	 * @return true if verbose logging is on
	 */
	public static boolean isVerbose() {
		return verbose;
	}

  /**
   * Gets the horizontal center of this component on the screen, in pixels.
   *
   * @return the x coordinate of the center, in pixels
   */
  public int getCenterX() {
    return getBaseX() + this.getWidth() / 2;
  }

  /**
   * Gets the vertical center of this component on the screen, in pixels.
   *
   * @return the y coordinate of the center, in pixels
   */
  public int getCenterY() {
    return getBaseY() + this.getHeight() / 2;
  }
  
  public static void setScale(int s) {
    BaseConnector.staticScale = s;
  }

  /**
   * DOCUMENT ME!
   *
   * @param newMax DOCUMENT ME!
   */
  public void setMaxLinks(int newMax) {
    maxLinks = newMax;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public Descriptor getMyDescriptor() {
  	if (BaseConnector.isVerbose()) {
  		System.err.println("get descriptor from " + myDescriptor);
  	}
    return myDescriptor;
  }

  /**
   * DOCUMENT ME!
   *
   * @param theDevice DOCUMENT ME!
   */
  public void setMyDevice(BaseDevice theDevice) {
  	if (BaseConnector.isVerbose()) {
  		System.err.println("set device: " + theDevice.getMyName());
  	}
    myDevice = theDevice;
  }

  /**
   * DOCUMENT ME!
   *
   * @param newLink DOCUMENT ME!
   */
  public void addLink(BaseLink newLink) {
  	if (BaseConnector.isVerbose()) {
  		System.err.println("add link to " + myDescriptor);
  	}
    links.add(newLink);
  }

  /**
   * DOCUMENT ME!
   *
   * @param cType DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean canAddLink(int cType) {
    boolean answer = false;
    if (links.size() < maxLinks) {
      if ((myDescriptor.getMySubTypeID() == cType)
            || (myDescriptor.getMySubTypeID() == Descriptor.BASE_CONNECTOR)) {
        answer = true;
      }
    }
    return answer;
  }

  /**
   * DOCUMENT ME!
   */
  public void notifyDeviceDeleted() {
    BaseLink bl;
    ListIterator<BaseLink> lli = links.listIterator();

    while (lli.hasNext()) {
      bl = (BaseLink) lli.next();
      bl.notifyConnectorDeleted(this);
    }

    links.removeAllElements();
  }

  /**
   * DOCUMENT ME!
   */
  public void notifyDeviceMoved() {
    BaseLink bl;
    ListIterator<BaseLink> lli = links.listIterator();

    while (lli.hasNext()) {
      bl = (BaseLink) lli.next();
      bl.notifyConnectorMoved(this);
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param droppedLink DOCUMENT ME!
   */
  public void notifyLinkDropped(BaseLink droppedLink) {
    links.remove(droppedLink);
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  protected int getBaseX() {
    return myDevice.getMyFoundation().getX() + myDevice.getX() + this.getX();
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  protected int getBaseY() {
    return myDevice.getMyFoundation().getY() + myDevice.getY() + this.getY();
  }

  /**
   * DOCUMENT ME!
   *
   * @param g DOCUMENT ME!
   */
  protected void paintComponent(Graphics g) {
    if (lastScale != staticScale) {
      lastScale = staticScale;
      setSize(sizeX * lastScale, sizeY * lastScale);
    }
    super.paintComponent(g);
    if (myDescriptor != null) {
      myDescriptor.getMyRenderer().render(g, 0, 0, this.getWidth(), this.getHeight());
    } else {
      //super.paintComponent(g);
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param e DOCUMENT ME!
   */
  void this_mouseDragged(MouseEvent e) {
    stopX = getBaseX() + e.getX();
    stopY = getBaseY() + e.getY();

    if (attemptingLink) {
      tempLink.updateFromEndPoints(startX, startY, stopX, stopY);
    }
    myDevice.getMyFoundation().getMyGround().repaint();
  }

  /**
   * DOCUMENT ME!
   *
   * @param e DOCUMENT ME!
   */
  void this_mousePressed(MouseEvent e) {
    Ground myGround = myDevice.getMyFoundation().getMyGround();
    startX = getBaseX() + this.getWidth() / 2;
    startY = getBaseY() + this.getHeight() / 2;

    if (links.size() < maxLinks) {
      stopX = getBaseX() + e.getX();
      stopY = getBaseY() + e.getY();

      Color linkColor = Color.white;

      switch (myDescriptor.getMySubTypeID()) {
        case Descriptor.NICTEN_CONNECTOR:
          linkColor = Color.green;

          break;

        case Descriptor.NICHUNDRED_CONNECTOR:
          linkColor = Color.orange;

          break;

        case Descriptor.NICGIG_CONNECTOR:
          linkColor = Color.red;

          break;
      }

      tempLink = new BaseLink(startX, startY, stopX, stopY, linkColor);
      tempLink.setConnectionType(myDescriptor.getMySubTypeID());
      myGround.add(tempLink, 0);
      myGround.repaint();
      attemptingLink = true;
    } else {
      attemptingLink = false;
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param e DOCUMENT ME!
   */
  void this_mouseReleased(MouseEvent e) {
    stopX = getBaseX() + e.getX();
    stopY = getBaseY() + e.getY();
    if (attemptingLink) {
      Ground myGround = myDevice.getMyFoundation().getMyGround();
      Component otherComponent = myGround.getComponentAt(stopX, stopY);

      if (otherComponent != null) {
        if (otherComponent instanceof Foundation) { //.getClass().getName().equalsIgnoreCase("NetBuilder.Foundation")) {
          try {
            Foundation otherFoundation = (Foundation) otherComponent;
            int fx = stopX - otherFoundation.getX();
            int fy = stopY - otherFoundation.getY();
            BaseDevice otherDevice = otherFoundation.getDeviceAt(fx, fy);

            if (otherDevice != null) {
              int dx = fx - otherDevice.getX();
              int dy = fy - otherDevice.getY();
              BaseConnector otherConnector = otherDevice.getConnectorAt(dx, dy);

              if (otherConnector != null && otherConnector != this) {
                if (otherConnector.canAddLink(myDescriptor.getMySubTypeID())) {
                  tempLink.setEnds(this, otherConnector);
                  Link link = new Link();
                  link.addNetworkListener(tempLink);
                  NetworkComponent device1 = myDevice.getNetDevice();
                  NetworkComponent device2 = otherDevice.getNetDevice();
                  link.connect(device1, device2);
                  link.setName("GuiLink", true);
                  myGround.getMyNBFrame().getNetwork().addLink(link);
                  updateLink(link, device1);
                  updateLink(link, device2);
                  if (device1 instanceof Source) {
                    ((Source)device1).setNextStop(device2);
                  }
                  if (device2 instanceof Source) {
                    ((Source)device2).setNextStop(device1);
                  }
                  if (device1 instanceof Sink) {
                    if (device2 instanceof Router) {
                      ((Router)device2).addNextStopForSink(device1, (Sink)device1);
                    }
                  }
                  if (device2 instanceof Sink) {
                    if (device1 instanceof Router) {
                      ((Router)device1).addNextStopForSink(device2, (Sink)device2);
                    }
                  }
                } else {
                  myGround.remove(tempLink);
                  myGround.repaint();
                }
              } else {
                myGround.remove(tempLink);
                myGround.repaint();
              }
            } else {
              myGround.remove(tempLink);

              //tempLink = null;
              myGround.repaint();
            }
          } catch (ClassCastException cce) {
            myDevice.getMyFoundation().getMyGround().remove(tempLink);
            cce.printStackTrace();
          }
        } else {
          myGround.remove(tempLink);
        }
      } else {
        myGround.remove(tempLink);
      }
      myGround.repaint();
      attemptingLink = false;
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param g DOCUMENT ME!
   */
  private void drawMe(Graphics g) {
    g.setColor(Color.white);
    g.fill3DRect(0, 0, 16, 16, true);
    g.setColor(Color.black);
    g.fillOval(0, 0, 16, 16);
  }

  /**
   * DOCUMENT ME!
   *
   * @throws Exception DOCUMENT ME!
   */
  private void jbInit() throws Exception {

    this.setSize(sizeX * lastScale, sizeY * lastScale);
    this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseDragged(MouseEvent e) {
          this_mouseDragged(e);
        }
      });
    this.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          this_mousePressed(e);
        }

        public void mouseReleased(MouseEvent e) {
          this_mouseReleased(e);
        }
      });
  }

  /**
   * DOCUMENT ME!
   *
   * @param link DOCUMENT ME!
   * @param nc DOCUMENT ME!
   */
  private void updateLink(Link link, NetworkComponent nc) {
  	if (BaseConnector.isVerbose()) {
  		System.err.println("updatelink");
  	}
    if (nc instanceof Sink) {
      Sink sink = (Sink) nc;
      sink.setLink(link);
    } else if (nc instanceof Source) {
      Source src = (Source) nc;
      src.setLink(link);
    } else if (nc instanceof Router) {
      Router rtr = (Router) nc;
      rtr.addLink(link);
    }
  }
/**
 * @return Returns the Links.
 */
public Vector<BaseLink> getLinks() {
	if (BaseConnector.isVerbose()) {
		System.err.println("getlinks from " + myDescriptor);
	}
	return links;
}

/**
 * @param myLinks The Links to set.
 */
public void setLinks(Vector<BaseLink> myLinks) {
	if (BaseConnector.isVerbose()) {
		System.err.println("setlinks in " + myDescriptor);
	}
	this.links = myLinks;
}

/**
 * @return Returns the maxLinks.
 */
public int getMaxLinks() {
	return maxLinks;
}

/**
 * @return Returns the myDevice.
 */
public BaseDevice getMyDevice() {
	return myDevice;
}

/**
 * @param myDescriptor The myDescriptor to set.
 */
public void setMyDescriptor(Descriptor myDescriptor) {
	if (BaseConnector.isVerbose()) {
		System.err.println("set Descriptor for " + myDescriptor);
	}
	this.myDescriptor = myDescriptor;
	this.setToolTipText(myDescriptor.getShortDescription());
}

}
