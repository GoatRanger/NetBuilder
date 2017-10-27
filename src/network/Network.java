package network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Network {
  
  public static int MAX_TTL = 24;
  private String name = "Network";
  private Collection<Source> sources = new ArrayList<Source>();
  private Collection<Sink> sinks = new ArrayList<Sink>();
  private Collection<Link> links = new ArrayList<Link>();
  private Collection<Router> routers = new ArrayList<Router>();
  private Collection<Hub> hubs = new ArrayList<Hub>();

  public static void setMaxTTL(int ttl) {
    MAX_TTL = ttl;
  }
  public void removeAll() {
    sources.clear();
    sinks.clear();
    links.clear();
    routers.clear();
    hubs.clear();
  }
  
  public void resetAll() {
    Iterator<?> i = sources.iterator();
    while (i.hasNext()) {
      ((NetworkComponent)i.next()).reset();
    }
    i = sinks.iterator();
    while (i.hasNext()) {
      ((NetworkComponent)i.next()).reset();
    }
    i = links.iterator();
    while (i.hasNext()) {
      ((NetworkComponent)i.next()).reset();
    }
    i = routers.iterator();
    while (i.hasNext()) {
      ((NetworkComponent)i.next()).reset();
    }
    i = hubs.iterator();
    while (i.hasNext()) {
      ((NetworkComponent)i.next()).reset();
    }
    
  }
  public void addSource(Source source) {
    sources.add(source);
  }
  
  public void removeSource(Source source) {
    sources.remove(source);
  }

  public void addSink(Sink sink) {
    sinks.add(sink);
  }
  
  public void removeSink(Sink sink) {
    sinks.remove(sink);
  }

  public void addLink(Link link) {
    links.add(link);
  }
  
  public void removeLink(Link link) {
    links.remove(link);
  }

  public void addRouter(Router router) {
    routers.add(router);
  }
  
  public void removeRouter(Router router) {
    routers.remove(router);
  }

  public void addHub(Hub hub) {
    hubs.add(hub);
  }
  
  public void removeHub(Hub hub) {
    hubs.remove(hub);
  }

  public Collection<Hub> getHubs() {
    return hubs;
  }

  public Collection<Link> getLinks() {
    return links;
  }

  public Collection<Router> getRouters() {
    return routers;
  }

  public Collection<Sink> getSinks() {
    return sinks;
  }

  public Collection<Source> getSources() {
    return sources;
  }

  public void setHubs(Collection<Hub> collection) {
    hubs = collection;
  }

  public void setLinks(Collection<Link> collection) {
    links = collection;
  }

  public void setRouters(Collection<Router> collection) {
    routers = collection;
  }

  public void setSinks(Collection<Sink> collection) {
    sinks = collection;
  }

  public void setSources(Collection<Source> collection) {
    sources = collection;
  }
  
  public String toString() {
    StringBuffer sb = new StringBuffer("");
    sb.append(name);
    sb.append("\n");
    for (Iterator<Source> i = sources.iterator(); i.hasNext();) {
      Source s = (Source) i.next();
      sb.append("  ");
      sb.append(s.toString()); 
      sb.append("\n");
    }
    for (Iterator<Sink> i = sinks.iterator(); i.hasNext();) {
      Sink s = (Sink) i.next();
      sb.append("  ");
      sb.append(s.toString()); 
      sb.append("\n");
    }
    for (Iterator<Link> i = links.iterator(); i.hasNext();) {
      Link l = (Link) i.next();
      sb.append("  ");
      sb.append(l.toString()); 
      sb.append("\n");
    }
    for (Iterator<Router> i = routers.iterator(); i.hasNext();) {
      Router r = (Router) i.next();
      sb.append("  ");
      sb.append(r.toString()); 
      sb.append("\n");
    }
    for (Iterator<Hub> i = hubs.iterator(); i.hasNext();) {
      Hub h = (Hub) i.next();
      sb.append("  ");
      sb.append(h.toString()); 
      sb.append("\n");
    }
    return sb.toString();
  }
}