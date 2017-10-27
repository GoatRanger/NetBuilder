package network;

public class Packet implements Comparable<Object> {
  private Source source = null;
  private Sink sink = null;
  private NetworkComponent nextStop = null;
  private String name = "";
  private Link currentLink = null;
  private int ttl = Network.MAX_TTL;
  
  public Object clone() {
    Packet packet = new Packet();
    packet.setSource(source);
    packet.setSink(sink);
    packet.setNextStop(nextStop);
    packet.setName(name);
    packet.setCurrentLink(currentLink);
    packet.setTTL(ttl);
    return packet;
  }
  
  public Sink getSink() {
    return sink;
  }

  public int getTTL() {
    return ttl;
  }
  
  public void setTTL(int ttl) {
    this.ttl = ttl;
  }
  public Source getSource() {
    return source;
  }

  public void setSink(Sink sink) {
    this.sink = sink;
  }

  public void setSource(Source source) {
    this.source = source;
  }

  public NetworkComponent getNextStop() {
    return nextStop;
  }

  public void setNextStop(NetworkComponent component) {
    nextStop = component;
  }

  public String getName() {
    return name;
  }

  public void setName(String string) {
    name = string;
  }

  public Link getCurrentLink() {
    return currentLink;
  }

  public void setCurrentLink(Link link) {
    currentLink = link;
  }
  
  public boolean equals(Object o) {
    if (o != null && o instanceof Packet) {
      Packet pkt = (Packet)o;
      return nameWithoutID(pkt.getName()).equals(nameWithoutID(this.getName()));
    }
    return false;
  }
  
  public int compareTo(Object o) {
    return (nameWithoutID(((Packet)o).getName())
            .compareTo(nameWithoutID(this.getName())));
  }
  
  public int hashCode() {
    return getName().hashCode();
  }
  
  public String nameWithoutID(String pktName) {
    int colon = pktName.indexOf(":");
    if (colon > -1) {
      return pktName.substring(0,colon);
    }
    else return pktName;
  }
}