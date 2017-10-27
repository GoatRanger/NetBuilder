package network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import event.Event;

public class NetworkComponent {
  protected String name = "";
  protected static int id=0;
  protected int componentID;
  ArrayList<NetworkEventListener> listeners;

  public NetworkComponent() {
    componentID = id++;
    listeners = new ArrayList<NetworkEventListener>();
  }
  
  public String getName() {
    return name;
  }
  
  /** A method for resetting any state that shouldn't
   * be carried over between runs.  Normally related to
   * router tables, but other components may have state
   * information.
   *
   */
  public void reset() {
    // do nothing;
  }
  
  public static void resetIDs() {
    id = 0;
  }
  
  public int getComponentID() {
    return componentID;
  }

  public void setName(String string) {
  	name = string;
  }
  public void setName(String string, boolean addID) {
    name = string;
    if (addID) {
      name += componentID;
    }
  }
  
  public NetworkComponent route(Sink sink) {
    return null;
  }

  public Link findLinkTo(Link exclude, NetworkComponent component) {
    return null;
  }
  
  public void addNetworkListener(NetworkEventListener l) {
    listeners.add(l);
  }
  public void eventExecuted(Event e) {
  	List<NetworkEventListener> tempList = new ArrayList<NetworkEventListener>(listeners);
    Iterator<NetworkEventListener> i = tempList.iterator();
    while (i.hasNext()) {
      NetworkEventListener nel = (NetworkEventListener)i.next();
      nel.networkEvent(e);
    }
  }
  
  public void eventComplete(Event e) {
    List<NetworkEventListener> tempList = new ArrayList<NetworkEventListener>(listeners);
    Iterator<NetworkEventListener> i = tempList.iterator();
    while (i.hasNext()) {
      NetworkEventListener nel = (NetworkEventListener)i.next();
      nel.eventComplete(e);
    }
  }
  
  public boolean equals(Object obj) {
    if (obj != null && obj instanceof NetworkComponent) {
      return ((NetworkComponent)obj).getName().equals(this.getName());
    }
    return false;
  }
}