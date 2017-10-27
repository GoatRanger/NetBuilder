package gui;

import java.awt.*;
import java.util.*;

public class Descriptor {

  public final static int NO_TYPE = 0;
  public final static int DEVICE_TYPE = 1;
  public final static int SERVICE_TYPE = 2;
  public final static int CONNECTOR_TYPE = 3;
  public final static int DEMAND_TYPE = 4;
  public final static String[] typeName =
    {"None", "Device", "Service", "NICs","Demand"};
  public final static int NO_SUBTYPE = 0;
  public final static int DESKTOP_DEVICE = 1;
  public final static int SERVER_DEVICE = 2;
  public final static int ROUTER_DEVICE = 3;
  public final static int MAIL_SERVICE = 4;
  public final static int ADMIN_DEMAND = 5;
  public final static int GRAPHIC_DEMAND = 6;
  public final static int PROGRAM_DEMAND = 7;
  public final static int NEWUSER_DEMAND = 8;
  public final static int NIC_CONNECTOR = 9;
  public final static int WEB_SERVICE = 10;
  public final static int DATA_SERVICE = 11;
  public final static int FILE_SERVICE = 12;
  public final static int GUIDE_DEVICE = 13;
  public final static int BASE_CONNECTOR = 14;
  public final static int NICTEN_CONNECTOR = 15;
  public final static int NICHUNDRED_CONNECTOR = 16;
  public final static int NICGIG_CONNECTOR = 17;
  public final static int HUB_DEVICE = 18;
  public final static int FIREWALL_DEVICE = 19;

  public final static String[] subTypeName =
    {"None", "Desktop", "Server", "Router","Mail","System Administrator",
     "Graphic Artist", "Programmer", "New User", "NIC","Web Service",
     "Data Service", "File Service", "Guide","Base Connector",
     "10Mbps NIC","100Mbps NIC","1Gbps NIC","Hub","Firewalled Router"};

  public final static BasicRenderer[] renderer =
    {new BasicRenderer(), new DesktopRenderer(), new ServerRenderer(),
     new RouterRenderer(), new MailRenderer(), new PersonRenderer(Color.blue),
     new PersonRenderer(Color.red), new PersonRenderer(Color.yellow),
     new PersonRenderer(Color.green), new NICRenderer(),
     new WebRenderer(), new DataRenderer(), new FileRenderer(),
     new GuideRenderer(), new BaseConnectorRenderer(),
     new NICtenRenderer(), new NIChundredRenderer(), new NICgigRenderer(),
     new HubRenderer(), new FirewallRouterRenderer()};

  int myTypeID = NO_TYPE;
  int mySubTypeID = NO_SUBTYPE;
  String myBrandName = "No Brand Name";
  double myCost = 0.0;
  BasicRenderer myRenderer = new BasicRenderer();

  public Descriptor() {
  }

  public Descriptor(int typeID, int subTypeID, String brandName, double cost) {
    myTypeID = typeID;
    mySubTypeID = subTypeID;
    myBrandName = brandName;
    myCost = cost;
  }

  public String toString() {
    String descString = new String(
      Integer.toString(myTypeID) + ":" +
      Integer.toString(mySubTypeID) + ":" +
      myBrandName + ":" +
      Double.toString(myCost));
    return descString;
  }

  public static Descriptor parseString(String descString) {
    Descriptor newDescriptor = new Descriptor();
    StringTokenizer st = new StringTokenizer(descString,":");
    newDescriptor.setMyTypeID(Integer.parseInt(st.nextToken()));
    newDescriptor.setMySubTypeID(Integer.parseInt(st.nextToken()));
    newDescriptor.setMyBrandName(st.nextToken());
    newDescriptor.setMyCost(Double.parseDouble(st.nextToken()));
    return newDescriptor;
  }

  public static String getNameForTypeID(int typeID) {
    return typeName[typeID];
  }

  public void setMyTypeID(int newTypeID) {
    myTypeID = newTypeID;
  }

  public static String getNameForSubTypeID(int subTypeID) {
    return subTypeName[subTypeID];
  }

  public void setMySubTypeID(int newSubTypeID) {
    mySubTypeID = newSubTypeID;
  }

  public void setMyBrandName(String newBrandName) {
    myBrandName = newBrandName;
  }
  
  public String getMyBrandName() {
  	return myBrandName;
  }

  public void setMyCost(double newCost) {
    myCost = newCost;
  }

  public double getMyCost() {
    return myCost;
  }

  public BasicRenderer getMyRenderer() {
    return renderer[mySubTypeID];
  }

  public String getShortDescription() {
    // Original short from MAADNet 
//    return new String(subTypeName[mySubTypeID] + ":" + myBrandName + ":$" + Double.toString(myCost));
      return new String(subTypeName[mySubTypeID]);
  }

  public int getMyTypeID() {
    return myTypeID;
  }

  public int getMySubTypeID() {
    return mySubTypeID;
  }

}