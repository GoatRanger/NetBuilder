package gui;

import javax.swing.*;

import network.Hub;
import network.Router;
import network.RouterFirewall;
import network.Sink;
import network.Source;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class Foundation extends JPanel
                        implements DropTargetListener {

	private static final long serialVersionUID = 1L;
	DropTarget dropTarget = null;
  Ground myGround = null;
  double myCost = 0.00;

  public Foundation() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public Foundation(Ground ground) {
    myGround = ground;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private synchronized void jbInit() throws Exception {
    System.err.println("Starting jbinit(Foundation)");
    dropTarget = new DropTarget (this, this);
    this.setLayout(null);
    this.setBorder(BorderFactory.createRaisedBevelBorder());
    //this.setSize(200,150);
    //this.setBackground(new Color(255, 96, 96));
    this.setBackground(SystemColor.controlShadow);
    System.err.println("Finishing jbinit(Foundation)");
  }

  public synchronized Ground getMyGround() {
    return myGround;
  }
  
  public synchronized void setMyGround(Ground ground) {
  	myGround = ground;
	try {
	  jbInit();
	}
	catch(Exception e) {
	  e.printStackTrace();
	}
  }

  public BaseDevice getDeviceAt(int x, int y) {
    System.err.println("Getting (Foundation)deviceAt");
    Component c = this.getComponentAt(x,y);
    if (c != null) {
      if ( c instanceof BaseDevice ||
           c instanceof GuideDevice) { //.getClass().getName().equalsIgnoreCase("NetBuilder.GuideDevice") ) {
        return (BaseDevice)c;
      }
      else {
        return null;
      }
    }
    else {
      return null;
    }
  }
  
  public void notifyDeviceDeleted(BaseDevice deletedDevice) {
    this.remove(deletedDevice);
    if (deletedDevice.getNetDevice() instanceof Source) {
      getMyGround().getMyNBFrame().getNetwork().removeSource((Source)deletedDevice.getNetDevice());
    }
    if (deletedDevice.getNetDevice() instanceof Sink) {
      getMyGround().getMyNBFrame().getNetwork().removeSink((Sink)deletedDevice.getNetDevice());
    }
    if (deletedDevice.getNetDevice() instanceof Router) {
      getMyGround().getMyNBFrame().getNetwork().removeRouter((Router)deletedDevice.getNetDevice());
    }
    if (deletedDevice.getNetDevice() instanceof Hub) {
      getMyGround().getMyNBFrame().getNetwork().removeHub((Hub)deletedDevice.getNetDevice());
    }
    deletedDevice.netDevice = null;
    this.repaint(new Rectangle(deletedDevice.getSize()));
  }

  public void dragEnter(DropTargetDragEvent event) {
    event.acceptDrag (DnDConstants.ACTION_MOVE);
  }

  public void dragExit(DropTargetEvent event) {
  }

  public void dragOver(DropTargetDragEvent event) {
  }

  public void drop(DropTargetDropEvent event) {
    try {
      Transferable transferable = event.getTransferable();
      // Accept only Strings.
      if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)){
        event.acceptDrop(DnDConstants.ACTION_COPY);
        String DropString = (String)transferable.getTransferData(DataFlavor.stringFlavor);
        handleDrop(DropString,event.getLocation());
        event.getDropTargetContext().dropComplete(true);
      }
      else {
        event.rejectDrop();
      }
    }
    catch (IOException exception) {
      exception.printStackTrace();
      System.err.println( "IO:  " + exception.getMessage());
      event.rejectDrop();
    }
    catch (UnsupportedFlavorException ufException ) {
      ufException.printStackTrace();
      System.err.println( "Unsupported Flavor:  " + ufException.getMessage());
      event.rejectDrop();
    }
    repaint();
  }

  public void dropActionChanged ( DropTargetDragEvent event ) {
  }

  public void addCost(double newCost) {
    myCost = myCost + newCost;
    myGround.addCost(newCost);
  }

  protected void handleDrop(String dropString, Point dropLocation) {
    int dropX = (int)dropLocation.getX();
    int dropY = (int)dropLocation.getY();
    Descriptor newDescriptor = Descriptor.parseString(dropString);
    if (newDescriptor.getMyTypeID()==Descriptor.DEVICE_TYPE) {
      addCost(newDescriptor.getMyCost());
      BaseDevice newDevice;
      
      if (newDescriptor.getMySubTypeID()==Descriptor.GUIDE_DEVICE) {
        newDevice = new GuideDevice(this,newDescriptor,16,16);
      }
      else {
        newDevice = new BaseDevice(this,newDescriptor);
        BaseConnector connector;
        switch (newDescriptor.getMySubTypeID()) {
        	case Descriptor.DESKTOP_DEVICE:
            Source source = new Source();
    			  source.setName("Source", true);
    			  source.setNumberOfPacketsToGenerate(2);    
            getMyGround().getMyNBFrame().getNetwork().addSource(source);
            newDevice.setNetDevice(source);
            connector = new BaseConnector(newDevice, new Descriptor(Descriptor.CONNECTOR_TYPE, Descriptor.NICHUNDRED_CONNECTOR, "1Port100MBps", 25));
            connector.setMaxLinks(1);
            newDevice.addConnector(connector);
         	  break;
        	case Descriptor.SERVER_DEVICE:
      		  Sink sink = new Sink();
      		  sink.setName("Sink", true);
            getMyGround().getMyNBFrame().getNetwork().addSink(sink);
            newDevice.setNetDevice(sink);
            connector = new BaseConnector(newDevice, new Descriptor(Descriptor.CONNECTOR_TYPE, Descriptor.NICHUNDRED_CONNECTOR, "1Port100MBps", 25));
            connector.setMaxLinks(1);
            newDevice.addConnector(connector);
            break;
          case Descriptor.ROUTER_DEVICE:
            Router router = new Router();
            router.setName("Router", true);
            getMyGround().getMyNBFrame().getNetwork().addRouter(router);
            newDevice.setNetDevice(router);
            connector = new BaseConnector(newDevice, new Descriptor(Descriptor.CONNECTOR_TYPE, Descriptor.NICHUNDRED_CONNECTOR, "16Port100MBps", 250));
            connector.setMaxLinks(16);
            newDevice.addConnector(connector);
            break;
          case Descriptor.HUB_DEVICE:
            Hub hub = new Hub();
            hub.setName("Hub", true);
            getMyGround().getMyNBFrame().getNetwork().addRouter(hub);
            newDevice.setNetDevice(hub);
            connector = new BaseConnector(newDevice, new Descriptor(Descriptor.CONNECTOR_TYPE, Descriptor.NICHUNDRED_CONNECTOR, "1Port100MBps", 25));
            connector.setMaxLinks(8);
            newDevice.addConnector(connector);
            break;
          case Descriptor.FIREWALL_DEVICE:
            RouterFirewall firewall = new RouterFirewall();
            firewall.setName("Firewall", true);
            getMyGround().getMyNBFrame().getNetwork().addRouter(firewall);
            newDevice.setNetDevice(firewall);
            connector = new BaseConnector(newDevice, new Descriptor(Descriptor.CONNECTOR_TYPE, Descriptor.NICHUNDRED_CONNECTOR, "1Port100MBps", 25));
            connector.setMaxLinks(8);
            newDevice.addConnector(connector);
            break;
          default:
            System.out.println("Added unknown device. Network not modified");
        }
              
      }
      newDevice.setLocation(dropX, dropY);
      System.err.println("Added " + newDevice.toString() + ", component:"+this.getComponentCount());
    }
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
  
  public Component add(Component c) {
    System.err.println("Adding " + c.toString());
    if (c instanceof BaseDevice) {
      BaseDevice b = (BaseDevice)c;
      System.err.println("Device: " + b.myName + ", " + b.toString());
    }
    
    super.add(c);
    System.out.println("Total devices: " + this.getComponentCount());
    return c;
  }

}