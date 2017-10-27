package network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Router extends NetworkComponent {
  Collection<Link> links = new ArrayList<Link>();
  
  // Routing table
  Collection<NetworkComponent> sinks = new ArrayList<NetworkComponent>();
  Collection<NetworkComponent> nextStops = new ArrayList<NetworkComponent>();

  public Link findLinkTo(Link exclude, NetworkComponent component) {
    if (component==null) return null;
    int maxHops = 18;
    int minPath = maxHops + 1;
    Link minLink = null;
    for (Iterator<Link> i = links.iterator(); i.hasNext(); ) {
      Link link = (Link) i.next();
      if (link.equals(exclude)) continue;
      ArrayList<Link> visited = new ArrayList<Link>();
      visited.add(exclude);
      int len;
      if ((len = link.pathLength(0, maxHops, visited, this, component)) > 0) {
        if (len < minPath) {
          minPath = len;
          minLink = link;
        }
      }
    }
    return minLink;
  }
    
  public NetworkComponent route(Sink sink) {
    for (Iterator<NetworkComponent> i = sinks.iterator(), j = nextStops.iterator(); i.hasNext() && j.hasNext(); ) {
      NetworkComponent component = i.next();
      if (component.equals(sink)) {
        NetworkComponent next = j.next();
        return next;
      } else {
        j.next();
      }
    }
    return null;
  }

  public void addNextStopForSink(NetworkComponent nextStop, Sink sink) {
    nextStops.add(nextStop);
    sinks.add(sink);
  }

  public void addLink(Link link) {
    links.add(link);
  }
  
  public Collection<Link> getLinks() {
    return links;
  }

  public void setLinks(Collection<Link> collection) {
    links = collection;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("");
    sb.append("Router:");
    sb.append(getName());
    sb.append("\n");
    for (Iterator<Link> i = links.iterator(); i.hasNext(); ) {
      Link l = (Link) i.next();
      sb.append("   ");
      sb.append(l.toString());
      sb.append("\n");
    }
    return sb.toString();
  }
 
}