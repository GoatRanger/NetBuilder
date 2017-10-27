package gui;

import javax.swing.*;

import network.Network;
import network.NetworkComponent;
import network.NetworkEventListener;
import network.RouterFirewall;
import network.Sink;
import network.Source;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import event.Event;
import event.MoveOnLinkEvent;

public class BaseDevice extends JComponent implements DropTargetListener, NetworkEventListener {

	private static final long serialVersionUID = -1522813060507322617L;

	transient DropTarget dropTarget = null;
	transient Foundation myFoundation;
	transient int offsetX = 0;
	transient int offsetY = 0;
	transient int minX = 0;
	transient int minY = 0;
	transient int maxX = 0;
	transient int maxY = 0;
	int sizeX = 16;
	int sizeY = 16;
	static int scale = 3;
	transient boolean leftButtonDown = false;
	transient boolean rightButtonDown = false;
	transient JPopupMenu myPopupMenu = new JPopupMenu("Incognito");
	transient JMenuItem myNameMenuItem = new JMenuItem();
	transient Container myContainer;
	String myName = "Incognito";
	Vector<BaseConnector> myConnectors = new Vector<BaseConnector>();
	Vector<BaseService> myServices = new Vector<BaseService>();
	Vector<BasePerson> myPeople = new Vector<BasePerson>();
	Descriptor myDescriptor = null;
	transient BasicRenderer myRenderer = null;
	int numConnectors = 0;
	int numServices = 0;
	int numPeople = 0;
	double myCost = 0.00;
	boolean dropsAllowed = true;
	NetworkComponent netDevice;
	transient boolean showPacket = false;
	transient boolean isFirstEvent = true;
	transient int lastScale = scale;

	public BaseDevice() {
		myFoundation = null;
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseDevice(Foundation theFoundation, Descriptor theDescriptor) {
		myFoundation = theFoundation;
		myFoundation.add(this);
		this.setMyDescriptor(theDescriptor);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseDevice(Foundation theFoundation, Descriptor theDescriptor, int newSizeX, int newSizeY) {
		myFoundation = theFoundation;
		myFoundation.add(this);
		this.setMyDescriptor(theDescriptor);
		sizeX = newSizeX;
		sizeY = newSizeY;
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setScale(int s) {
		BaseDevice.scale = s;
	}

	private void jbInit() throws Exception {
		dropTarget = new DropTarget(this, this);
		this.setBackground(Color.orange);
		this.setSize(sizeX * lastScale, sizeY * lastScale);
		setupRightClickMenu();
		this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				handleMouseDragged(e);
			}
		});
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				handleMousePressed(e);
			}

			public void mouseReleased(MouseEvent e) {
				handleMouseReleased(e);
			}
		});
	}

	public void networkEvent(Event e) {
		if (netDevice instanceof Source) {
			MessagePanel.appendLine(netDevice.getName() + " sending " + ((Source) netDevice).getNumberOfPacketsToGenerate());
		}
		if (netDevice instanceof Sink) {
			if (e instanceof MoveOnLinkEvent) {
				MoveOnLinkEvent mole = (MoveOnLinkEvent) e;
				if (mole.getPacket().getSink().equals(netDevice)) {
					MessagePanel
							.appendLine(netDevice.getName() + " received packet from " + mole.getPacket().getSource().getName());
				}
			}
		}
		showPacket = true;
		myFoundation.getMyGround().repaint();
		// this.repaint();
	}

	public void eventComplete(Event e) {
		showPacket = false;

		myFoundation.getMyGround().repaint();
	}

	public void addCost(double newCost) {
		myCost = myCost + newCost;
		myFoundation.addCost(newCost);
	}

	public boolean getDropsAllowed() {
		return dropsAllowed;
	}

	public void setDropsAllowed(boolean value) {
		dropsAllowed = value;
	}

	public synchronized void setMyFoundation(Foundation newFoundation) {
		myFoundation = newFoundation;
		myFoundation.add(this);
	}

	public synchronized Foundation getMyFoundation() {
		return myFoundation;
	}

	public void setMyName(String newName) {
		myName = newName;
		myNameMenuItem.setText("Name:  " + myName);
		this.setToolTipText(myName);
		if (netDevice != null) {
			netDevice.setName(newName, false);
		}
	}

	public String getMyName() {
		return myName;
	}

	public void setNetDevice(NetworkComponent nc) {
		netDevice = nc;
		nc.addNetworkListener(this);
		setMyName(netDevice.getName());
		this.setToolTipText(myName);
	}

	public NetworkComponent getNetDevice() {
		return netDevice;
	}

	public void setMyDescriptor(Descriptor newDescriptor) {
		myDescriptor = newDescriptor;
		this.setToolTipText(myDescriptor.getShortDescription());
		this.setMyRenderer(myDescriptor.getMyRenderer());
	}

	public Descriptor getMyDescriptor() {
		return myDescriptor;
	}

	void sendThisToBack() {
		myFoundation.remove(this);
		myFoundation.add(this);
		myFoundation.getMyGround().repaint(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	void sendThisToFront() {
		myFoundation.remove(this);
		myFoundation.add(this, 0);
		myFoundation.getMyGround().repaint(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public void selfDelete() {
		BaseConnector connector;
		ListIterator<BaseConnector> cli = myConnectors.listIterator();
		while (cli.hasNext()) {

			connector = (BaseConnector) cli.next();
			connector.notifyDeviceDeleted();
		}
		myConnectors.removeAllElements();
		myFoundation.notifyDeviceDeleted(this);
	}

	public void moveTo(int x, int y) {
		this.setLocation(x, y);
		BaseConnector bc;
		ListIterator<BaseConnector> cli = myConnectors.listIterator();
		while (cli.hasNext()) {
			bc = (BaseConnector) cli.next();
			bc.notifyDeviceMoved();
		}
	}

	public BaseConnector getConnectorAt(int x, int y) {
		Component c = this.getComponentAt(x, y);
		if (c != null) {
			if (c instanceof BaseConnector) {
				return (BaseConnector) c;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void setMyRenderer(BasicRenderer newRenderer) {
		myRenderer = newRenderer;
	}

	protected void paintComponent(Graphics g) {
		if (lastScale != scale) {
			lastScale = scale;
			setSize(sizeX * scale, sizeY * scale);
		}
		super.paintComponent(g);
		g.setColor(SystemColor.controlShadow);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (myRenderer != null) {
			myRenderer.render(g, 0, 0, this.getWidth(), this.getHeight());
		}
		if (showPacket) {
			g.setColor(Color.RED);
			g.fillOval(0, 0, 5, 5);
			showPacket = false;
		}
	}

	void handleMousePressed(MouseEvent e) {
		offsetX = e.getX();
		offsetY = e.getY();
		minX = 0;
		minY = 0;
		maxX = myFoundation.getWidth() - this.getWidth();
		maxY = myFoundation.getHeight() - this.getHeight();
		if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) == MouseEvent.BUTTON3_MASK) {
			rightButtonDown = true;
			handleRightMousePressed(e);
		} else if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK) {
			leftButtonDown = true;
			handleLeftMousePressed(e);
		}
	}

	void handleRightMousePressed(MouseEvent e) {
	}

	void handleLeftMousePressed(MouseEvent e) {
	}

	void handleMouseDragged(MouseEvent e) {
		if (leftButtonDown) {
			handleLeftMouseDragged(e);
		}
		if (rightButtonDown) {
			handleRightMouseDragged(e);
		}
	}

	void handleLeftMouseDragged(MouseEvent e) {
		int newX = getX() + e.getX() - offsetX;
		int newY = getY() + e.getY() - offsetY;
		if (newX <= minX)
			newX = minX;
		if (newX >= maxX)
			newX = maxX;
		if (newY <= minY)
			newY = minY;
		if (newY >= maxY)
			newY = maxY;
		this.moveTo(newX, newY);
		this.getMyFoundation().getMyGround().repaint();
	}

	void handleRightMouseDragged(MouseEvent e) {
	}

	void handleMouseReleased(MouseEvent e) {
		if (leftButtonDown) {
			leftButtonDown = false;
			handleLeftMouseReleased(e);
		}
		if (rightButtonDown) {
			rightButtonDown = false;
			handleRightMouseReleased(e);
		}
	}

	protected void handleLeftMouseReleased(MouseEvent e) {
	}

	protected void handleRightMouseReleased(MouseEvent e) {
		if ((e.getX() == offsetX) && (e.getY() == offsetY))
			showMyPopupMenu(e.getComponent(), e.getX(), e.getY());
	}

	public void showMyPopupMenu(Component c, int thisX, int thisY) {
		myPopupMenu = new JPopupMenu();
		setupRightClickMenu();
		if (netDevice instanceof Source)
			updatePopup();
		if (netDevice instanceof RouterFirewall)
			addBlocksToPopup();
		myPopupMenu.show(c, thisX, thisY);
		this.getParent().repaint(thisX, thisY, myPopupMenu.getWidth(), myPopupMenu.getHeight());
	}

	void setupRightClickMenu() {
		myNameMenuItem.setText("Name:  " + myName);
		myNameMenuItem.setOpaque(true);
		myNameMenuItem.setBackground(new Color(64, 64, 255));
		myNameMenuItem.setForeground(Color.yellow);
		myNameMenuItem.setEnabled(false);
		myPopupMenu.add(myNameMenuItem);
		myPopupMenu.addSeparator();
		ActionListener changeNameListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMyName(JOptionPane.showInputDialog(null, "Enter a new name"));
			}
		};
		addRightClickMenuItem("Change My Name", changeNameListener);
		ActionListener sendBackListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendThisToBack();
			}
		};
		addRightClickMenuItem("Send to Back", sendBackListener);
		ActionListener sendFrontListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendThisToFront();
			}
		};
		addRightClickMenuItem("Send to Front", sendFrontListener);

		// Disabled--the Network model doesn't fully support it yet.
		/*
		 * ActionListener deleteMeListener = new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { selfDelete(); } };
		 * addRightClickMenuItem("Delete Me",deleteMeListener);
		 */
		this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	}

	private void updatePopup() {
		NetworkComponent nc = getNetDevice();
		if (nc instanceof Source) {
			Network net = myFoundation.getMyGround().getMyNBFrame().getNetwork();
			Object[] sinks = net.getSinks().toArray();
			for (int i = 0; i < sinks.length; i++) {
				addSinkToPopup((Sink) sinks[i]);
			}
		}
	}

	private void addBlocksToPopup() {
		Network net = myFoundation.getMyGround().getMyNBFrame().getNetwork();
		Object[] sources = net.getSources().toArray();
		for (int i = 0; i < sources.length; i++) {
			addSourceToPopup((Source) sources[i]);
		}
	}

	/* Original updatePopup()--this doesn't allow depth of network */
	/*
	  private void updatePopup_OLD() {
	  	// Find all possible sinks--but can only have 1 router in between 
		  NetworkComponent nc = getNetDevice(); 
		  if (nc instanceof Source) {
		  	Link link = ((Source)nc).getLink();
		  	if (link == null) {
		  		JOptionPane.showMessageDialog(null, "That component isn't connected!");
		  		return;
		  	}
		  NetworkComponent other;
		  if (link.getBlueComponent() instanceof Source) {
		  	other = link.getRedComponent();
		  } else {
		  	other = link.getBlueComponent();
		  }
		  if (other instanceof Router) {
		  	Collection<?> links = ((Router)other).getLinks();
		  	for (Iterator<?> i=links.iterator(); i.hasNext();) {
		  		Link nextLink = (Link)i.next();
		  		if (nextLink != link) {
		  			final NetworkComponent maybeSink;
		  			if (nextLink.getBlueComponent()==other) {
		  				maybeSink = nextLink.getRedComponent();
		  			} else {
		  				maybeSink = nextLink.getBlueComponent();
		  			}
		  		  if (maybeSink instanceof Sink) {
		  		  	addSinkToPopup(maybeSink);
		  		  }
		  		}
		  	}
		  } else if (other instanceof Sink) {
		  	addSinkToPopup(other);
		  }
		}
	}
	 */
	
	private void addSinkToPopup(final NetworkComponent sink) {
		JMenuItem item = new JMenuItem("Connect to " + sink.getName());
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((Source) getNetDevice()).setSink((Sink) sink);
			}
		});
		myPopupMenu.add(item);
	}

	private void addSourceToPopup(final NetworkComponent source) {
		JMenuItem item = new JMenuItem("Block " + source.getName());
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((RouterFirewall) getNetDevice()).setBlockedID(source.getComponentID());
			}
		});
		myPopupMenu.add(item);
	}

	public void addRightClickMenuItem(String ItemText, ActionListener al) {
		JMenuItem newMenuItem = new JMenuItem(ItemText);
		newMenuItem.addActionListener(al);
		myPopupMenu.add(newMenuItem);
	}

	public void dragEnter(DropTargetDragEvent event) {
		event.acceptDrag(DnDConstants.ACTION_MOVE);
	}

	public void dragExit(DropTargetEvent event) {

	}

	public void dragOver(DropTargetDragEvent event) {

	}

	public void drop(DropTargetDropEvent event) {
		try {
			Transferable transferable = event.getTransferable();
			// Accept only Strings.
			if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				event.acceptDrop(DnDConstants.ACTION_COPY);
				String DropString = (String) transferable.getTransferData(DataFlavor.stringFlavor);
				handleDrop(DropString, event.getLocation());
				event.getDropTargetContext().dropComplete(true);
			} else {
				event.rejectDrop();
			}
		} catch (IOException exception) {
			exception.printStackTrace();
			System.err.println("IO:  " + exception.getMessage());
			event.rejectDrop();
		} catch (UnsupportedFlavorException ufException) {
			ufException.printStackTrace();
			System.err.println("Unsupported Flavor:  " + ufException.getMessage());
			event.rejectDrop();
		}
	}

	public void dropActionChanged(DropTargetDragEvent event) {
	}

	protected void handleDrop(String dropString, Point dropLocation) {
		// int dropX = (int)dropLocation.getX();
		// int dropY = (int)dropLocation.getY();
		Descriptor newDescriptor = Descriptor.parseString(dropString);
		int typeID = newDescriptor.getMyTypeID();
		switch (typeID) {
		case Descriptor.CONNECTOR_TYPE:
			if (numConnectors >= 2) {
				break;
			}
			BaseConnector newConnector = new BaseConnector(this, newDescriptor);
			this.addConnector(newConnector);
			break;
		case Descriptor.DEMAND_TYPE:
			if (getNetDevice() instanceof Source) {
				BasePerson newPerson = new BasePerson(this, newDescriptor);
				this.addPerson(newPerson);
			}
			break;
		case Descriptor.SERVICE_TYPE:
			BaseService newService = new BaseService(this, newDescriptor);
			this.addService(newService);
			break;
		}
	}

	public void addConnector(BaseConnector newConnector) {
		myConnectors.add(newConnector);
		this.add(newConnector);
		newConnector.repaint();
		numConnectors++;
		newConnector.setLocation(0, 8 * (numConnectors - 1));
		newConnector.setMyDevice(this);
		this.addCost(newConnector.getMyDescriptor().getMyCost());
	}

	public void addService(BaseService newService) {
		myServices.add(newService);
		this.add(newService);
		newService.repaint();
		numServices++;
		newService.setLocation(this.getWidth() - 10, 10 * (numServices - 1));
		newService.setMyDevice(this);
		this.addCost(newService.getMyDescriptor().getMyCost());
	}

	public void addPerson(BasePerson newPerson) {
		myPeople.add(newPerson);
		this.add(newPerson);
		newPerson.repaint();
		numPeople++;
		newPerson.setLocation(10 * (numPeople - 1), this.getHeight() - 10);
		newPerson.setMyDevice(this);
		this.addCost(newPerson.getMyDescriptor().getMyCost());
		if (getNetDevice() instanceof Source) {
			Source source = (Source) getNetDevice();
			int demand = 10 - newPerson.getMyDescriptor().getMySubTypeID();
			source.setName(source.getName(), true);
			// In Descriptor, 3 subtypes of persons have id of 6,7,8
			source.setNumberOfPacketsToGenerate(source.getNumberOfPacketsToGenerate() + demand);
		}
	}

	/**
	 * @return Returns the myConnectors.
	 */
	public Vector<BaseConnector> getMyConnectors() {
		return myConnectors;
	}

	/**
	 * @param myConnectors
	 *          The myConnectors to set.
	 */
	public void setMyConnectors(Vector<BaseConnector> myConnectors) {
		this.myConnectors = myConnectors;
	}

	/**
	 * @return Returns the myCost.
	 */
	public double getMyCost() {
		return myCost;
	}

	/**
	 * @param myCost
	 *          The myCost to set.
	 */
	public void setMyCost(double myCost) {
		this.myCost = myCost;
	}

	/**
	 * @return Returns the myPeople.
	 */
	public Vector<BasePerson> getMyPeople() {
		return myPeople;
	}

	/**
	 * @param myPeople
	 *          The myPeople to set.
	 */
	public void setMyPeople(Vector<BasePerson> myPeople) {
		this.myPeople = myPeople;
	}

	/**
	 * @return Returns the numConnectors.
	 */
	public int getNumConnectors() {
		return numConnectors;
	}

	/**
	 * @param numConnectors
	 *          The numConnectors to set.
	 */
	public void setNumConnectors(int numConnectors) {
		this.numConnectors = numConnectors;
	}

	/**
	 * @return Returns the numPeople.
	 */
	public int getNumPeople() {
		return numPeople;
	}

	/**
	 * @param numPeople
	 *          The numPeople to set.
	 */
	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}

	/**
	 * @return Returns the numServices.
	 */
	public int getNumServices() {
		return numServices;
	}

	/**
	 * @param numServices
	 *          The numServices to set.
	 */
	public void setNumServices(int numServices) {
		this.numServices = numServices;
	}

	/**
	 * @return Returns the sizeX.
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * @param sizeX
	 *          The sizeX to set.
	 */
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	/**
	 * @return Returns the sizeY.
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 * @param sizeY
	 *          The sizeY to set.
	 */
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

}
